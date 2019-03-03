package com.linhndk.rust.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.linhndk.rust.entity.UserSetting;
import com.linhndk.rust.exception.ApplicationDataException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Utils {

    private static final File file = new File("app.dat");

    public static UserSetting loadUserSettingRaw() throws ApplicationDataException {
        if (!file.exists()) {
            throw new ApplicationDataException("app.data does not exist");
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(new String(FileUtils.readFileToByteArray(file)), UserSetting.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApplicationDataException("app.dat is corrupted");
            }
        }
    }

    public static void saveUserSettingRaw(UserSetting userSetting) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        FileUtils.writeByteArrayToFile(file, objectMapper.writeValueAsString(userSetting).getBytes());
    }

    public static String getMacAddress() throws UnknownHostException, SocketException {
        byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }
}
