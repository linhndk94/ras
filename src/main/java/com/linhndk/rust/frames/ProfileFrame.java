/*
 * Created by JFormDesigner on Fri Feb 08 06:25:54 ICT 2019
 */

package com.linhndk.rust.frames;

import com.linhndk.rust.Application;
import com.linhndk.rust.entity.Coordinate;
import com.linhndk.rust.entity.Profile;
import com.linhndk.rust.entity.UserSetting;
import com.linhndk.rust.exception.InputFormatException;
import com.linhndk.rust.types.FireMode;
import com.linhndk.rust.types.Status;
import com.linhndk.rust.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khanh Linh
 */
public class ProfileFrame extends JFrame {

    private boolean editingProfile;

    private List<Profile> profileList;
    private final String VERSION = "1.0.5-release";
    private UserSetting userSetting;
    private int box1Index;
    private int box2Index;
    private int box3Index;
    private int box4Index;
    private int box5Index;
    private int box6Index;

    public ProfileFrame(UserSetting userSetting) {
        box1Index = 0;
        box2Index = 0;
        box3Index = 0;
        box4Index = 0;
        box5Index = 0;
        box6Index = 0;
        this.userSetting = userSetting;
        editingProfile = false;
        profileList = new ArrayList<>();
        initData();
        initComponents();
        customInitComponent();
        updateProfileBox();
        updateHotkeyBoxes();
        updateComponents();
    }

    private void showHelpBox() {
        JOptionPane.showMessageDialog(this, "-Set profile of slot 1 - 6 based on your gun ingame below.\r\n" +
                "-Press (NUMPAD) + to activate and deactivate the tool\r\n" +
                "-Press (NUMPAD) - to shutdown the tool right away.");
    }

    private void initData() {
        if (userSetting.getProfileList() != null) {
            profileList = userSetting.getProfileList();
            if (userSetting.getActiveProfileName() != null) {
                userSetting.getProfileList().forEach(profile -> {
                    if (profile.getProfileName().equals(userSetting.getActiveProfileName())) {
                        Application.ActivatedProfile = profile;
                    }
                });
            }
            userSetting.getProfileList().forEach(profile -> {
                switch (profile.getProfileName()) {
                    case "AK-online":
                        Application.Numpad1Profile = profile;
                        break;
                    case "LR300":
                        Application.Numpad2Profile = profile;
                        break;
                    case "SAR":
                        Application.Numpad3Profile = profile;
                        break;
                    case "MP5":
                        Application.Numpad4Profile = profile;
                        break;
                    case "TOMMY":
                        Application.Numpad5Profile = profile;
                        break;
                    case "SMG":
                        Application.Numpad6Profile = profile;
                        break;
                    case "M92":
                        Application.Numpad7Profile = profile;
                        break;
                    case "P250":
                        Application.Numpad8Profile = profile;
                        break;
                    case "NAIL":
                        Application.Numpad9Profile = profile;
                        break;
                }
            });
        }
    }


    public void updateStatusLabel() {
        EventQueue.invokeLater(() -> {
            if (Application.STATUS == Status.NOT_ACTIVE) {
                statusLabel.setText("Not active");
                statusLabel.setForeground(Color.RED);
            } else {
                statusLabel.setText("Activated");
                statusLabel.setForeground(Color.GREEN);
            }
        });
    }

    private void updateProfileBox() {
        DefaultComboBoxModel<Profile> profileModel = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        profilesBox.setModel(profileModel);

        Profile profile = Application.ActivatedProfile;
        if (profile != null) {
            profilesBox.setSelectedItem(profile);
        }
    }

    private void updateHotkeyBoxes() {
        DefaultComboBoxModel<Profile> slot1Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot1Box.setModel(slot1Model);

        DefaultComboBoxModel<Profile> slot2Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot2Box.setModel(slot2Model);

        DefaultComboBoxModel<Profile> slot3Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot3Box.setModel(slot3Model);

        DefaultComboBoxModel<Profile> slot4Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot4Box.setModel(slot4Model);

        DefaultComboBoxModel<Profile> slot5Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot5Box.setModel(slot5Model);

        DefaultComboBoxModel<Profile> slot6Model = new DefaultComboBoxModel<>(profileList.toArray(new Profile[0]));
        slot6Box.setModel(slot6Model);

        if (profileList.size() > 0) {
            slot1Box.setSelectedIndex(box1Index);
            slot2Box.setSelectedIndex(box2Index);
            slot3Box.setSelectedIndex(box3Index);
            slot4Box.setSelectedIndex(box4Index);
            slot5Box.setSelectedIndex(box5Index);
            slot6Box.setSelectedIndex(box6Index);
        }
    }

