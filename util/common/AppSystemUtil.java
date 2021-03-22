package org.solar.editor.core.util.common;

import org.solar.concurrent.ThreadUtil;
import org.solar.system.SystemUtil;
import org.solar.editor.core.Config;

import java.io.InputStream;

public class AppSystemUtil {


    public static void closeWorldeditydwe() {
        Process process = SystemUtil.execCommand(Config.filePath_app_data + "exe/nircmd.exe", "elevate", "taskkill", "/f", "/im", "worldeditydwe.exe");
        try {
//            ThreadUtil.refreshStartTime();
            process.waitFor();
//            ThreadUtil.printUseTimeInfo("关闭WE");
            //至少等待300毫秒才能关闭we进程完毕
            ThreadUtil.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public static Process execCommand(String... cmdstr) {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(cmdstr);
            InputStream in = process.getInputStream();
            byte[] buff = new byte[120];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                System.out.print(new String(buff, 0, len) + "\r\n");
            }
            //getErrorStream
            in = process.getErrorStream();
            while ((len = in.read(buff)) != -1) {
                System.out.print(new String(buff, 0, len) + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return process;
    }


}
