package proxy.structs.nbt;

import java.util.LinkedList;

import static util.TAGUtil.*;
import static util.Util.*;

public class TAGShort extends TAG {
    private final Short value;

    public TAGShort(LinkedList<Byte> bytes, boolean named) {
        super(named ? readString(bytes) : null, 0x02);
        value = unpackShort(bytes);
    }
}
