package proxy.packets.clientbound.play;

import proxy.packets.Packet;
import proxy.structs.nbt.NBT;
import proxy.structs.VarInt;

import java.util.Arrays;
import java.util.LinkedList;

import static util.Util.*;

public class PacketJoinGame extends Packet {
    private final Integer id;
    private final Boolean isHardcore;
    private final Byte gamemode;
    private final Byte previousGamemode;
    private final VarInt worldCount;
    private final String[] dimensionNames;
    private final NBT dimensionCodec;
    private final NBT dimension;
    private final String dimensionName;
    private final Long hashedSeed;
    private final VarInt maxPlayers;
    private final VarInt viewDistance;
    private final VarInt simulationDistance;
    private final Boolean reducedDebugInfo;
    private final Boolean enableRespawnScreen;
    private final Boolean isDebug;
    private final Boolean isFlat;

    public PacketJoinGame(LinkedList<Byte> bytes) {
        super(0x26);
        id = unpackInt(bytes);
        isHardcore = unpackBoolean(bytes);
        gamemode = bytes.remove();
        previousGamemode = bytes.remove();
        worldCount = new VarInt(bytes);
        dimensionNames = new String[worldCount.getValue()];
        for (int i = 0; i < dimensionNames.length; i++) {
            dimensionNames[i] = unpackString(bytes);
        }
        System.out.println(Arrays.toString(dimensionNames));
        dimensionCodec = new NBT(bytes);
        dimension = new NBT(bytes);
        dimensionName = unpackString(bytes);
        hashedSeed = unpackLong(bytes);
        maxPlayers = new VarInt(bytes);
        viewDistance = new VarInt(bytes);
        simulationDistance = new VarInt(bytes);
        reducedDebugInfo = unpackBoolean(bytes);
        enableRespawnScreen = unpackBoolean(bytes);
        isDebug = unpackBoolean(bytes);
        isFlat = unpackBoolean(bytes);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packInt(id, bytes);
        packBoolean(isHardcore, bytes);
        bytes.add(gamemode);
        bytes.add(previousGamemode);
        worldCount.pack(bytes);
        for (String s : dimensionNames) {
            packString(s, bytes);
        }
        dimensionCodec.pack(bytes);
        dimension.pack(bytes);
        packString(dimensionName, bytes);
        packLong(hashedSeed, bytes);
        maxPlayers.pack(bytes);
        viewDistance.pack(bytes);
        simulationDistance.pack(bytes);
        packBoolean(reducedDebugInfo, bytes);
        packBoolean(enableRespawnScreen, bytes);
        packBoolean(isDebug, bytes);
        packBoolean(isFlat, bytes);
    }

    @Override
    public String toString() {
        return "PacketJoinGame{" +
                "id=" + id +
                ", isHardcore=" + isHardcore +
                ", gamemode=" + gamemode +
                ", previousGamemode=" + previousGamemode +
                ", worldCount=" + worldCount +
                ", dimensionNames=" + Arrays.toString(dimensionNames) +
                ", dimensionCodec='" + dimensionCodec + '\'' +
                ", dimension='" + dimension + '\'' +
                ", dimensionName='" + dimensionName + '\'' +
                ", hashedSeed=" + hashedSeed +
                ", maxPlayers=" + maxPlayers +
                ", viewDistance=" + viewDistance +
                ", simulationDistance=" + simulationDistance +
                ", reducedDebugInfo=" + reducedDebugInfo +
                ", enableRespawnScreen=" + enableRespawnScreen +
                ", isDebug=" + isDebug +
                ", isFlat=" + isFlat +
                '}';
    }
}
