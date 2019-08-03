package com.linhndk.rust.listeners.key;

import com.linhndk.rust.Application;
import com.linhndk.rust.types.Status;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;

public class RustKeyListener implements NativeKeyListener {
    /*
    /: 53
    *: 3639
    -: 3658
    +: 3662

    hotbar1 49
    hotbar2 50
    hotbar3 51
    hotbar4 52
    hotbar5 53
    hotbar6 54

    numpad1 97
    numpad2 98
    numpad3 99
    numpad4 100
    numpad5 101
    numpad6 102
    numpad7 103
    numpad8 104
    numpad9 105
     */


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (Application.STATUS != Status.NOT_ACTIVE && nativeKeyEvent.getRawCode() >= 49 && nativeKeyEvent.getRawCode() <= 54 && !Application.editingProfile) {
            Application.Frame.hotkeyActivated(nativeKeyEvent.getRawCode() - 48, Application.CachedProfile);
            Application.CachedProfile = null;
        } else if (nativeKeyEvent.getKeyCode() == 3658) {
            System.exit(0);
        } else if (nativeKeyEvent.getKeyCode() == 3662) {
            Application.STATUS = Application.STATUS == Status.NOT_ACTIVE ? Status.STAND_BY : Status.NOT_ACTIVE;
            if (Application.STATUS == Status.STAND_BY) {
                EventQueue.invokeLater((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default"));
            } else {
                EventQueue.invokeLater((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand"));
            }
            Application.Frame.updateStatusLabel();
        } else if (Application.STATUS != Status.NOT_ACTIVE && nativeKeyEvent.getRawCode() >= 97 && nativeKeyEvent.getRawCode() <= 105 && !Application.editingProfile) {
            switch (nativeKeyEvent.getRawCode()) {
                case 97:
                    Application.CachedProfile = Application.Numpad1Profile;
                    break;
                case 98:
                    Application.CachedProfile = Application.Numpad2Profile;
                    break;
                case 99:
                    Application.CachedProfile = Application.Numpad3Profile;
                    break;
                case 100:
                    Application.CachedProfile = Application.Numpad4Profile;
                    break;
                case 101:
                    Application.CachedProfile = Application.Numpad5Profile;
                    break;
                case 102:
                    Application.CachedProfile = Application.Numpad6Profile;
                    break;
                case 103:
                    Application.CachedProfile = Application.Numpad7Profile;
                    break;
                case 104:
                    Application.CachedProfile = Application.Numpad8Profile;
                    break;
                case 105:
                    Application.CachedProfile = Application.Numpad9Profile;
                    break;

            }
        } else if (Application.STATUS != Status.NOT_ACTIVE && nativeKeyEvent.getRawCode() == 162 && !Application.editingProfile) {
            Application.isCrouching = true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (Application.STATUS != Status.NOT_ACTIVE && nativeKeyEvent.getRawCode() == 162 && !Application.editingProfile) {
            Application.isCrouching = false;
        }
    }
}
