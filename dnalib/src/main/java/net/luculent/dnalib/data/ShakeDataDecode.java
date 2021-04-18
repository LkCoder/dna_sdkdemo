//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.data;

import net.luculent.dnalib.RadixConverter;

public class ShakeDataDecode {

    private int getLength(int len) {
        return 1024 << len - 1;
    }

    public ShakeDataDecode.ShakeData Decode(String result) {
        ShakeDataDecode.ShakeData data = null;
        try {
            data = new ShakeData();
            data.addr = Integer.parseInt(result.substring(0, 2), 16);
            data.mode = Integer.parseInt(result.substring(4, 6), 16);
            data.freq = Integer.parseInt(result.substring(6, 8), 16);
            int dataLength = Integer.parseInt(result.substring(10, 12), 16);
            data.dataLength = dataLength;
            dataLength = this.getLength(dataLength) * 4;
            data.isOverRun = Integer.parseInt(result.substring(dataLength + 16 + 4, dataLength + 16 + 6), 16);
            data.dataType = Integer.parseInt(result.substring(8, 10), 16);
            data.valString = result.substring(dataLength + 16, dataLength + 16 + 4);
            data.value = Integer.parseInt(data.valString, 16);
            String var8 = result.substring(0, dataLength + 22);
            if (result.substring(dataLength + 22, dataLength + 22 + 2).equals(RadixConverter.getLrcString(var8))) {
                data.datas = RadixConverter.getGrayCodeFromHexString(result.substring(16, dataLength + 16), 4);
            } else {
                data.datas = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static class ShakeData {
        public int addr;
        public int dataLength;
        public int dataType;
        public double[] datas;
        public int freq;
        public int isOverRun;
        public int mode;
        public String valString;
        public int value;

        public ShakeData() {
        }
    }
}
