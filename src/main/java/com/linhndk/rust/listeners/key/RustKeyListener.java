package com.linhndk.rust.listeners.key;

import com.linhndk.rust.Application;
import com.linhndk.rust.types.Status;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class RustKeyListener implements NativeKeyListener {
    /*
    /: 53
    *: 3639
    -: 3658
    +: 3662
     */

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (Application.STATUS != Status.NOT_ACTIVE && nativeKeyEvent.getKeyCode() >= 2 && nativeKeyEvent.getKeyCode() <= 7 && !Application.editingProfile) {
            Application.Frame.hotkeyActivated(nativeKeyEvent.getKeyCode() - 1);
        } else if (nativeKeyEvent.getKeyCode() == 3658) {
            System.exit(0);
        } else if (nativeKeyEvent.getKeyCode() == 3662) {
            Application.STATUS = Application.STATUS == Status.NOT_ACTIVE ? Status.STAND_BY : Status.NOT_ACTIVE;
            Application.Frame.updateStatusLabel();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
