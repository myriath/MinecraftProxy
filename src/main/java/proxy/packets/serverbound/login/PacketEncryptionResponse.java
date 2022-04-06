package proxy.packets.serverbound.login;

import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

public class PacketEncryptionResponse extends Packet {
    private final VarInt secretLength;
    private final LinkedList<Byte> secret;
    private final VarInt vtokenLength;
    private final LinkedList<Byte> vtoken;

    public PacketEncryptionResponse(LinkedList<Byte> bytes) {
        super(0x01);
        secretLength = new VarInt(bytes);
        secret = new LinkedList<>();
        for (int i = 0; i < secretLength.getValue(); i++) {
            secret.add(bytes.remove());
        }
        vtokenLength = new VarInt(bytes);
        vtoken = new LinkedList<>();
        for (int i = 0; i < vtokenLength.getValue(); i++) {
            secret.add(bytes.remove());
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        secretLength.pack(bytes);
        bytes.addAll(secret);
        vtokenLength.pack(bytes);
        bytes.addAll(vtoken);
    }

    @Override
    public String toString() {
        return "PacketEncryptionResponse{" +
                "secretLength=" + secretLength +
                ", secret=" + secret +
                ", vtokenLength=" + vtokenLength +
                ", vtoken=" + vtoken +
                '}';
    }
}
