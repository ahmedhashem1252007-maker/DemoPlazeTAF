package com.automationexercices.utils;

import com.automationexercices.utils.dataReader.PropertyReader;

public class OSUtils {
    public enum OS { WINDOWS, LINUX, MAC, OTHER}

    public static OS getCurrentOS()
    {
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) return OS.WINDOWS;
        if (osName.contains("nix") || osName.contains("nux")) return OS.LINUX;
        if (osName.contains("mac")) return OS.MAC;
        return OS.OTHER;
    }
}
