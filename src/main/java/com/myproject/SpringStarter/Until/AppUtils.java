package com.myproject.SpringStarter.Until;

import java.io.File;

public class AppUtils {
    public static String getUploadPath(String path) {
        return new File("src\\main\\resources\\static\\uploads").getAbsolutePath() + "\\" + path;
    }
}
