package ua.kiev.allexb.carrental.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author allexb
 * @version 1.0 09.09.2016
 */
public class RootPathUtil {

    public static final String CLASS_PATH = "/WEB-INF/classes/";

    public static String getRootApplicationPath() {
        String path =  RootPathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.out.println(path);

        String fullPath = "";
        try {
            fullPath = URLDecoder.decode(path, "UTF-8");
            String[] pathArr = fullPath.split(CLASS_PATH);
            fullPath = pathArr[0];
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return new File(fullPath).getPath();
    }

    public static String getRootApplicationPath(String fileName) {
        return getRootApplicationPath() + fileName;
    }

    public static String getRootApplicationClassPath() {
        return getRootApplicationPath() + CLASS_PATH;
    }

    public static String getRootApplicationClassPath(String fileName) {
        return getRootApplicationClassPath() + fileName;
    }
}
