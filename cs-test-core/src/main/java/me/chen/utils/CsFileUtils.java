package me.chen.utils;

import java.io.File;
import java.net.URL;

/**
 * @Author: ftdcs
 * @Date: 2019/05/29 0029 20:31
 * @Version 1.0
 */
public class CsFileUtils {


    public static File getResource(String path){
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        File file = org.apache.commons.io.FileUtils.toFile(resource);
        return file;
    }
}
