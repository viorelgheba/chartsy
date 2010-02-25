package org.chartsy.main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author viorel.gheba
 */
public final class FileUtils {

    protected FileUtils() {}

    public static String LocalFolder() {
        String result = System.getProperty("user.home") + File.separator + "Chartsy";
        createFolder(result);
        return result;
    }

    public static String LogFolder() {
        String result = LocalFolder() + File.separator + "log";
        createFolder(result);
        return result;
    }

    public static String LogFile() {
        String result = LogFolder() + File.separator + "log.txt";
        createFile(result);
        return result;
    }

    public static String ErrorFile() {
        String result = LogFolder() + File.separator + "err.txt";
        createFile(result);
        return result;
    }

    public static String SettingsFolder() {
        String result = LocalFolder() + File.separator + "settings";
        createFolder(result);
        return result;
    }

    public static String UserFile() {
        String result = SettingsFolder() + File.separator + "user.xml";
        return result;
    }

    public static String RegisterFile() {
        String result = SettingsFolder() + File.separator + "registred.xml";
        return result;
    }

    public static void removeFile(String path) {
        File file = new File(path);
        try {
            FileObject fo = FileUtil.toFileObject(file);
            fo.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createFile(String path) {
        File f = new File(path);
        try { FileObject file = FileUtil.createData(f); }
        catch (IOException ex) { ex.printStackTrace(); }
    }

    public static void createFolder(String path) {
        File dir = new File(path);
        try { FileObject folder = FileUtil.createFolder(dir); }
        catch (IOException ex) { ex.printStackTrace(); }
    }

    public static void copyFile(File source, File destination) throws IOException {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(destination).getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

}