package com.automationexercices;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {
    private static final String USER_DIR = PropertyReader.getProperty("user.dir") + File.separator;
    private FileUtils() {

    }
    //Renaming
    public static void renameFile(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("Target File Path: \"" + oldName + "\", file was renamed to \"" + newName + "\".");
            } else {
                LogsManager.info("Target File Path: \"" + oldName + "\", already has the desired name \"" + newName + "\".");
            }
        } catch (IOException e) {
            LogsManager.error(e.getMessage());
        }
    }
    //Creating Directory
    public static void createDirectory(String path)
    {
        try
        {
            File file = new File(USER_DIR + path);
            if (!file.exists())
            {
                file.mkdirs();
                LogsManager.info("Directory created: " + path);
            }
        }
        catch (Exception e)
        {
            LogsManager.error("Field to create directory: " + path , e.getMessage());
        }
    }

    // Force delete directory
    public static void forceDeleteDirectory(File file) {
        try
        {
            org.apache.commons.io.FileUtils.forceDelete(file);
        }
        catch (Exception e)
        {
            LogsManager.error("Field to force delete directory: " + file.getAbsolutePath() , e.getMessage());
        }
    }

    //Cleaning Directory
    public static void cleanDirectory(File file) {
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(file);
        }
        catch (Exception e)
        {
            LogsManager.error("Field to clean directory: " + file.getAbsolutePath() , e.getMessage());
        }
    }

}


