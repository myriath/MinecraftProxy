package util;

import proxy.structs.VarInt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

public class Util {
    public static final int BOOLEAN_SIZE = 1;
    public static final int SHORT_SIZE = 2;
    public static final int INT_SIZE = 4;
    public static final int LONG_SIZE = 8;
    public static final int FLOAT_SIZE = 4;
    public static final int DOUBLE_SIZE = 8;

    public static void packString(String s, LinkedList<Byte> bytes) {
        if (s == null) return;
        new VarInt(s.length()).pack(bytes);
        for (byte b : s.getBytes()) {
            bytes.add(b);
        }
    }

    public static void packStringArray(String[] s, LinkedList<Byte> bytes) {
        if (s.length <= 0) return;
        for (String str : s) {
            packString(str, bytes);
        }
    }

    public static String unpackString(LinkedList<Byte> bytes) {
        Integer len = new VarInt(bytes).getValue();
        if (len == null) return null;
//        System.out.print(len + " ");

        char[] chars = new char[len];

        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes.remove() & 0xff);
        }

//        System.out.println(String.valueOf(chars));
        return String.valueOf(chars);
    }

    public static String[] unpackStringArray(int capacity, LinkedList<Byte> bytes) {
        if (bytes.isEmpty()) return null;
        String[] strings = new String[capacity];

        for (int i = 0; i < capacity; i++) {
            strings[i] = unpackString(bytes);
        }

        return strings;
    }


    public static void packShort(Short value, LinkedList<Byte> bytes) {
        if (value == null) return;
        for (byte b : pack(SHORT_SIZE).putShort(value).flip().array()) {
            bytes.add(b);
        }
    }

    public static void packInt(Integer value, LinkedList<Byte> bytes) {
        if (value == null) return;
        for (byte b : pack(INT_SIZE).putInt(value).flip().array()) {
            bytes.add(b);
        }
    }

    public static void packLong(Long value, LinkedList<Byte> bytes) {
        if (value == null) return;
        for (byte b : pack(LONG_SIZE).putLong(value).flip().array()) {
            bytes.add(b);
        }
    }

    public static void packFloat(Float value, LinkedList<Byte> bytes) {
        if (value == null) return;
        for (byte b : pack(FLOAT_SIZE).putFloat(value).flip().array()) {
            bytes.add(b);
        }
    }

    public static void packDouble(Double value, LinkedList<Byte> bytes) {
        if (value == null) return;
        for (byte b : pack(DOUBLE_SIZE).putDouble(value).flip().array()) {
            bytes.add(b);
        }
    }

    public static void packBoolean(boolean b, LinkedList<Byte> bytes) {
        bytes.add(b ? (byte) 0x01 : (byte) 0x00);
    }

    public static Short unpackShort(LinkedList<Byte> bytes) {
        if (bytes.size() < SHORT_SIZE) return null;
        return unpack(SHORT_SIZE, bytes).getShort();
    }

    public static Integer unpackInt(LinkedList<Byte> bytes) {
        if (bytes.size() < INT_SIZE) return null;
        return unpack(INT_SIZE, bytes).getInt();
    }

    public static Long unpackLong(LinkedList<Byte> bytes) {
        if (bytes.size() < LONG_SIZE) return null;
        return unpack(LONG_SIZE, bytes).getLong();
    }

    public static Float unpackFloat(LinkedList<Byte> bytes) {
        if (bytes.size() < FLOAT_SIZE) return null;
        return unpack(FLOAT_SIZE, bytes).getFloat();
    }

    public static Double unpackDouble(LinkedList<Byte> bytes) {
        if (bytes.size() < DOUBLE_SIZE) return null;
        return unpack(DOUBLE_SIZE, bytes).getDouble();
    }

    public static Boolean unpackBoolean(LinkedList<Byte> bytes) {
        if (bytes.size() < BOOLEAN_SIZE) return null;
        return bytes.remove() == (byte) 0x01;
    }


    private static ByteBuffer pack(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.BIG_ENDIAN);
    }

    public static ByteBuffer unpack(int capacity, LinkedList<Byte> bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(capacity).order(ByteOrder.BIG_ENDIAN);
        for (int i = 0; i < capacity; i++) {
            buffer.put(bytes.remove());
        }
        return buffer.flip();
    }
}
