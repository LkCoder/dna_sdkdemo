package net.luculent.dnalib.card;

import com.speedata.r6lib.IMifareManager;
import com.speedata.r6lib.R6Manager;

import net.luculent.dnalib.DataConvertUtils;

public class CardHelper {
    private MifareCard card;
    private IMifareManager dev;

    private CardHelper(MifareCard card) {
        this.card = card;
    }

    public static CardHelper withCard(MifareCard card) {
        return new CardHelper(card);
    }

    public boolean init() {
        this.dev = R6Manager.getMifareInstance(R6Manager.CardType.MIFARE);
        return dev.InitDev() == 0;
    }

    public byte[] search() {
        return dev.SearchCard();
    }

    public boolean auth(byte[] cid) {
        return dev.AuthenticationCardByKey(card.authType, cid, card.block, card.key) == 0;
    }

    public String read() {
        byte[] cid = search();
        if (cid == null) {
            return "no card find";
        }
        if (!auth(cid)) {
            return "card auth failed";
        }
        byte[] data = dev.ReadBlock(card.block);
        return DataConvertUtils.bytesToHexString(data);
    }

    public void release() {
        dev.ReleaseDev();
    }
}
