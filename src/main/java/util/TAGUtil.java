package util;

import java.util.LinkedList;

import static util.Util.unpackShort;

public class TAGUtil {
    public static String readString(LinkedList<Byte> bytes) {
        Short len = unpackShort(bytes);
        if (len == null) return null;

        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes.remove() & 0xff);
        }
        return String.valueOf(chars);
    }
}
