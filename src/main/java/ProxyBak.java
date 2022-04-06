//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PipedInputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class proxy.Proxy {
//    public static final int SEGMENT_BITS = 0x7f;
//    public static final int CONTINUE_BIT = 0x80;
//    private static int id = 0;
//
//    private StreamHandler ds;
//    private StreamHandler us;
//
//    private ConcurrentLinkedQueue<Byte> dsQueue;
//    private ConcurrentLinkedQueue<Byte> usQueue;
//
//    public proxy.Proxy(String ip, int port) throws IOException, InterruptedException {
//        dsQueue = new ConcurrentLinkedQueue<>();
//        usQueue = new ConcurrentLinkedQueue<>();
//
//        Scanner in = new Scanner(System.in);
//        ServerSocket serverSocket = new ServerSocket(25565);
//        System.out.println("Starting");
//        do {
//            Socket dsSocket = serverSocket.accept();
//            System.out.println("Connected: " + id);
//            StringBuilder dsBuilder = new StringBuilder();
//            StringBuilder usBuilder = new StringBuilder();
//
//            ds = new StreamHandler(dsSocket, data -> {
//                us.write((byte) data);
//                dsQueue.add((byte) data);
//            });
//            us = new StreamHandler(new Socket(ip, port), data -> {
//                ds.write((byte) data);
//                usQueue.add((byte) data);
//            });
//
////            Parser dsParser = new Parser(dsQueue, us, ds, "[D]");
////            Parser usParser = new Parser(usQueue, us, ds, "[U]");
//
//            Thread outputThread = new Thread(() -> {
//                try {
////                dsParser.parse();
////                usParser.parse();
//                    int packetBytesLeft = 0;
//                    StringBuilder lineBuilder = new StringBuilder();
//                    while (true) {
////                    while (us.connected() && ds.connected()) {
//                        if (packetBytesLeft == 0) {
//                            if (lineBuilder.length() != 0) {
//                                System.out.println("[D] " + lineBuilder);
//                                lineBuilder.setLength(0);
//                            }
//                            packetBytesLeft = readVarInt(lineBuilder);
//                            lineBuilder.append(" | ");//String.format("%5d | ", packetBytesLeft));
//                        } else {
//                            while (dsQueue.isEmpty()) {
//                                Thread.sleep(1);
//                            }
//                            lineBuilder.append(String.format("%02x", dsQueue.remove() & 0xff));
//                            packetBytesLeft--;
//                        }
////                    if (!usQueue.isEmpty()) {
////                        byte usByte = usQueue.remove();
////                        usBuilder.append(String.format("%02X ", usByte));
////                    }
////                    if (!dsQueue.isEmpty()) {
////                        byte dsByte = dsQueue.remove();
////                        dsBuilder.append(String.format("%02X ", dsByte));
////                    }
////
////                    if (dsBuilder.length() >= 16) {
////                        System.out.println("[D] " + dsBuilder);
////                        dsBuilder.setLength(0);
////                    }
////                    if (usBuilder.length() >= 16) {
////                        System.out.println("[U] " + usBuilder);
////                        usBuilder.setLength(0);
////                    }
//                    }
//                } catch (InterruptedException ignored) {}
//            });
//
//            ds.start();
//            us.start();
//            outputThread.start();
//            while (us.connected() && ds.connected()) {
//                Thread.sleep(500);
//            }
//            outputThread.interrupt();
////            ds.close();
////            us.close();
//            System.out.println("Disconnected: " + id++);
//        } while (in.nextInt() == 0);
//
//        serverSocket.close();
//    }
//
////    public int readVarInt(StringBuilder lineBuilder) throws InterruptedException {
////        int value = 0;
////        int position = 0;
////        byte currentByte;
////        byte readByte;
////
////        while (true) {
////            while (dsQueue.isEmpty()) {
////                Thread.sleep(1);
////            }
////            currentByte = (byte)(dsQueue.remove() & 0xff);
////            lineBuilder.append(String.format("%02x", currentByte));
////            value |= (currentByte & SEGMENT_BITS) << position;
////
////            if ((currentByte & CONTINUE_BIT) == 0) break;
////
////            position += 7;
////
////            if (position >= 32) throw new RuntimeException("VarInt is too big");
////        }
////
////        return value;
////    }
//
//    private static class StreamHandler extends Thread {
//        private boolean connected = true;
//        private final Socket socket;
//        private final DataHandler dataHandler;
//
//        private OutputStream os;
//        private InputStream is;
//
//        public StreamHandler(Socket socket, DataHandler handler) {
//            this.socket = socket;
//            dataHandler = handler;
//        }
//
//        @Override
//        public void run() {
//            try {
//                is = socket.getInputStream();
//                os = socket.getOutputStream();
//
//                int data;
//                while ((data = is.read()) != ~0x0) {
//                    dataHandler.handle(data);
//                }
//                close();
//            } catch (IOException | InterruptedException ignored) {
//            } finally {
//                connected = false;
//            }
//        }
//
//        public boolean connected() {
//            return connected;
//        }
//
//        public void write(byte data) throws IOException {
//            os.write(data);
//            os.flush();
//        }
//
//        public void close() throws IOException {
//            socket.close();
//        }
//    }
//
//    private static class Parser {
//        public static final int SEGMENT_BITS = 0x7f;
//        public static final int CONTINUE_BIT = 0x80;
//
//        private ConcurrentLinkedQueue<Byte> byteQueue;
//        private StreamHandler us;
//        private StreamHandler ds;
//        private String tag;
//
//        private enum PacketID {
//            // Clientbound
//            // Handshaking
//            // NONE
//
//            // Status
//            RESPONSE, PONG,
//
//            // Login
//            DISCONNECT_LOGIN, ENCRYPTION_REQUEST, LOGIN_SUCCESS, SET_COMPRESSION, LOGIN_PLUGIN_REQUEST,
//
//            // Play
//            SPAWN_ENTITY, SPAWN_EXP_ORB, SPAWN_LIVING_ENTITY, SPAWN_PAINTING, SPAWN_PLAYER, SCULK_VIB_SIGNAL,
//            ENTITY_ANIM, STATISTICS, ACK_PLAYER_DIGGING, BLOCK_BREAK_ANIM, BLOCK_ENTITY_DATA, BLOCK_ACTION,
//            BLOCK_CHANGE, BOSS_BAR, SERVER_DIFFICULTY, CHAT_MESSAGE_CB, CLEAR_TITLES, TAB_COMPLETE_CB,
//            DECLARE_COMMANDS, CLOSE_WINDOW_CB, WINDOW_ITEMS, WINDOW_PROPERTY, SET_SLOT, SET_COOLDOWN,
//            PLUGIN_MESSAGE_CB, NAMED_SOUND_EFFECT, DISCONNECT_PLAY, ENTITY_STATUS, EXPLOSION, UNLOAD_CHUNK,
//            CHANGE_GAME_STATE, OPEN_HORSE_WINDOW, INIT_WORLD_BORDER, KEEP_ALIVE_CB, CHUNK_DAT_UPDATE_LIGHT,
//            EFFECT, PARTICLE, UPDATE_LIGHT, JOIN_GAME, MAP_DATA, TRADE_LIST, ENTITY_POS, ENTITY_POS_ROT,
//            ENTITY_ROT, VEHICLE_MOVE_CB, OPEN_BOOK, OPEN_WINDOW, OPEN_SIGN_EDITOR, PING_PLAY, CRAFT_RECIPE_RESPONSE,
//            PLAYER_ABILITIES_CB, END_COMBAT_EVENT, ENTER_COMBAT_EVENT, DEATH_COMBAT_EVENT, PLAYER_INFO, FACE_PLAYER,
//            PLAYER_POS_AND_LOOK, UNLOCK_RECIPES, DESTROY_ENTITIES, REMOVE_ENTITY_EFFECT, RESOURCE_PACK_SEND, RESPAWN,
//            ENTITY_HEAD_LO0K, MULTI_BLOCK_CHANGE, SELECT_ADVANCEMENT_TAB, ACTION_BAR, WORLD_BORDER_CENTER,
//            WORLD_BORDER_SIZE, WORLD_BORDER_WARNING_DELAY, WORLD_BORDER_WARNING_REACH, CAMERA, HELD_ITEM_CHANGE_CB,
//            UPDATE_VIEW_POS, UPDATE_VIEW_DISTANCE, SPAWN_POS, DISPLAY_SCOREBOARD, ENTITY_METADATA, ATTACH_ENTITY,
//            ENTITY_VELOCITY, ENTITY_EQUIPMENT, SET_EXPERIENCE, UPDATE_HEALTH, SCOREBOARD_OBJECTIVE, SET_PASSENGERS,
//            TEAMS, UPDATE_SCORE, UPDATE_SIMULATION_DISTANCE, SET_TITLE_SUBTITLE, TIME_UPDATE, SET_TITLE_TEXT,
//            SET_TITLE_TIMES, ENTITY_SOUND_EFFECT, SOUND_EFFECT, STOP_SOUND, PLAYER_LIST_HEADER_AND_FOOTER,
//            NBT_QUERY_RESPONSE, COLLECT_ITEM, ENTITY_TELEPORT, ADVANCEMENTS, ENTITY_PROPERTIES, ENTITY_EFFECT,
//            DECLARE_RECIPES, TAGS,
//
//            // Serverbound
//            // Handshaking
//            HANDSHAKING, LEGACY_SERVER_LIST_PING,
//
//            // Status
//            REQUEST, PING,
//
//            // Login
//            LOGIN_START, ENCRYPTION_RESPONSE, LOGIN_PLUGIN_RESPONSE,
//
//            // Play
//            TELEPORT_CONFIRM, QUERY_BLOCK_NBT, SET_DIFFICULTY, CHAT_MESSAGE_SB, CLIENT_STATUS, CLIENT_SETTINGS,
//            TAB_COMPLETE_SB, CLICK_WINDOW_BUTTON, CLICK_WINDOW, CLOSE_WINDOW_SB, PLUGIN_MESSAGE_SB, EDIT_BOOK, QUERY_ENTITY_NBT,
//            INTERACT_ENTITY, GENERATE_STRUCTURE, KEEP_ALIVE, LOCK_DIFFICULTY, PLAYER_POS, PLAYER_POS_AND_ROT,
//            PLAYER_ROT, PLAYER_MOVEMENT, VEHICLE_MOVE_SB, STEER_BOAT, PICK_ITEM, CRAFT_RECIPE_REQUEST,
//            PLAYER_ABILITIES_SB, PLAYER_DIGGING, ENTITY_ACTION, STEER_VEHICLE, PONG_PLAY, SET_RECIPE_BOOK_STATE,
//            SET_DISPLAYED_RECIPE, NAME_ITEM, RESOURCE_PACK_STATUS, ADVANCEMENT_TAB, SELECT_TRADE, SET_BEACON_EFFECT,
//            HELD_ITEM_CHANGE_SB, UPDATE_COMMAND_BLOCK, UPDATE_COMMAND_BLOCK_MINECART, CREATIVE_INVENTORY_ACTION,
//            UPDATE_JIGSAW_BLOCK, UPDATE_STRUCTURE_BLOCK, UPDATE_SIGN, ANIMATION_SB, SPECTATE, PLAYER_BLOCK_PLACEMENT,
//            USE_ITEM;
//        }
//
//        public enum State {
//            HANDSHAKING, STATUS, LOGIN, PLAY
//        }
//
//        public enum Binding {
//            CLIENT, SERVER
//        }
//
//        public Parser(ConcurrentLinkedQueue<Byte> queue, StreamHandler us, StreamHandler ds, String tag) {
//            byteQueue = queue;
//            this.us = us;
//            this.ds = ds;
//            this.tag = tag;
//        }
//
//        public byte readByte() {
//            while (byteQueue.isEmpty()) {}
//            return byteQueue.remove();
//        }
//
//        public int readVarInt() {
//            int value = 0;
//            int position = 0;
//            byte currentByte;
//
//            while (true) {
//                currentByte = (byte)(readByte() & 0xff);
//                value |= (currentByte & SEGMENT_BITS) << position;
//
//                if ((currentByte & CONTINUE_BIT) == 0) break;
//
//                position += 7;
//
//                if (position >= 32) throw new RuntimeException("VarInt is too big");
//            }
//
//            return value;
//        }
//
//        public long readVarLong() {
//            long value = 0;
//            int position = 0;
//            byte currentByte;
//
//            while (true) {
//                currentByte = (byte)(readByte() & 0xff);
//                value |= (long) (currentByte & SEGMENT_BITS) << position;
//
//                if ((currentByte & CONTINUE_BIT) == 0) break;
//
//                position += 7;
//
//                if (position >= 64) throw new RuntimeException("VarLong is too big");
//            }
//
//            return value;
//        }
//
//        public void parse() {
//            StringBuilder lineBuilder = new StringBuilder();
//            int packetBytesLeft = 0;
//            while (us.connected() && ds.connected()) {
//                if (packetBytesLeft == 0) {
//                    if (lineBuilder.length() != 0) {
//                        System.out.println(tag + " " + lineBuilder);
//                        lineBuilder.setLength(0);
//                    }
//                    packetBytesLeft = readVarInt();
//                    lineBuilder.append(String.format("S: %5d | ", packetBytesLeft));
//                } else {
//                    lineBuilder.append(String.format("%02X ", readByte() & 0xff));
//                    packetBytesLeft--;
//                }
//            }
//            System.out.println("ended");
//        }
//
////        public static void startHandshakingParse(Binding binding, byte data) {
////            switch (binding) {
////                case CLIENT: {
////
////                    break;
////                }
////                case SERVER: {
////
////                    break;
////                }
////            }
////        }
////
////        public static void startStatusParse(Binding binding, byte data) {
////            switch (binding) {
////                case CLIENT: {
////
////                    break;
////                }
////                case SERVER: {
////
////                    break;
////                }
////            }
////        }
////
////        public static void startLoginParse(Binding binding, byte data) {
////            switch (binding) {
////                case CLIENT: {
////
////                    break;
////                }
////                case SERVER: {
////
////                    break;
////                }
////            }
////        }
////
////        public static void startPlayParse(Binding binding, byte data) {
////            switch (binding) {
////                case CLIENT: {
////
////                    break;
////                }
////                case SERVER: {
////
////                    break;
////                }
////            }
////        }
////
////        public static void startParse(State state, Binding binding, byte data) {
////            switch (state) {
////                case HANDSHAKING: {
////                    startHandshakingParse(binding, data);
////                    break;
////                }
////                case STATUS: {
////                    startStatusParse(binding, data);
////                    break;
////                }
////                case LOGIN: {
////                    startLoginParse(binding, data);
////                    break;
////                }
////                case PLAY: {
////                    startPlayParse(binding, data);
////                    break;
////                }
////            }
////        }
//    }
//
//    private interface DataHandler {
//        void handle(int data) throws IOException, InterruptedException;
//    }
//}
