package util;

import proxy.Proxy;
import proxy.packets.Packet;
import proxy.packets.clientbound.handshaking.PacketUnknown;
import proxy.packets.clientbound.login.*;
import proxy.packets.clientbound.play.PacketDeclareRecipes;
import proxy.packets.clientbound.play.PacketJoinGame;
import proxy.packets.clientbound.status.PacketPong;
import proxy.packets.clientbound.status.PacketResponse;
import proxy.packets.serverbound.handshaking.PacketHandshake;
import proxy.packets.serverbound.login.PacketEncryptionResponse;
import proxy.packets.serverbound.login.PacketLoginPluginResponse;
import proxy.packets.serverbound.login.PacketLoginStart;
import proxy.packets.serverbound.status.PacketPing;
import proxy.packets.serverbound.status.PacketRequest;
import proxy.structs.VarInt;

import java.util.LinkedList;

public class PacketUtil {
    public static Packet generate(Proxy.State state, Proxy.Binding binding, LinkedList<Byte> data) {
//        System.out.println(Arrays.toString(bytes));
        int id = new VarInt(data).getValue();
        switch (binding) {
            case CLIENT -> {
                switch (state) {
                    case HANDSHAKING -> {
                        return new PacketUnknown(id, data, "Unknown");
                    }
                    case STATUS -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketResponse(data);
                            }
                            case 0x01 -> {
                                return new PacketPong(data);
                            }
                        }
                    }
                    case LOGIN -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketDisconnectLogin(data);
                            }
                            case 0x01 -> {
                                return new PacketEncryptionRequest(data);
                            }
                            case 0x02 -> {
                                return new PacketLoginSuccess(data);
                            }
                            case 0x03 -> {
                                return new PacketSetCompression(data);
                            }
                            case 0x04 -> {
                                return new PacketLoginPluginRequest(data);
                            }
                        }
                    }
                    case PLAY -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketUnknown(id, data, "Spawn Entity");
                            }
                            case 0x01 -> {
                                return new PacketUnknown(id, data, "Spawn EXP Orb");
                            }
                            case 0x02 -> {
                                return new PacketUnknown(id, data, "Spawn Living Entity");
                            }
                            case 0x03 -> {
                                return new PacketUnknown(id, data, "Spawn Painting");
                            }
                            case 0x04 -> {
                                return new PacketUnknown(id, data, "Spawn Player");
                            }
                            case 0x05 -> {
                                return new PacketUnknown(id, data, "Sculker Vibration Signal");
                            }
                            case 0x06 -> {
                                return new PacketUnknown(id, data, "Entity Animation");
                            }
                            case 0x07 -> {
                                return new PacketUnknown(id, data, "Statistics");
                            }
                            case 0x08 -> {
                                return new PacketUnknown(id, data, "Acknowledge Player Digging");
                            }
                            case 0x09 -> {
                                return new PacketUnknown(id, data, "Block Break Animation");
                            }
                            case 0x0a -> {
                                return new PacketUnknown(id, data, "Block Entity Data");
                            }
                            case 0x0b -> {
                                return new PacketUnknown(id, data, "Block Action");
                            }
                            case 0x0c -> {
                                return new PacketUnknown(id, data, "Block Change");
                            }
                            case 0x0d -> {
                                return new PacketUnknown(id, data, "Boss Bar");
                            }
                            case 0x0e -> {
                                return new PacketUnknown(id, data, "Server Difficulty");
                            }
                            case 0x0f -> {
                                return new PacketUnknown(id, data, "Chat Message (clientbound)");
                            }
                            case 0x10 -> {
                                return new PacketUnknown(id, data, "Clear Titles");
                            }
                            case 0x11 -> {
                                return new PacketUnknown(id, data, "Tab Complete (clientbound)");
                            }
                            case 0x12 -> {
                                return new PacketUnknown(id, data, "Declare Commands");
                            }
                            case 0x13 -> {
                                return new PacketUnknown(id, data, "Close Window (clientbound)");
                            }
                            case 0x14 -> {
                                return new PacketUnknown(id, data, "Window Items");
                            }
                            case 0x15 -> {
                                return new PacketUnknown(id, data, "Window Property");
                            }
                            case 0x16 -> {
                                return new PacketUnknown(id, data, "Set Slot");
                            }
                            case 0x17 -> {
                                return new PacketUnknown(id, data, "Set Cooldown");
                            }
                            case 0x18 -> {
                                return new PacketUnknown(id, data, "Plugin Message (clientbound)");
                            }
                            case 0x19 -> {
                                return new PacketUnknown(id, data, "Named Sound Effect");
                            }
                            case 0x1a -> {
                                return new PacketUnknown(id, data, "Disconnect (play)");
                            }
                            case 0x1b -> {
                                return new PacketUnknown(id, data, "Entity Status");
                            }
                            case 0x1c -> {
                                return new PacketUnknown(id, data, "Explosion");
                            }
                            case 0x1d -> {
                                return new PacketUnknown(id, data, "Unload Chunk");
                            }
                            case 0x1e -> {
                                return new PacketUnknown(id, data, "Change Game State");
                            }
                            case 0x1f -> {
                                return new PacketUnknown(id, data, "Open Horse Window");
                            }
                            case 0x20 -> {
                                return new PacketUnknown(id, data, "Initialize World Border");
                            }
                            case 0x21 -> {
                                return new PacketUnknown(id, data, "Keep Alive (clientbound)");
                            }
                            case 0x22 -> {
                                return new PacketUnknown(id, data, "Chunk Data and Update Light");
                            }
                            case 0x23 -> {
                                return new PacketUnknown(id, data, "Effect");
                            }
                            case 0x24 -> {
                                return new PacketUnknown(id, data, "Particle");
                            }
                            case 0x25 -> {
                                return new PacketUnknown(id, data, "Update Light");
                            }
                            case 0x26 -> {
                                return new PacketJoinGame(data);
                            }
                            case 0x27 -> {
                                return new PacketUnknown(id, data, "Map Data");
                            }
                            case 0x28 -> {
                                return new PacketUnknown(id, data, "Trade List");
                            }
                            case 0x29 -> {
                                return new PacketUnknown(id, data, "Entity Position");
                            }
                            case 0x2a -> {
                                return new PacketUnknown(id, data, "Entity Position and Rotation");
                            }
                            case 0x2b -> {
                                return new PacketUnknown(id, data, "Entity Rotation");
                            }
                            case 0x2c -> {
                                return new PacketUnknown(id, data, "Vehicle Move (clientbound)");
                            }
                            case 0x2d -> {
                                return new PacketUnknown(id, data, "Open Book");
                            }
                            case 0x2e -> {
                                return new PacketUnknown(id, data, "Open Window");
                            }
                            case 0x2f -> {
                                return new PacketUnknown(id, data, "Open Sign Editor");
                            }
                            case 0x30 -> {
                                return new PacketUnknown(id, data, "Ping Play");
                            }
                            case 0x31 -> {
                                return new PacketUnknown(id, data, "Craft Recipe Response");
                            }
                            case 0x32 -> {
                                return new PacketUnknown(id, data, "Player Abilities (clientbound)");
                            }
                            case 0x33 -> {
                                return new PacketUnknown(id, data, "End Combat Event");
                            }
                            case 0x34 -> {
                                return new PacketUnknown(id, data, "Enter Combat Event");
                            }
                            case 0x35 -> {
                                return new PacketUnknown(id, data, "Death Combat Event");
                            }
                            case 0x36 -> {
                                return new PacketUnknown(id, data, "Player Info");
                            }
                            case 0x37 -> {
                                return new PacketUnknown(id, data, "Face Player");
                            }
                            case 0x38 -> {
                                return new PacketUnknown(id, data, "Player Position and Look");
                            }
                            case 0x39 -> {
                                return new PacketUnknown(id, data, "Unlock Recipes");
                            }
                            case 0x3a -> {
                                return new PacketUnknown(id, data, "Destroy Entities");
                            }
                            case 0x3b -> {
                                return new PacketUnknown(id, data, "Remove Entity Effect");
                            }
                            case 0x3c -> {
                                return new PacketUnknown(id, data, "Resource Pack Send");
                            }
                            case 0x3d -> {
                                return new PacketUnknown(id, data, "Respawn");
                            }
                            case 0x3e -> {
                                return new PacketUnknown(id, data, "Entity Head Look");
                            }
                            case 0x3f -> {
                                return new PacketUnknown(id, data, "Multi Block Change");
                            }
                            case 0x40 -> {
                                return new PacketUnknown(id, data, "Select Advancement Tab");
                            }
                            case 0x41 -> {
                                return new PacketUnknown(id, data, "Action Bar");
                            }
                            case 0x42 -> {
                                return new PacketUnknown(id, data, "World Border Center");
                            }
                            case 0x43 -> {
                                return new PacketUnknown(id, data, "World Border Lerp Size");
                            }
                            case 0x44 -> {
                                return new PacketUnknown(id, data, "World Border Size");
                            }
                            case 0x45 -> {
                                return new PacketUnknown(id, data, "World Border Warning Delay");
                            }
                            case 0x46 -> {
                                return new PacketUnknown(id, data, "World Border Warning Reach");
                            }
                            case 0x47 -> {
                                return new PacketUnknown(id, data, "Camera");
                            }
                            case 0x48 -> {
                                return new PacketUnknown(id, data, "Held Item Change (clientbound)");
                            }
                            case 0x49 -> {
                                return new PacketUnknown(id, data, "Update View Position");
                            }
                            case 0x4a -> {
                                return new PacketUnknown(id, data, "Update View Distance");
                            }
                            case 0x4b -> {
                                return new PacketUnknown(id, data, "Spawn Position");
                            }
                            case 0x4c -> {
                                return new PacketUnknown(id, data, "Display Scoreboard");
                            }
                            case 0x4d -> {
                                return new PacketUnknown(id, data, "Entity Metadata");
                            }
                            case 0x4e -> {
                                return new PacketUnknown(id, data, "Attach Entity");
                            }
                            case 0x4f -> {
                                return new PacketUnknown(id, data, "Entity Velocity");
                            }
                            case 0x50 -> {
                                return new PacketUnknown(id, data, "Entity Equipment");
                            }
                            case 0x51 -> {
                                return new PacketUnknown(id, data, "Set Experience");
                            }
                            case 0x52 -> {
                                return new PacketUnknown(id, data, "Update Health");
                            }
                            case 0x53 -> {
                                return new PacketUnknown(id, data, "Scoreboard Objective");
                            }
                            case 0x54 -> {
                                return new PacketUnknown(id, data, "Set Passengers");
                            }
                            case 0x55 -> {
                                return new PacketUnknown(id, data, "Teams");
                            }
                            case 0x56 -> {
                                return new PacketUnknown(id, data, "Update Score");
                            }
                            case 0x57 -> {
                                return new PacketUnknown(id, data, "Update Simulation Distance");
                            }
                            case 0x58 -> {
                                return new PacketUnknown(id, data, "Set Title Subtitle");
                            }
                            case 0x59 -> {
                                return new PacketUnknown(id, data, "Time Update");
                            }
                            case 0x5a -> {
                                return new PacketUnknown(id, data, "Set Title Text");
                            }
                            case 0x5b -> {
                                return new PacketUnknown(id, data, "Set Title Times");
                            }
                            case 0x5c -> {
                                return new PacketUnknown(id, data, "Entity Sound Effect");
                            }
                            case 0x5d -> {
                                return new PacketUnknown(id, data, "Sound Effect");
                            }
                            case 0x5e -> {
                                return new PacketUnknown(id, data, "Stop Sound");
                            }
                            case 0x5f -> {
                                return new PacketUnknown(id, data, "Player List Header and Footer");
                            }
                            case 0x60 -> {
                                return new PacketUnknown(id, data, "NBT Query Response");
                            }
                            case 0x61 -> {
                                return new PacketUnknown(id, data, "Collect Item");
                            }
                            case 0x62 -> {
                                return new PacketUnknown(id, data, "Entity Teleport");
                            }
                            case 0x63 -> {
                                return new PacketUnknown(id, data, "Advancements");
                            }
                            case 0x64 -> {
                                return new PacketUnknown(id, data, "Entity Properties");
                            }
                            case 0x65 -> {
                                return new PacketUnknown(id, data, "Entity Effect");
                            }
                            case 0x66 -> {
                                return new PacketDeclareRecipes(data);
                            }
                            case 0x67 -> {
                                return new PacketUnknown(id, data, "Tags");
                            }
                        }
                    }
                }
            } case SERVER -> {
                switch (state) {
                    case HANDSHAKING -> {
                        if (id == 0x00) {
//                            return new PacketUnknown(id, data, "Handshake");
                            return new PacketHandshake(data);
                        }
                    }
                    case STATUS -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketRequest();
                            }
                            case 0x01 -> {
                                return new PacketPing(data);
                            }
                        }
                    }
                    case LOGIN -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketLoginStart(data);
                            }
                            case 0x01 -> {
                                return new PacketEncryptionResponse(data);
                            }
                            case 0x02 -> {
                                return new PacketLoginPluginResponse(data);
                            }
                        }
                    }
                    case PLAY -> {
                        switch (id) {
                            case 0x00 -> {
                                return new PacketUnknown(id, data, "Teleport Confirm");
                            }
                            case 0x01 -> {
                                return new PacketUnknown(id, data, "Query Block NBT");
                            }
                            case 0x02 -> {
                                return new PacketUnknown(id, data, "Set Difficulty");
                            }
                            case 0x03 -> {
                                return new PacketUnknown(id, data, "Chat Message (serverbound)");
                            }
                            case 0x04 -> {
                                return new PacketUnknown(id, data, "Client Status");
                            }
                            case 0x05 -> {
                                return new PacketUnknown(id, data, "Client Settings");
                            }
                            case 0x06 -> {
                                return new PacketUnknown(id, data, "Tab Complete (serverbound)");
                            }
                            case 0x07 -> {
                                return new PacketUnknown(id, data, "Click Window Button");
                            }
                            case 0x08 -> {
                                return new PacketUnknown(id, data, "Click Window");
                            }
                            case 0x09 -> {
                                return new PacketUnknown(id, data, "Close Window (serverbound)");
                            }
                            case 0x0a -> {
                                return new PacketUnknown(id, data, "Plugin Message (serverbound)");
                            }
                            case 0x0b -> {
                                return new PacketUnknown(id, data, "Edit Book");
                            }
                            case 0x0c -> {
                                return new PacketUnknown(id, data, "Query Entity NBT");
                            }
                            case 0x0d -> {
                                return new PacketUnknown(id, data, "Interact Entity");
                            }
                            case 0x0e -> {
                                return new PacketUnknown(id, data, "Generate Structure");
                            }
                            case 0x0f -> {
                                return new PacketUnknown(id, data, "Keep Alive (serverbound)");
                            }
                            case 0x10 -> {
                                return new PacketUnknown(id, data, "Lock Difficulty");
                            }
                            case 0x11 -> {
                                return new PacketUnknown(id, data, "Player Position");
                            }
                            case 0x12 -> {
                                return new PacketUnknown(id, data, "Player Position and Rotation (serverbound)");
                            }
                            case 0x13 -> {
                                return new PacketUnknown(id, data, "Player Rotation");
                            }
                            case 0x14 -> {
                                return new PacketUnknown(id, data, "Player Movement");
                            }
                            case 0x15 -> {
                                return new PacketUnknown(id, data, "Vehicle Move (serverbound)");
                            }
                            case 0x16 -> {
                                return new PacketUnknown(id, data, "Steer Boat");
                            }
                            case 0x17 -> {
                                return new PacketUnknown(id, data, "Pick Item");
                            }
                            case 0x18 -> {
                                return new PacketUnknown(id, data, "Craft Recipe Request");
                            }
                            case 0x19 -> {
                                return new PacketUnknown(id, data, "Player Abilities (serverbound)");
                            }
                            case 0x1a -> {
                                return new PacketUnknown(id, data, "Player Digging");
                            }
                            case 0x1b -> {
                                return new PacketUnknown(id, data, "Entity Action");
                            }
                            case 0x1c -> {
                                return new PacketUnknown(id, data, "Steer Vehicle");
                            }
                            case 0x1d -> {
                                return new PacketUnknown(id, data, "Pong (play)");
                            }
                            case 0x1e -> {
                                return new PacketUnknown(id, data, "Set Recipe Book State");
                            }
                            case 0x1f -> {
                                return new PacketUnknown(id, data, "Set Displayed Recipe");
                            }
                            case 0x20 -> {
                                return new PacketUnknown(id, data, "Name Item");
                            }
                            case 0x21 -> {
                                return new PacketUnknown(id, data, "Resource Pack Status");
                            }
                            case 0x22 -> {
                                return new PacketUnknown(id, data, "Advancement Tab");
                            }
                            case 0x23 -> {
                                return new PacketUnknown(id, data, "Select Trade");
                            }
                            case 0x24 -> {
                                return new PacketUnknown(id, data, "Set Beacon Effect");
                            }
                            case 0x25 -> {
                                return new PacketUnknown(id, data, "Held Item Change (serverbound)");
                            }
                            case 0x26 -> {
                                return new PacketUnknown(id, data, "Update Command Block");
                            }
                            case 0x27 -> {
                                return new PacketUnknown(id, data, "Update Command Block Minecart");
                            }
                            case 0x28 -> {
                                return new PacketUnknown(id, data, "Creative Inventory Action");
                            }
                            case 0x29 -> {
                                return new PacketUnknown(id, data, "Update Jigsaw Block");
                            }
                            case 0x2a -> {
                                return new PacketUnknown(id, data, "Update Structure Block");
                            }
                            case 0x2b -> {
                                return new PacketUnknown(id, data, "Update Sign");
                            }
                            case 0x2c -> {
                                return new PacketUnknown(id, data, "Animation (serverbound)");
                            }
                            case 0x2d -> {
                                return new PacketUnknown(id, data, "Spectate");
                            }
                            case 0x2e -> {
                                return new PacketUnknown(id, data, "Player Block Placement");
                            }
                            case 0x2f -> {
                                return new PacketUnknown(id, data, "Use Item");
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(id);
        return new PacketUnknown(id, data, "Who the heck knows");
    }
}
