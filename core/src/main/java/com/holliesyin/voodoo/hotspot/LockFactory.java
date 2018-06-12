package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018/6/12.
 */
public class LockFactory {
    private static final int LOCK_SIZE = 16;
    private static Object[][] LOCK_HOLDER = new Object[LockType.values().length][LOCK_SIZE];

    private enum LockType {
        DATABUS,SUBUNSUB;
    }

    static {
        for (int i = 0; i < LockType.values().length; i++) {
            for (int j = 0; j < LOCK_SIZE; j++) {
                LOCK_HOLDER[i][j] = i;
            }
        }
    }

    public static Object getDataBusLock(String busName) {
        return LOCK_HOLDER[LockType.DATABUS.ordinal()][Math.abs(busName.hashCode()) % LOCK_SIZE];
    }

    public static Object getSubAndUnSubLock(){
        return 0;
    }
}