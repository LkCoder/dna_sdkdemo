//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib;

public class DataConvertUtils {
    public DataConvertUtils() {
    }

    public static byte[] hexString2Bytes(String var0) {
        byte[] var1 = new byte[var0.length() / 2];
        byte[] var2 = var0.getBytes();

        for (int var3 = 0; var3 < var0.length() / 2; ++var3) {
            var1[var3] = uniteBytes(var2[var3 * 2], var2[var3 * 2 + 1]);
        }

        return var1;
    }

    public static String bytesToHexString(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        String var5;
        if (var0 != null && var0.length > 0) {
            for (int var2 = 0; var2 < var0.length; ++var2) {
                int var3;
                if (var0[var2] >= 0) {
                    var3 = var0[var2];
                } else {
                    var3 = var0[var2] + 256;
                }

                String var4 = Integer.toHexString(var3);
                if (var4.length() < 2) {
                    var1.append(0);
                }

                var1.append(var4);
            }

            var5 = var1.toString();
        } else {
            var5 = null;
        }

        return var5;
    }

    public static boolean checkValid(String var0) {
        boolean var1 = false;
        boolean var2 = var1;
        if (var0 != null) {
            if (var0.length() == 0) {
                var2 = var1;
            } else {
                int var3 = var0.length();
                int var4 = 0;

                for (int var5 = 0; var5 < var3; var5 += 2) {
                    int var6;
                    try {
                        var6 = Integer.parseInt(var0.substring(var5, var5 + 2), 16);
                    } catch (Exception var8) {
                        var8.printStackTrace();
                        continue;
                    }

                    var4 += var6;
                }

                var2 = var1;
                if (var4 != 0) {
                    var2 = var1;
                    if (var4 % 256 == 0) {
                        var2 = true;
                    }
                }
            }
        }

        return var2;
    }

    private static byte uniteBytes(byte var0, byte var1) {
        byte var2 = 0;

        byte var5;
        byte var6;
        try {
            StringBuilder var3 = new StringBuilder();
            var3 = var3.append("0x");
            String var4 = new String(new byte[]{var0});
            var5 = (byte) (Byte.decode(var3.append(var4).toString()) << 4);
            var3 = new StringBuilder();
            var3 = var3.append("0x");
            var4 = new String(new byte[]{var1});
            var6 = Byte.decode(var3.append(var4).toString());
        } catch (Exception var7) {
            var7.printStackTrace();
            var0 = var2;
            return var0;
        }

        byte var8 = (byte) (var5 ^ var6);
        var0 = var8;
        return var0;
    }

    public static String getLrcString(String var0) {
        if (!"".equals(var0) && var0.length() % 2 == 0) {
            int var1 = var0.length();
            int var2 = 0;

            for (int var3 = 0; var3 < var1; var3 += 2) {
                var2 = var2 + Integer.parseInt(var0.substring(var3, var3 + 2), 16) & 255;
            }

            var0 = String.format("%02x", 255 - var2 + 1);
        } else {
            var0 = null;
        }

        return var0;
    }
}