    private void updateComponents() {
        if (editingProfile) {
            saveBtn.setVisible(true);
            if (profileList == null || profileList.size() == 0) {
                saveBtn.setEnabled(false);
            } else {
                saveBtn.setEnabled(true);
            }
            saveAsBtn.setVisible(true);
            deleteProfileBtn.setVisible(true);
            editCancelBtn.setText("Cancel");
//            profilesBox.setEnabled(true);
            profileNameField.setEnabled(true);
            fireModeBox.setEnabled(true);
            rpmField.setEnabled(true);
            minField.setEnabled(true);
            maxField.setEnabled(true);
            stepField.setEnabled(true);
            sensitivityMultiplierField.setEnabled(true);
            patternArea.setEnabled(true);
        } else {
            saveBtn.setVisible(false);
            saveAsBtn.setVisible(false);
            deleteProfileBtn.setVisible(false);
            editCancelBtn.setText("Edit");
//            profilesBox.setEnabled(false);
            profileNameField.setEnabled(false);
            fireModeBox.setEnabled(false);
            rpmField.setEnabled(false);
            minField.setEnabled(false);
            maxField.setEnabled(false);
            stepField.setEnabled(false);
            sensitivityMultiplierField.setEnabled(false);
            patternArea.setEnabled(false);
        }

        Profile profile = Application.ActivatedProfile;
        if (profile != null) {
            profileNameField.setText(profile.getProfileName());
            if (profile.getFireMode() != null) {
                fireModeBox.setSelectedItem(profile.getFireMode());
            }
            rpmField.setText(profile.getRpm() + "");
            minField.setText(profile.getRmin() + "");
            maxField.setText(profile.getRmax() + "");
            stepField.setText(profile.getSteps() + "");
            sensitivityMultiplierField.setText(profile.getSensitivityMultiplier() + "");
            if (profile.getPattern() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                profile.getPattern().forEach(integerIntegerPair -> stringBuilder.append(integerIntegerPair.getX()).append(",").append(integerIntegerPair.getY()).append("\r\n"));
                patternArea.setText(stringBuilder.toString());
            }
        } else {
            profileNameField.setText("");
            fireModeBox.setSelectedItem(FireMode.SEMI);
            rpmField.setText("");
            minField.setText("");
            maxField.setText("");
            sensitivityMultiplierField.setText("");
            patternArea.setText("");
        }
        pack();
    }

