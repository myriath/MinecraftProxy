package proxy.packets.clientbound.login;

import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class PacketEncryptionRequest extends Packet {
    private final String serverID;
    private final VarInt publicKeyLength;
    private final LinkedList<Byte> publicKey;
    private final VarInt vtokenLength;
    private final LinkedList<Byte> vtoken;

    public PacketEncryptionRequest(LinkedList<Byte> bytes) {
        super(0x01);
        serverID = unpackString(bytes);
        publicKeyLength = new VarInt(bytes);
        publicKey = new LinkedList<>();
        for (int i = 0; i < publicKeyLength.getValue(); i++) {
            publicKey.add(bytes.remove());
        }
        vtokenLength = new VarInt(bytes);
        vtoken = new LinkedList<>();
        for (int i = 0; i < vtokenLength.getValue(); i++) {
            vtoken.add(bytes.remove());
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packString(serverID, bytes);
        publicKeyLength.pack(bytes);
        bytes.addAll(publicKey);
        vtokenLength.pack(bytes);
        bytes.addAll(vtoken);
    }

    @Override
    public String toString() {
        return "PacketEncryptionRequest{" +
                "serverID='" + serverID + '\'' +
                ", pkeyLength=" + publicKeyLength +
                ", pkey=" + publicKey +
                ", vtokenLength=" + vtokenLength +
                ", vtoken=" + vtoken +
                '}';
    }
}
