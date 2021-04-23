//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.data;


import net.luculent.dnalib.RadixConverter;

public class TemperatureDataDecode {

    public static TemperatureDataDecode.TemperatureData_Laster Decode_Laser(String result) {
        TemperatureDataDecode.TemperatureData_Laster dataLaster = null;
        if (result.length() == 22) {
            dataLaster = new TemperatureDataDecode.TemperatureData_Laster();
            dataLaster.addr = Integer.parseInt(result.substring(0, 2), 16);
            dataLaster.bitCount = Integer.parseInt(result.substring(4, 8), 16);
            dataLaster.targetTemp = RadixConverter.getGrayCodeFromIneger(Integer.parseInt(result.substring(8, 12), 16));
            dataLaster.envirTemp = RadixConverter.getGrayCodeFromIneger(Integer.parseInt(result.substring(12, 16), 16));
            dataLaster.fsl = Integer.parseInt(result.substring(16, 20), 16);
            String lrc = result.substring(20, 22);
            if (!lrc.equals(RadixConverter.getLrcString(result.substring(0, 20)))) {
                dataLaster = null;
            } else {
                dataLaster.lrc = Integer.parseInt(lrc, 16);
            }
        }
        return dataLaster;
    }

    public static class TemperatureData_Laster {//温度和发射率目前都是100倍
        public int addr;
        public int bitCount;
        public int envirTemp;//环境温度
        public int fsl;//发射率
        public int lrc;
        public int targetTemp;//测量温度

        public TemperatureData_Laster() {
        }
    }
}