    private void customInitComponent() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(640, 360));
        versionLabel.setText(VERSION);
        DefaultComboBoxModel<FireMode> fireModeModel = new DefaultComboBoxModel<>(new FireMode[]{FireMode.SEMI, FireMode.AUTO, FireMode.EMPTY});
        fireModeBox.setModel(fireModeModel);
    }

    private void editCancelBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        editingProfile = !editingProfile;
        Application.editingProfile = editingProfile;
        updateComponents();
    }

    private void saveAsBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        try {
            Profile profile = validateInput();
            profileList.add(profile);
            Application.ActivatedProfile = profile;
        } catch (InputFormatException e1) {
            JOptionPane.showMessageDialog(this, "Exception: " + e1.getLocalizedMessage());
        }
        updateComponents();
        updateProfileBox();
        updateHotkeyBoxes();
    }

    private Profile validateInput() throws InputFormatException {
        String profileName = profileNameField.getText().trim();
        FireMode fireMode = (FireMode) fireModeBox.getSelectedItem();
        int rpm;
        try {
            rpm = Integer.parseInt(rpmField.getText().trim());
        } catch (NumberFormatException e) {
            throw new InputFormatException("RPM must be an integer");
        }

        int rmin;
        try {
            rmin = Integer.parseInt(minField.getText().trim());
        } catch (NumberFormatException e) {
            throw new InputFormatException("Rmin must be an integer");
        }

        int rmax;
        try {
            rmax = Integer.parseInt(maxField.getText().trim());
        } catch (NumberFormatException e) {
            throw new InputFormatException("Rmax must be an integer");
        }
        int steps;
        try {
            steps = Integer.parseInt(stepField.getText().trim());
        } catch (NumberFormatException e) {
            throw new InputFormatException("steps must be an integer");
        }
        double sensitivityMultiplier;
        try {
            sensitivityMultiplier = Double.parseDouble(sensitivityMultiplierField.getText().trim());
        } catch (NumberFormatException e) {
            throw new InputFormatException("Sensitivity multiplier must be a double");
        }
        List<Coordinate> pattern = new ArrayList<>();
        String temp = patternArea.getText().trim();
        String[] tempItems = temp.split("\n");
        for (String tempItem : tempItems) {
            if (!tempItem.trim().isEmpty()) {
                String[] tempSubItems = tempItem.trim().split(",");
                if (tempSubItems.length > 2) {
                    throw new InputFormatException("Pattern must be pairs");
                }
                try {
                    pattern.add(new Coordinate(Integer.parseInt(tempSubItems[0].trim()), Integer.parseInt(tempSubItems[1].trim())));
                } catch (NumberFormatException e) {
                    throw new InputFormatException("Pattern must be integers");
                }
            }
        }
        if (rmax <= rmin) {
            throw new InputFormatException("Rmax must be greater than Rmin");
        }
        if (rmin <= 0) {
            throw new InputFormatException("Rmin must be greater than 0");
        }
        if (sensitivityMultiplier <= 0) {
            throw new InputFormatException("Sensitivity Multiplier must be greater than 0");
        }
        if (rpm <= 0) {
            throw new InputFormatException("RPM must be greater than 0");
        }
        if (steps <= 0) {
            throw new InputFormatException("Steps must be greater than 0");
        }
        Profile profile = new Profile();
        profile.setProfileName(profileName);
        profile.setFireMode(fireMode);
        profile.setRpm(rpm);
        profile.setRmin(rmin);
        profile.setRmax(rmax);
        profile.setSteps(steps);
        profile.setSensitivityMultiplier(sensitivityMultiplier);
        profile.setPattern(pattern);
        return profile;
    }

    private void saveData() {
        userSetting.setProfileList(profileList);
        Profile profile = (Profile) profilesBox.getSelectedItem();
        if (profile != null) {
            userSetting.setActiveProfileName(profile.getProfileName());
        }
        EventQueue.invokeLater(() -> {
            try {
                Utils.saveUserSettingRaw(userSetting);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void profilesBoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        Application.ActivatedProfile = (Profile) profilesBox.getSelectedItem();
        updateComponents();
    }

    private void deleteProfileBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        profileList.remove(Application.ActivatedProfile);
        if (profileList.size() > 0) {
            Application.ActivatedProfile = profileList.get(0);
        } else {
            Application.ActivatedProfile = null;
        }
        updateProfileBox();
        updateComponents();
    }

    private void saveBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        try {
            Profile profile = validateInput();
            profileList.set(profileList.indexOf(Application.ActivatedProfile), profile);
            Application.ActivatedProfile = profile;
        } catch (InputFormatException e1) {
            JOptionPane.showMessageDialog(this, "Exception: " + e1.getLocalizedMessage());
        }
        updateProfileBox();
        updateComponents();
        updateHotkeyBoxes();
    }

    public void hotkeyActivated(int choice, Profile cachedProfile) {
        switch (choice) {
            case 1:
                if (cachedProfile != null) slot1Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot1Box.getSelectedItem();
                break;
            case 2:
                if (cachedProfile != null) slot2Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot2Box.getSelectedItem();
                break;
            case 3:
                if (cachedProfile != null) slot3Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot3Box.getSelectedItem();
                break;
            case 4:
                if (cachedProfile != null) slot4Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot4Box.getSelectedItem();
                break;
            case 5:
                if (cachedProfile != null) slot5Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot5Box.getSelectedItem();
                break;
            case 6:
                if (cachedProfile != null) slot6Box.setSelectedItem(cachedProfile);
                Application.ActivatedProfile = (Profile) slot6Box.getSelectedItem();
                break;
            default:
                break;
        }
        Profile profile = Application.ActivatedProfile;
        SwingUtilities.invokeLater(() -> {
            if (profile != null) {
                profilesBox.setSelectedItem(profile);
            }
            updateComponents();
        });
    }

    private void saveSettingButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        saveData();
        JOptionPane.showMessageDialog(this, "Data saved");
    }

    private void slot1BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box1Index = slot1Box.getSelectedIndex();
    }

    private void slot2BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box2Index = slot2Box.getSelectedIndex();
    }

    private void slot3BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box3Index = slot3Box.getSelectedIndex();
    }

    private void slot4BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box4Index = slot4Box.getSelectedIndex();
    }

    private void slot5BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box5Index = slot5Box.getSelectedIndex();
    }

    private void slot6BoxActionPerformed(ActionEvent e) {
        // TODO add your code here
        box6Index = slot6Box.getSelectedIndex();
    }

    private void helpButtonActionPerformed(ActionEvent e) {
        showHelpBox();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Khanh Linh
        label1 = new JLabel();
        versionLabel = new JLabel();
        statusLabel = new JLabel();
        helpButton = new JButton();
        label4 = new JLabel();
        profilesBox = new JComboBox<>();
        editCancelBtn = new JButton();
        saveAsBtn = new JButton();
        label2 = new JLabel();
        profileNameField = new JTextField();
        saveBtn = new JButton();
        deleteProfileBtn = new JButton();
        label13 = new JLabel();
        fireModeBox = new JComboBox<>();
        label3 = new JLabel();
        rpmField = new JTextField();
        label5 = new JLabel();
        minField = new JTextField();
        label8 = new JLabel();
        maxField = new JTextField();
        label9 = new JLabel();
        stepField = new JTextField();
        label6 = new JLabel();
        sensitivityMultiplierField = new JTextField();
        label7 = new JLabel();
        scrollPane1 = new JScrollPane();
        patternArea = new JTextArea();
        saveSettingButton = new JButton();
        label10 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        slot1Box = new JComboBox<>();
        slot2Box = new JComboBox<>();
        slot3Box = new JComboBox<>();
        slot4Box = new JComboBox<>();
        slot5Box = new JComboBox<>();
        slot6Box = new JComboBox<>();

        //======== this ========
        setVisible(true);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout) contentPane.getLayout()).columnWidths = new int[]{105, 105, 105, 105, 105, 105, 105, 100};
        ((GridBagLayout) contentPane.getLayout()).rowHeights = new int[]{35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 30};

        //---- label1 ----
        label1.setText("RUST AIM SUPPORTER");
        label1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPane.add(label1, new GridBagConstraints(1, 1, 6, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- versionLabel ----
        versionLabel.setText("version");
        contentPane.add(versionLabel, new GridBagConstraints(2, 2, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- statusLabel ----
        statusLabel.setText("Not active");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.red);
        contentPane.add(statusLabel, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- helpButton ----
        helpButton.setText("?");
        helpButton.addActionListener(e -> helpButtonActionPerformed(e));
        contentPane.add(helpButton, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Profiles: ");
        contentPane.add(label4, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- profilesBox ----
        profilesBox.addActionListener(e -> profilesBoxActionPerformed(e));
        contentPane.add(profilesBox, new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- editCancelBtn ----
        editCancelBtn.setText("Edit");
        editCancelBtn.addActionListener(e -> editCancelBtnActionPerformed(e));
        contentPane.add(editCancelBtn, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- saveAsBtn ----
        saveAsBtn.setText("Save as new");
        saveAsBtn.addActionListener(e -> saveAsBtnActionPerformed(e));
        contentPane.add(saveAsBtn, new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Profile name: ");
        contentPane.add(label2, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(profileNameField, new GridBagConstraints(3, 5, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- saveBtn ----
        saveBtn.setText("Save");
        saveBtn.addActionListener(e -> saveBtnActionPerformed(e));
        contentPane.add(saveBtn, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- deleteProfileBtn ----
        deleteProfileBtn.setText("Delete");
        deleteProfileBtn.addActionListener(e -> deleteProfileBtnActionPerformed(e));
        contentPane.add(deleteProfileBtn, new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label13 ----
        label13.setText("Fire mode:");
        contentPane.add(label13, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(fireModeBox, new GridBagConstraints(3, 6, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("RPM: ");
        contentPane.add(label3, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(rpmField, new GridBagConstraints(3, 7, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Rmin:");
        contentPane.add(label5, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(minField, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label8 ----
        label8.setText("Rmax:");
        contentPane.add(label8, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(maxField, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label9 ----
        label9.setText("Steps:");
        contentPane.add(label9, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(stepField, new GridBagConstraints(3, 9, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label6 ----
        label6.setText("Sensitivity multiplier:");
        contentPane.add(label6, new GridBagConstraints(1, 10, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPane.add(sensitivityMultiplierField, new GridBagConstraints(3, 10, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label7 ----
        label7.setText("Pattern:");
        contentPane.add(label7, new GridBagConstraints(1, 11, 2, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- patternArea ----
            patternArea.setRows(3);
            scrollPane1.setViewportView(patternArea);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(3, 11, 2, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- saveSettingButton ----
        saveSettingButton.setText("Save setting");
        saveSettingButton.addActionListener(e -> saveSettingButtonActionPerformed(e));
        contentPane.add(saveSettingButton, new GridBagConstraints(5, 12, 2, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label10 ----
        label10.setText("1");
        contentPane.add(label10, new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label11 ----
        label11.setText("2");
        contentPane.add(label11, new GridBagConstraints(2, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label12 ----
        label12.setText("3");
        contentPane.add(label12, new GridBagConstraints(3, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label14 ----
        label14.setText("4");
        contentPane.add(label14, new GridBagConstraints(4, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label15 ----
        label15.setText("5");
        contentPane.add(label15, new GridBagConstraints(5, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label16 ----
        label16.setText("6");
        contentPane.add(label16, new GridBagConstraints(6, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot1Box ----
        slot1Box.addActionListener(e -> slot1BoxActionPerformed(e));
        contentPane.add(slot1Box, new GridBagConstraints(1, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot2Box ----
        slot2Box.addActionListener(e -> slot2BoxActionPerformed(e));
        contentPane.add(slot2Box, new GridBagConstraints(2, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot3Box ----
        slot3Box.addActionListener(e -> slot3BoxActionPerformed(e));
        contentPane.add(slot3Box, new GridBagConstraints(3, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot4Box ----
        slot4Box.addActionListener(e -> slot4BoxActionPerformed(e));
        contentPane.add(slot4Box, new GridBagConstraints(4, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot5Box ----
        slot5Box.addActionListener(e -> slot5BoxActionPerformed(e));
        contentPane.add(slot5Box, new GridBagConstraints(5, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- slot6Box ----
        slot6Box.addActionListener(e -> slot6BoxActionPerformed(e));
        contentPane.add(slot6Box, new GridBagConstraints(6, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Khanh Linh
    private JLabel label1;
    private JLabel versionLabel;
    private JLabel statusLabel;
    private JButton helpButton;
    private JLabel label4;
    private JComboBox<Profile> profilesBox;
    private JButton editCancelBtn;
    private JButton saveAsBtn;
    private JLabel label2;
    private JTextField profileNameField;
    private JButton saveBtn;
    private JButton deleteProfileBtn;
    private JLabel label13;
    private JComboBox<FireMode> fireModeBox;
    private JLabel label3;
    private JTextField rpmField;
    private JLabel label5;
    private JTextField minField;
    private JLabel label8;
    private JTextField maxField;
    private JLabel label9;
    private JTextField stepField;
    private JLabel label6;
    private JTextField sensitivityMultiplierField;
    private JLabel label7;
    private JScrollPane scrollPane1;
    private JTextArea patternArea;
    private JButton saveSettingButton;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel label14;
    private JLabel label15;
    private JLabel label16;
    private JComboBox<Profile> slot1Box;
    private JComboBox<Profile> slot2Box;
    private JComboBox<Profile> slot3Box;
    private JComboBox<Profile> slot4Box;
    private JComboBox<Profile> slot5Box;
    private JComboBox<Profile> slot6Box;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
