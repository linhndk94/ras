package com.linhndk.rust.thread;

import com.linhndk.rust.Application;
import com.linhndk.rust.entity.Coordinate;
import com.linhndk.rust.entity.Profile;
import com.linhndk.rust.types.FireMode;
import com.linhndk.rust.types.Status;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RustRecoilSupportThread extends Thread {

    public static Robot robot;
    private boolean shouldHalt;
    public static int verticalCenter = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
    public static int horizontalCenter = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
    private int bulletCount;
    private Profile profile;
    private int systemX;
    private int systemY;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Init robot failed, exiting");
            System.exit(1);
        }
    }

    public RustRecoilSupportThread(Profile profile, int x, int y) {
        this.shouldHalt = false;
        this.bulletCount = 0;
        this.profile = profile;
        this.systemX = x;
        this.systemY = y;
    }

    public void halt() {
        this.shouldHalt = true;
    }

    private void mouseMove(Coordinate coordinate) {
        if (profile.getFireMode() == FireMode.SEMI) {
            smoothMouseMove((int) (coordinate.getX() / 2 * profile.getSensitivityMultiplier()), (int) (coordinate.getY() / 2 * profile.getSensitivityMultiplier()), (profile.getTimeBetweenShot() + ThreadLocalRandom.current().nextInt(profile.getRmin(), profile.getRmax())) / 2);
        } else if (profile.getFireMode() == FireMode.AUTO) {
            smoothMouseMove((int) (coordinate.getX() * profile.getSensitivityMultiplier()), (int) (coordinate.getY() * profile.getSensitivityMultiplier()), profile.getTimeBetweenShot());
        }
    }

    private void smoothMouseMove(int x, int y, long time) {
        if (!Application.isCrouching && (profile.getFireMode() == FireMode.SEMI || profile.getProfileName().equalsIgnoreCase("M249"))) {
            x = x * 2;
            y = y * 2;
        }
        int step = profile.getSteps();
        int modx = x % step;
        int mody = y % step;
        int divx = x / step;
        int divy = y / step;
        for (int i = 0; i < step; i++) {
            robot.mouseMove(horizontalCenter + divx, verticalCenter + divy);
//            if (modx != 0 && mody != 0) {
//                robot.mouseMove(horizontalCenter + (modx > 0 ? 1 : -1), verticalCenter + (mody > 0 ? 1 : -1));
//                if (modx > 0) {
//                    modx--;
//                } else {
//                    modx++;
//                }
//                if (mody > 0) {
//                    mody--;
//                } else {
//                    mody++;
//                }
//            } else if (modx != 0) {
//                robot.mouseMove(horizontalCenter + (modx > 0 ? 1 : -1), verticalCenter);
//                if (modx > 0) {
//                    modx--;
//                } else {
//                    modx++;
//                }
//            } else if (mody != 0) {
//                robot.mouseMove(horizontalCenter, verticalCenter + (mody > 0 ? 1 : -1));
//                if (mody > 0) {
//                    mody--;
//                } else {
//                    mody++;
//                }
//            }
            try {
                Thread.sleep(time / step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (!shouldHalt && Application.STATUS == Status.ACTIVE) {
            if (bulletCount >= profile.getPattern().size()) {
                break;
            }
            if (profile.getFireMode() == FireMode.SEMI) {
                GlobalScreen.postNativeEvent(new NativeMouseEvent(NativeMouseEvent.NATIVE_MOUSE_PRESSED, 0, systemX, systemY, 0, NativeMouseEvent.BUTTON3));
                mouseMove(profile.getPattern().get(bulletCount));
                GlobalScreen.postNativeEvent(new NativeMouseEvent(NativeMouseEvent.NATIVE_MOUSE_RELEASED, 0, systemX, systemY, 0, NativeMouseEvent.BUTTON3));
                mouseMove(profile.getPattern().get(bulletCount));
            } else if (profile.getFireMode() == FireMode.AUTO) {
                mouseMove(profile.getPattern().get(bulletCount));
            }
            bulletCount++;
        }
    }
}
