package com.linhndk.rust;

import com.linhndk.rust.entity.Profile;
import com.linhndk.rust.entity.UserSetting;
import com.linhndk.rust.exception.ApplicationDataException;
import com.linhndk.rust.frames.ProfileFrame;
import com.linhndk.rust.listeners.key.RustKeyListener;
import com.linhndk.rust.listeners.mouse.RustMouseListener;
import com.linhndk.rust.types.Status;
import com.linhndk.rust.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    public static Profile ActivatedProfile;
    public static Status STATUS = Status.NOT_ACTIVE;
    public static ProfileFrame Frame;
    public static boolean editingProfile = false;


    public static void main(String[] args) throws ApplicationDataException, IOException {
//        giveEverLasting();
        bootstrap();
    }

    private static void turnOffLog() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            JOptionPane.showMessageDialog(null, "Register native hook failed, exiting");
            System.exit(1);
        }
    }

    private static void stupidShit() throws IOException, ApplicationDataException {
//        int[] recoilTableY = new int[]{40, 48, 48, 48, 33, 33, 28, 24, 16, 13, 18, 22, 24, 25, 26, 26, 26, 26, 32, 28, 25, 25, 24, 28, 28, 30, 30, 30, 27};
//        int[] recoilTableX = new int[]{-36, 5, -59, -49, 3, 20, 25, 45, 43, 32, 82, 8, 43, -32, -28, -42, -45, -46, -45, -46, -48, -55, -25, 15, 20, 35, 50, 62, 40};
//
//        for (int i = 0; i < recoilTableX.length; i++) {
//            System.out.println((int) Math.ceil(recoilTableX[i] * 2.45) + "," + (int) Math.ceil(recoilTableY[i] * 2.45));
//        }
//        File file = new File("data.txt");
//        String raw = FileUtils.readFileToString(file, (String) null);
//        String[] splited = raw.trim().split("\n");
//        for (int i = 0; i < splited.length - 1; i++) {
//            int prev1 = Integer.parseInt(splited[i].trim().split(",")[0]);
//            int prev2 = Integer.parseInt(splited[i].trim().split(",")[1]);
//            int after1 = Integer.parseInt(splited[i + 1].trim().split(",")[0]);
//            int after2 = Integer.parseInt(splited[i + 1].trim().split(",")[1]);
//            System.out.println((int) ((prev1 - after1) * 1.0) + "," + (int) ((prev2 - after2) * 1.0));
//        }
        String raw = "-20, 30, 4, 29, -33, 26, -23, 26, -2, 18, 10, 17, 21, 16, 21, 15, 25, 9, 23, 9, 25, 3, 21, 8, 9, 18, 0, 20, -12, 21, -18, 20, -22, 22, -23, 16, -30, 12, -30, 9, -23, 3, -21, 2, -14, 5, -5, 16, 5, 12, 21, 16, 30, 15, 29, 14, 21, 16";
        raw = raw.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\\(", "").replaceAll("\\)", "");
        String[] splited = raw.trim().split(",");
        for (int i = 0; i < splited.length - 1; i = i + 2) {
            System.out.println((int) (Integer.parseInt(splited[i].trim()) * 1.0) + "," + (int) (Integer.parseInt(splited[i + 1].trim()) * 1.0));
        }
//        for (int i = 0; i < 7; i++) {
//            System.out.println("-20,80\n" +
//                    "-10,80");
//        }
    }

    private static void bootstrap() {
        turnOffLog();
        GlobalScreen.addNativeKeyListener(new RustKeyListener());
        GlobalScreen.addNativeMouseListener(new RustMouseListener());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Exception: " + e.getLocalizedMessage());
        }
        EventQueue.invokeLater(() -> Frame = new ProfileFrame(authenticate()));
    }

    private static void giveEverLasting() throws ApplicationDataException, IOException {
        UserSetting userSetting = Utils.loadUserSettingRaw();
        userSetting.setEverLasting(true);
        Utils.saveUserSettingRaw(userSetting);
        File file = new File(System.getProperty("user.home") + "/client.properties");
        FileUtils.writeByteArrayToFile(file, "0".getBytes());
    }

    private static UserSetting authenticate() {
        File file = new File(System.getProperty("user.home") + "/client.properties");
        if (file.exists()) {
            try {
                if (!FileUtils.readFileToString(file, (String) null).equals("0") && (new Date().getTime() - new Date(Long.parseLong(FileUtils.readFileToString(file, (String) null))).getTime()) > 604800000) {
                    JOptionPane.showMessageDialog(null, "Evaluation is over, ask admin for an everlasting account");
                    System.exit(0);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Read authenticated file failed, exiting");
                System.exit(1);
            }
        } else {
            try {
                file.createNewFile();
                FileUtils.writeByteArrayToFile(file, String.format("%d", new Date().getTime()).getBytes());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Write authenticated file failed, exiting");
                System.exit(1);
            }
        }
        UserSetting userSetting = null;
        try {
            userSetting = Utils.loadUserSettingRaw();
        } catch (ApplicationDataException e) {
            JOptionPane.showMessageDialog(null, "Exception: " + e.getLocalizedMessage());
        }
        if (userSetting == null) {
            file = new File("app.dat");
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Create app.dat failed, exiting");
                System.exit(1);
            }
            userSetting = new UserSetting();
            userSetting.setEverLasting(false);
            try {
                userSetting.setMacAddress(Utils.getMacAddress());
            } catch (UnknownHostException | SocketException e) {
                JOptionPane.showMessageDialog(null, "Can not get unique user, exiting");
                System.exit(1);
            }
            try {
                Utils.saveUserSettingRaw(userSetting);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Exception: " + e.getLocalizedMessage());
            }
        } else {
            if (!userSetting.isEverLasting()) {
                try {
                    if (!Utils.getMacAddress().equals(userSetting.getMacAddress())) {
                        JOptionPane.showMessageDialog(null, "Hmm, who are you ?, exiting");
                        System.exit(0);
                    }
                } catch (UnknownHostException | SocketException e) {
                    JOptionPane.showMessageDialog(null, "Can not get unique user, exiting");
                    System.exit(1);
                }
            }
        }
        return userSetting;
    }
}
