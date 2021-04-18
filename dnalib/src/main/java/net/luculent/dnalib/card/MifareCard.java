package net.luculent.dnalib.card;

import static com.android.hflibs.Mifare_native.AUTH_TYPEA;

/**
 * A/B卡
 */
public class MifareCard {
    public int authType;
    public int block;
    public byte[] key;

    public MifareCard() {
        authType = AUTH_TYPEA;
        block = 4;
        key = new byte[]{0x4c, 0x55, 0x43, 0x55, 0x43, 0x4c};
    }
}
