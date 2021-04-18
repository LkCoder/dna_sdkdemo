//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.serial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeviceControl {
    private BufferedWriter CtrlFile;

    DeviceControl(String path) throws IOException {
        this.CtrlFile = new BufferedWriter(new FileWriter(new File(path), false));
    }

    public void DeviceClose() throws IOException {
        this.CtrlFile.close();
    }

    public void MTGpioOff() throws IOException {
        this.CtrlFile.write("-wdout64 0");
        this.CtrlFile.flush();
    }

    public void MTGpioOn() throws IOException {
        this.CtrlFile.write("-wdout64 1");
        this.CtrlFile.flush();
    }

    public void PowerOffDevice(String cmd) throws IOException {
        this.CtrlFile.write(cmd);
        this.CtrlFile.flush();
    }

    public void PowerOnDevice(String cms) throws IOException {
        this.CtrlFile.write(cms);
        this.CtrlFile.flush();
    }

    public void TriggerOffDevice() throws IOException {
        this.CtrlFile.write("trigoff");
        this.CtrlFile.flush();
    }

    public void TriggerOnDevice() throws IOException {
        this.CtrlFile.write("trig");
        this.CtrlFile.flush();
    }
}
