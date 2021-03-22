package org.solar.editor.plugin.util;


import javafx.application.Application;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.solar.cache.CacheImpl;
import org.solar.coder.AESCoder;
import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.bean.SolarPlugin;
import org.solar.editor.gui.EmptyApp;
import org.solar.editor.core.bean.SEP;
import org.solar.io.FileUtil;
import org.solar.jfx.util.DialogUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.lang.ScriptUtil;
import org.solar.lang.SolarMap;
import org.solar.system.SystemUtil;

import javax.script.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SolarWEPluginUtil {
    public static final GroovyScriptEngineImpl groovyScriptEngine = (GroovyScriptEngineImpl) ScriptUtil.getEngineByName("groovy");
    public static final String base_import =
            "import org.solar.editor.core.Config;\n" +
                    "import org.solar.editor.core.bean.ObjMap;\n" +
                    "import org.solar.editor.core.bean.GameScript;\n" +
                    "import org.solar.editor.plugin.SEPUtil;\n" +
                    "import org.solar.editor.SE;\n" +
                    "import org.solar.editor.core.util.common.ObjMapUtil;\n" +
                    "import org.solar.editor.core.util.common.ObjMapFilterUtil;\n" +
                    "import javafx.scene.control.Label\n" +
                    "import org.solar.jfx.util.DialogUtil\n" +
                    "import org.solar.lang.SolarMap\n" +
                    "import org.solar.lang.SolarList\n" +
                    "import java.util.ArrayList\n" +
                    "import java.io.File\n" +
                    "import org.solar.log.Log\n" +
                    "import org.solar.editor.core.util.SolarLog\n" +
                    "import org.solar.bean.SolarEnum\n" +
                    "import org.solar.bean.SolarColor\n" +
                    "\n" +
                    "\n" +
                    "\n";


    public static void run(String pluginGroovyFilePath) {
        run(new File(pluginGroovyFilePath));
    }

    public static CacheImpl groovyScriptEngineCache = new CacheImpl();


    public static void load_plugins(String dirPath) {
        List<File> files = FileUtil.getAllFiles(dirPath, new ArrayList<File>(), true, "groovy");
        if (files != null) {
            for (File file : files) {
                String scriptCode = FileUtil.getStringFromFile(file);
                getGroovyScriptEngineByScriptCode(scriptCode);
            }
        }
    }

    public static final AESCoder aesCoder = new AESCoder(SystemUtil.getHardwareUid() + ".groovy");

    public static CompiledScript getGroovyScriptEngineByScriptCode(final String scriptCode) {
        return groovyScriptEngineCache.get(scriptCode, new Callable() {
            @Override
            public CompiledScript call() throws Exception {
                String newScriptCode = scriptCode;
                if (newScriptCode.contains("XpE08aGQpyTKvhh66ilgNMKMIDAQAB")) {
                    if (newScriptCode.startsWith("//")) {
                        newScriptCode = newScriptCode.substring(2);
                    }
                    newScriptCode = aesCoder.AESDecode(newScriptCode.replace("XpE08aGQpyTKvhh66ilgNMKMIDAQAB", ""));
                }
                if (newScriptCode.contains(removeCode)) {
                    newScriptCode = newScriptCode.replace(removeCode, "");
                }
                CompiledScript compiledScript = groovyScriptEngine.compile(base_import + scriptCode);
                return compiledScript;
            }
        });
    }


    public static void run(SolarPlugin solarPlugin) {
        run(solarPlugin.getFileDirPath(), solarPlugin.getName(), solarPlugin.getMainScriptCode());
    }

    public static void run(File pluginGroovyFile) {
        String fileDir = FileUtil.formatPath(pluginGroovyFile.getParent() + "/");
        String pluginName = FileUtil.removeNameExtension(pluginGroovyFile.getName());
        String scriptCode = FileUtil.getStringFromFile(pluginGroovyFile);
        run(fileDir, pluginName, scriptCode);

    }

    public static void run(String fileDir, String pluginName, String scriptCode) {
        //j m

        //
        CompiledScript compiledScript = getGroovyScriptEngineByScriptCode(scriptCode);
        Bindings bindings = compiledScript.getEngine().createBindings();

        SolarMap solarMap = new SolarMap();
        bindings.put("vars", solarMap);
        bindings.put("params", solarMap);
        SEP sep = new SEP();
        sep.setName(pluginName);
        sep.setDir(fileDir);
        bindings.put("sep", sep);
        //eval
        try {
            ScriptContext ctxt = compiledScript.getEngine().getContext();
            SimpleScriptContext tempctxt = new SimpleScriptContext();
            tempctxt.setReader(ctxt.getReader());
            tempctxt.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            tempctxt.setBindings(ctxt.getBindings(ScriptContext.GLOBAL_SCOPE),
                    ScriptContext.GLOBAL_SCOPE);
            compiledScript.eval(tempctxt);
        } catch (Exception e) {
            ThreadUtil.execute(() -> {
                ThreadUtil.sleep(100);
                FxUtil.runLater(() -> {
                    DialogUtil.tip("出错了!\r\n" + e.getMessage());
                });
            });
            throw new RuntimeException(e);
        }

    }


    static String removeCode = "SolarWEPluginUtil.test_run(this); return;";

    public static void test_run(Object obj) {
        File pluginGroovyFile = new File(obj.getClass().getSimpleName() + ".groovy");
        test_run(pluginGroovyFile);
    }

    public static void test_run(File pluginGroovyFile) {
        Runnable c = () -> {
            ThreadUtil.refreshStartTime();
            String fileName = FileUtil.removeNameExtension(pluginGroovyFile.getName());
            String scriptCode = FileUtil.getStringFromFile(pluginGroovyFile);
            if (scriptCode == null) {
                System.out.println("scriptCode == null in test_run" + pluginGroovyFile.getAbsolutePath());
                return;
            }
            if (scriptCode.contains(removeCode)) {
                scriptCode = scriptCode.replace(removeCode, "");
            } else {
                System.out.println("scriptCode dont contains removeCode!");
                return;
            }
            CompiledScript compiledScript = getGroovyScriptEngineByScriptCode(scriptCode);
            Bindings bindings = compiledScript.getEngine().createBindings();
            SolarMap solarMap = new SolarMap();
            bindings.put("vars", solarMap);
            bindings.put("params", solarMap);
            SEP sep = new SEP();
            sep.setName(fileName);
            sep.setDir(new File("").getAbsolutePath() + "/");
            bindings.put("sep", sep);
            try {
                compiledScript.eval(bindings);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            /**
             * addons
             */
            ThreadUtil.printUseTimeInfo("run(File pluginGroovyFile)");
        };
        EmptyApp.runnableList.add(c);
        Application.launch(EmptyApp.class);
    }


}
