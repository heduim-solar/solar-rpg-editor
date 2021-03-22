package org.solar.editor.core.util.common;

import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.Config;
import org.solar.editor.core.util.SolarLog;
import org.solar.lang.ArrayUtil;
import org.solar.lang.ListUtil;
import org.solar.system.SystemUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExeCmdUtil {
    private static final Cache cache = new CacheImpl();

    public static void vscode(String... filePaths) {
        String filePath_exe_vscode_code_cmd = cache.get("filePath_exe_vscode_code_cmd", () -> {
            File file = new File(Config.filePath_exe_vscode);
            return file.getParent() + "/bin/code.cmd";
        });
        List cmdList = new ArrayList();
        cmdList.add("\"" + filePath_exe_vscode_code_cmd + "\"");
        cmdList.add("-r");
        cmdList.add("--reuse-window");
        for (int i = 0; i < filePaths.length; i++) {
            cmdList.add(filePaths[i]);
        }
        SystemUtil.execCommand(ListUtil.toStringArray(cmdList));
    }

    public static void WebStorm(String... filePaths) {
        List cmdList = new ArrayList();
        cmdList.add("\"" + Config.filePath_exe_web_storm + "\"");
        for (int i = 0; i < filePaths.length; i++) {
            cmdList.add(filePaths[i]);
        }
        ThreadUtil.execute(() -> {
            SystemUtil.execCommand(ListUtil.toStringArray(cmdList));
        });
    }

    public static void wps(String... filePaths) {
        List cmdList = new ArrayList();
        cmdList.add(Config.filePath_exe_wps);
        for (int i = filePaths.length - 1; i >= 0; i--) {
            cmdList.add(filePaths[i]);
        }
        SystemUtil.execCommand(ListUtil.toStringArray(cmdList));
    }


    public static void nircmd_elevate(String... cmds) {
        String[] newCmds = ArrayUtil.concat(new String[]{
                Config.filePath_exe_nircmd, "elevate"
        }, cmds);
        SystemUtil.execCommand(newCmds);
    }

    public static void node(String... cmds) {
        String[] newCmds = ArrayUtil.concat(new String[]{
                Config.filePath_exe_node_root + "node"
        }, cmds);
        SystemUtil.execCommand(newCmds);
    }

    public static void npx(String... cmds) {
        String[] newCmds = ArrayUtil.concat(new String[]{
                Config.filePath_exe_node_root + "npx.cmd"
        }, cmds);
        SystemUtil.execCommand(new File(Config.filePath_exe_node_root), newCmds);
    }

    public static void tstl(String... cmds) {
        String tstlPath = Config.filePath_exe_node_root + "node_modules\\typescript-to-lua\\dist\\tstl.js";
        String[] newCmds = ArrayUtil.concat(new String[]{
                Config.filePath_exe_node_root + "npx.cmd", tstlPath
                , "--allowJs", "true"
                , "--outDir", new File(cmds[0]).getParent()
//                ,"--noImplicitSelf true",
        }, cmds);
        System.out.println(String.join(" ", newCmds).replace("/", "\\"));
        SystemUtil.execCommand(new File(Config.filePath_exe_node_root), newCmds);
    }

    public static void tstlByTsConfig(String workDir) {
        String tstlPath = Config.filePath_exe_node_root + "node_modules\\typescript-to-lua\\dist\\tstl.js";
        SystemUtil.execCommand(new File(workDir), Config.filePath_exe_node_root + "npx.cmd", tstlPath);
        SolarLog.systemSay("typescript-to-lua 编译任务执行完毕！");
    }


    public static void war3_model_editor(String mdxpath) {
        ThreadUtil.execute(() -> {
            String filePath_wme = Config.filePath_app_data + "exe/war3_model_editor/";
            SystemUtil.execCommand(new File(mdxpath).getParentFile(), filePath_wme + "war3_model_editor.exe", mdxpath);
        });
    }


}
