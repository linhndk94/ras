package com.linhndk.rust.listeners.mouse;

import com.linhndk.rust.Application;
import com.linhndk.rust.thread.RustRecoilSupportThread;
import com.linhndk.rust.types.FireMode;
import com.linhndk.rust.types.Status;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class RustMouseListener implements NativeMouseInputListener {

    /*
    left 1
    right 2
    mid 3
    top 5
    bottom 4
     */

    private RustRecoilSupportThread rustRecoilSupportThread;

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if (Application.STATUS != Status.NOT_ACTIVE) {
            if (nativeMouseEvent.getButton() == 1) {
                if (Application.STATUS == Status.ACTIVE && Application.ActivatedProfile != null && Application.ActivatedProfile.getFireMode() != FireMode.EMPTY) {
                    rustRecoilSupportThread = new RustRecoilSupportThread(Application.ActivatedProfile, nativeMouseEvent.getX(), nativeMouseEvent.getY());
                    if (Application.ActivatedProfile.getFireMode() == FireMode.AUTO) {
                        GlobalScreen.postNativeEvent(new NativeMouseEvent(NativeMouseEvent.NATIVE_MOUSE_PRESSED, 0, nativeMouseEvent.getX(), nativeMouseEvent.getY(), 0, NativeMouseEvent.BUTTON3));
                    }
                    rustRecoilSupportThread.start();
                }
            } else if (nativeMouseEvent.getButton() == 2) {
                Application.STATUS = Status.ACTIVE;
            }
        }
    }


    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        if (Application.STATUS != Status.NOT_ACTIVE) {
            if (nativeMouseEvent.getButton() == 1) {
                if (Application.STATUS == Status.ACTIVE && Application.ActivatedProfile != null && Application.ActivatedProfile.getFireMode() != FireMode.EMPTY) {
                    rustRecoilSupportThread.halt();
                    GlobalScreen.postNativeEvent(new NativeMouseEvent(NativeMouseEvent.NATIVE_MOUSE_RELEASED, 0, nativeMouseEvent.getX(), nativeMouseEvent.getY(), 0, NativeMouseEvent.BUTTON3));
                }
            } else if (nativeMouseEvent.getButton() == 2) {
                Application.STATUS = Status.STAND_BY;
            }
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }
}
