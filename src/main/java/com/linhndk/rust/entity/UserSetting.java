package com.linhndk.rust.entity;

import java.util.List;

public class UserSetting {
    private String activeProfileName;
    private List<Profile> profileList;
    private boolean isEverLasting;
    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public boolean isEverLasting() {
        return isEverLasting;
    }

    public void setEverLasting(boolean everLasting) {
        isEverLasting = everLasting;
    }

    public String getActiveProfileName() {
        return activeProfileName;
    }

    public void setActiveProfileName(String activeProfileName) {
        this.activeProfileName = activeProfileName;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}
