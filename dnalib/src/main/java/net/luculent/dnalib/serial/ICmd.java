//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.serial;

public interface ICmd {
    void modifySeq(float seq);

    void mofifySen(float sen);

    void sendCmd(String cmd);

    void sendCmd(ICmd.CmdTyp cmdTyp);

    public static enum CmdTyp {
        ACCELERATE("012001030203D6"),
        COLLECT_DRIFT("0121DE"),
        COLLECT_STAND("0122DD"),
        @Deprecated
        GET_SEQ("0111EE"),
        INNER_ACCELERATE("012401040202D2"),
        INNER_SHIFT("012403040202D0"),
        INNER_SPEED("012402040202D1"),
        MODIFY_SENSE("0149"),
        MODIFY_SEQ("0119"),
        READ_DEVID("01916E"),
        READ_DRIFT("0125DA"),
        READ_FIRMID("01906F"),
        READ_PARAM("01926D"),
        READ_SENSE("0140BF"),
        READ_STAND("0126D9"),
        SHAKE_INNER_CHECK("0123DC"),
        SHIFT("012003030203D4"),
        SPEED("012002030203D5"),
        TEMP35_CHECK("0115EA"),
        TEMP80_CHECK("0116E9"),
        TEMPERATURE("0110EF");

        String cmd;

        private CmdTyp(String cmd) {
            this.cmd = cmd;
        }

        public String getCmd() {
            return this.cmd;
        }
    }
}
