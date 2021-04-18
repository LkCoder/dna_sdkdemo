//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib;

public class RadixConverter {
    public RadixConverter() {
    }

    public static String arrayInt2String(int[] var0, String var1) {
        String var2 = "";
        int var3 = var0.length;
        String var6;
        if (var3 <= 0) {
            var6 = null;
        } else {
            for (int var4 = 0; var4 < var3; ++var4) {
                String var5 = String.format(var1, var0[var4]);
                var2 = var2 + var5;
            }

            var6 = var2;
        }

        return var6;
    }

    public static String bytes2HexString(byte[] var0) {
        StringBuilder var1 = new StringBuilder("");
        String var4;
        if (var0 != null && var0.length > 0) {
            for (int var2 = 0; var2 < var0.length; ++var2) {
                String var3 = Integer.toHexString(var0[var2] & 255);
                if (var3.length() < 2) {
                    var1.append(0);
                }

                var1.append(var3);
            }

            var4 = var1.toString();
        } else {
            var4 = "";
        }

        return var4;
    }

    public static String bytes2LrcString(byte[] var0) {
        String var5;
        if (var0 != null && var0.length > 0) {
            int var1 = 0;

            for (int var2 = 0; var2 < var0.length; ++var2) {
                var1 += var0[var2] & 255;
            }

            byte var6 = (byte) ((byte) (255 - (byte) var1) + 1);
            byte[] var3 = new byte[]{(byte) var6};
            byte[] var4 = new byte[var0.length + 1];
            System.arraycopy(var0, 0, var4, 0, var0.length);
            System.arraycopy(var3, 0, var4, var0.length, var3.length);
            var5 = bytes2HexString(var4);
        } else {
            var5 = null;
        }

        return var5;
    }

    public static double[] getArrayDoubleFromHexString(String var0, int var1) {
        Object var2 = null;
        double[] var3;
        if (var0 == null) {
            var3 = (double[]) var2;
        } else {
            var3 = (double[]) var2;
            if (!"".equals(var0)) {
                var3 = (double[]) var2;
                if (var0.length() % var1 == 0) {
                    if (var1 != 2) {
                        var3 = (double[]) var2;
                        if (var1 != 4) {
                            return var3;
                        }
                    }

                    int var4 = var0.length();
                    double[] var7 = new double[var4 / var1];
                    int var5 = 0;

                    while (true) {
                        var3 = var7;
                        if (var5 >= var4) {
                            break;
                        }

                        int var6 = Integer.parseInt(var0.substring(var5, var5 + var1), 16);
                        var7[var5 / var1] = (double) var6 * 1.0D;
                        var5 += var1;
                    }
                }
            }
        }

        return var3;
    }

    public static int[] getArrayIntFromHexString(String var0, int var1) {
        Object var2 = null;
        int[] var3;
        if (var0 == null) {
            var3 = (int[]) var2;
        } else {
            var3 = (int[]) var2;
            if (!"".equals(var0)) {
                var3 = (int[]) var2;
                if (var0.length() % var1 == 0) {
                    if (var1 != 2) {
                        var3 = (int[]) var2;
                        if (var1 != 4) {
                            return var3;
                        }
                    }

                    int var4 = var0.length();
                    int[] var7 = new int[var4 / var1];
                    int var5 = 0;

                    while (true) {
                        var3 = var7;
                        if (var5 >= var4) {
                            break;
                        }

                        int var6 = Integer.parseInt(var0.substring(var5, var5 + var1), 16);
                        var7[var5 / var1] = var6;
                        var5 += var1;
                    }
                }
            }
        }

        return var3;
    }

    public static double[] getGrayCodeFromHexString(String var0, int var1) {
        Object var2 = null;
        double[] var3;
        if (var0 == null) {
            var3 = (double[]) var2;
        } else {
            var3 = (double[]) var2;
            if (!"".equals(var0)) {
                var3 = (double[]) var2;
                if (var0.length() % var1 == 0) {
                    if (var1 != 2) {
                        var3 = (double[]) var2;
                        if (var1 != 4) {
                            return var3;
                        }
                    }

                    int var4 = var0.length();
                    double[] var8 = new double[var4 / var1];
                    int var5 = 0;

                    while (true) {
                        var3 = var8;
                        if (var5 >= var4) {
                            break;
                        }

                        int var6 = Integer.parseInt(var0.substring(var5, var5 + var1), 16);
                        int var7 = var6;
                        if (var6 > 32767) {
                            var7 = var6 - 65536;
                        }

                        var8[var5 / var1] = (double) ((short) var7) * 1.0D;
                        var5 += var1;
                    }
                }
            }
        }

        return var3;
    }

    public static int getGrayCodeFromIneger(int var0) {
        int var1 = var0;
        if (var0 > 58534) {
            var1 = var0 - 65536;
        }

        return var1;
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

    public static byte[] hexstring2Bytes(String var0) {
        byte[] var1 = new byte[var0.length() / 2];
        byte[] var3 = var0.getBytes();

        for (int var2 = 0; var2 < var3.length / 2; ++var2) {
            var1[var2] = uniteBytes(var3[var2 * 2], var3[var2 * 2 + 1]);
        }

        return var1;
    }

    public static int hexstring2Int(String var0) {
        short var1 = 0;
        if (var0.length() == 4) {
            byte[] var2 = hexstring2Bytes(var0);
            var1 = (short) ((short) ((var2[1] & 255) + 0) + (var2[0] << 8 & '\uff00'));
        }

        return var1;
    }

    public static int[] hexstring2Ints(String var0) {
        int[] var1 = null;
        int var2 = var0.length();
        if (var0.length() < 4) {
            var1 = null;
        } else if (var2 % 4 == 0) {
            byte[] var3 = hexstring2Bytes(var0);
            int var4 = var3.length;
            int[] var7 = new int[var4 / 2];
            var2 = 0;

            while (true) {
                var1 = var7;
                if (var2 >= var4) {
                    break;
                }

                byte var5 = var3[var2 + 1];
                byte var6 = var3[var2];
                var7[var2 / 2] = 0 + (var5 & 255) + (var6 << 8 & '\uff00');
                var2 += 2;
            }
        }

        return var1;
    }

    private static byte uniteBytes(byte var0, byte var1) {
        return (byte) ((byte) (Byte.decode("0x" + new String(new byte[]{var0})) << 4) ^ Byte.decode("0x" + new String(new byte[]{var1})));
    }
}
