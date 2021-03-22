package org.solar.editor.core;


import org.solar.editor.core.bean.BuildConfig;
import org.solar.system.RegistryUtil;

public class Config extends BaseConfig {

    public static final String filePath_game_project_root = getAppHomePath() + "/game_root/";
    public static final String filePath_cache = getAppHomePath() + "/cache/";
    public static final String filePath_war3_mpq_files = filePath_cache + "wbdfiles/";


    public static String filePath_workspace = getAppHomePath() + "workspace/";
    public static String filePath_exe_nircmd = filePath_app_data + "exe/nircmd.exe";
    public static String filePath_exe_node_root = filePath_app_data + "exe/nodejs/";
    public static String filePath_web_root = filePath_app_data + "web/";
    public static String filePath_exe_vscode = filePath_windows_Desktop + "Code.exe";
    public static String filePath_exe_web_storm = filePath_windows_Desktop + "WebStorm.exe";
    public static String filePath_exe_wps = filePath_windows_Desktop + "ksolaunch.exe";
//    public static String testp = "testp";
    /**
     * build config
     */
    public static final BuildConfig buildConfig = new BuildConfig();


    static {
        RegistryUtil.nircmdExePath = Config.filePath_exe_nircmd;
    }


}
