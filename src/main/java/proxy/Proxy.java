package proxy;

import proxy.packets.Packet;
import util.PacketUtil;
import proxy.packets.serverbound.handshaking.PacketLegacyPing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Proxy {
    public enum State {
        HANDSHAKING, STATUS, LOGIN, PLAY
    }

    public enum Binding {
        CLIENT, SERVER
    }

//    public static State clientState = State.HANDSHAKING;
    public static State serverState = State.HANDSHAKING;

//    public static void setClientState(State state) {
//        clientState = state;
//    }

    public static void setServerState(State state) {
        serverState = state;
    }

    public static boolean compressed = false;
    public static int compressionThreshold = -1;

    public static boolean encrypted = false;
    public static byte[] pubKey;
    public static byte[] privKey;

    public Proxy(String ip, int port) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(25565);
        System.out.println("Starting");
        Socket downstream = serverSocket.accept();
        Socket upstream = new Socket(ip, port);
        System.out.println("Connected");

        ProxyThread clientThread = new ProxyThread(downstream.getInputStream(), upstream.getOutputStream(), Binding.SERVER);
        ProxyThread serverThread = new ProxyThread(upstream.getInputStream(), downstream.getOutputStream(), Binding.CLIENT);

        clientThread.start();
        serverThread.start();
        while (clientThread.isAlive() && serverThread.isAlive()) {
            Thread.sleep(1000);
        }
        clientThread.join();
        serverThread.join();
    }

    public byte readByte(ConcurrentLinkedQueue<Byte> queue) {
        while (queue.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.remove();
    }

    public int readVarInt(ConcurrentLinkedQueue<Byte> queue) {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte(queue);
            value |= (currentByte & 0x7f) << position;
            if ((currentByte & 0x80) == 0) break;

            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt too large");
        }
        return value;
    }

    private static class ProxyThread extends Thread {
        private enum ReadState {
            WAITING, READING
        }

        private ReadState readState = ReadState.WAITING;
        private boolean connected;
        private int packetLen = 0;

        private final InputStream is;
        private final OutputStream os;
        private final Binding binding;

        public ProxyThread(InputStream is, OutputStream os, Binding binding) {
            this.is = is;
            this.os = os;
            this.binding = binding;
            connected = true;
        }

        private byte readByte() throws IOException {
            if (!connected) return 0;

            int data;
            if ((data = is.read()) == ~0x0) {
                connected = false;
            }
            packetLen--;
            return (byte) (data & 0xff);
        }

        private LinkedList<Byte> readPacket() throws IOException {
            LinkedList<Byte> bytes = new LinkedList<>();
            System.out.println("Reading: " + packetLen);
            while (packetLen > 0) bytes.add(readByte());
            return bytes;
        }

        private int readVarInt() throws IOException {
            int value = 0;
            int position = 0;
            byte currentByte;
            boolean warning = false;
            while (true) {
                currentByte = readByte();
                if (warning && currentByte == (byte) 0x01) {
                    return -1;
                }
                warning = currentByte == (byte) 0xfe && position == 0;
                value |= (currentByte & 0x7f) << position;

                if ((currentByte & 0x80) == 0) break;
                position += 7;
                if (position >= 32) throw new RuntimeException("VarInt too large");
            }
            return value;
        }

        private void sendPacket(Packet packet) throws IOException {
            LinkedList<Byte> bytes = new LinkedList<>();
            packet.pack(bytes);
            System.out.println("Writing: " + bytes.size());
//            System.out.println(bytes);
            for (byte b : bytes) {
                os.write(b & 0xff);
            }
            os.flush();
        }

        @Override
        public void run() {
            try {
                while (connected) {
                    switch (readState) {
                        case WAITING -> {
                            packetLen = readVarInt();
                            if (packetLen == -1) {
                                sendPacket(new PacketLegacyPing());
                                continue;
                            }
                            readState = ReadState.READING;
                        }
                        case READING -> {
                            if (encrypted) {
                                System.out.println("ENCRYPTED!!!!");
                            }
                            if (compressed && packetLen > compressionThreshold) {
                                int dataLen = readVarInt();
                                System.out.println("COMPRESSED!!!");
                            } else {
                                Packet packet = PacketUtil.generate(serverState, binding, readPacket());
                                System.out.println(packet);
                                sendPacket(packet);
                                readState = ReadState.WAITING;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
