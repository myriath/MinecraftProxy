package proxy.structs.nbt;

import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.NBTWriter;
import me.nullicorn.nedit.type.NBTCompound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import static util.Util.*;

public class NBT {
    private NBTCompound root;

    public NBT(LinkedList<Byte> bytes) {
//        for (Byte aByte : bytes) {
//            System.out.print(aByte + " ");
//        }
        try {
            root = NBTReader.read(new InputStream() {
                @Override
                public int read() {
                    return bytes.remove() & 0xff;
                }
            });
//            System.out.println(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pack(LinkedList<Byte> bytes) {
        LinkedList<Byte> temp = new LinkedList<>();
        try {
            NBTWriter.write(root, new OutputStream() {
                @Override
                public void write(int b) {
                    temp.add((byte) (b & 0xff));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (Byte aByte : temp) {
//            System.out.print(aByte + " ");
//        }
        bytes.addAll(temp);
//        try {
//            byte[] nbtArray = new Nbt().toByteArray(root);
//            for (byte b : nbtArray) {
//                bytes.add(b);
//            }
//        } catch (IOException ignored) {}
    }
}
