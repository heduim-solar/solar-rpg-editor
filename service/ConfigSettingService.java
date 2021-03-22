package org.solar.editor.core.service;

import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.history.History;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.PreferencesFxModel;
import com.dlsc.preferencesfx.model.Setting;
import com.dlsc.preferencesfx.util.SearchHandler;
import com.dlsc.preferencesfx.util.StorageHandler;
import com.dlsc.preferencesfx.util.StorageHandlerImpl;
import com.dlsc.preferencesfx.view.PreferencesFxDialog;
import javafx.beans.property.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import org.solar.editor.core.BaseConfig;
import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;
import org.solar.util.BeanUtil;
import org.solar.editor.core.Config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigSettingService {
    //    public static final Class configClazz = Config.class;
    public static final StorageHandler storageHandler = new StorageHandlerImpl(Config.class);

    public static Setting buildSetting(Class configClazz, String description, String fieldName) {
        try {
            Field field = configClazz.getField(fieldName);
            return buildSetting(description, field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Setting buildSetting(String description, Field field) {
        try {
            Property objectProperty = null;
            Setting setting = null;
            field.setAccessible(true);
            Class fieldType = field.getType();
            if (fieldType == String.class) {
                String fieldName = field.getName().toLowerCase();
                if (fieldName.startsWith("filepath")) {
                    // FileChooser / DirectoryChooser
                    boolean isDirectory = true;
                    if (fieldName.contains("exe") || fieldName.contains(".")) {
                        isDirectory = false;
                    }
                    String fp = (String) field.get(null);
                    if (StringUtil.isEmpty(fp)) {
                        fp = Config.filePath_windows_Desktop;
                    }
                    objectProperty = new SimpleObjectProperty<>(new File(fp));
                    setting = Setting.of(description, (SimpleObjectProperty) objectProperty, "浏览", null, isDirectory);     // FileChooser
                } else {
                    objectProperty = new SimpleStringProperty((String) field.get(null));
                    setting = Setting.of(description, (SimpleStringProperty) objectProperty);
                }
            } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                objectProperty = new SimpleBooleanProperty((boolean) field.get(null));
                setting = Setting.of(description, (SimpleBooleanProperty) objectProperty);
            } else if (fieldType.isEnum()) {

//                Method method = fieldType.getMethod("valueOf", String.class);
//                Object obj = method.invoke(null, val);
//                field.set(null, obj);
            } else if (fieldType == int.class || fieldType == Integer.class) {
                objectProperty = new SimpleIntegerProperty((int) field.get(null));
                setting = Setting.of(description, (SimpleIntegerProperty) objectProperty);
            } else if (fieldType == double.class || fieldType == Double.class) {
                objectProperty = new SimpleDoubleProperty((int) field.get(null));
                setting = Setting.of(description, (SimpleDoubleProperty) objectProperty);
            }
            setting.valueProperty().addListener((a, o, n) -> {
                try {
                    if (n instanceof File && fieldType == String.class) {
                        String filePath = ((File) n).getAbsolutePath();
                        filePath = FileUtil.formatPath(filePath);
                        if (((File) n).isDirectory() && !filePath.endsWith("/")) {
                            filePath = filePath + "/";
                        }
                        field.set(null, filePath);
                    } else {
                        field.set(null, n);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            setting.loadSettingValue(storageHandler);

            return setting;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Category baseCategory() {
        Category category = Category.of("基础设置",
                buildSetting(BaseConfig.class, "编辑器数据根目录", "filePath_app_data"),
                buildSetting(BaseConfig.class, "插件根目录", "filePath_plugin")
        );
        return category;
    }

    public static Category compatibleCategory() {
        Category category = Category.of("魔兽设置",
                buildSetting(BaseConfig.class, "魔兽根目录", "filePath_warcraft_directory_root"),
                buildSetting(BaseConfig.class, "YDWE根目录", "filePath_world_editor_root")
        );
        category.expand();

        return category;
    }

    public static Category exeCategory() {
        Category category = Category.of("辅助程序",
                buildSetting(Config.class, "VsCode", "filePath_exe_vscode"),
                buildSetting(Config.class, "Wps", "filePath_exe_wps"),
                buildSetting(Config.class, "WebStorm", "filePath_exe_web_storm"),
                Setting.of(new Label("请选择对应的exe文件(或选择桌面上的应用快捷方式)"))
        );
        category.expand();
//        category.subCategories(
//                Category.of("程序关联",
//
//                )
//        );
        return category;
    }


    public static List<Category> getCategorys() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(baseCategory());
        categoryList.add(compatibleCategory());
        categoryList.add(exeCategory());
        return categoryList;
    }


    static Category[] categorys = getCategorys().toArray(new Category[0]);

    public static void init() {
        //one case
        Config.getAppHomePath();
        PreferencesFxModel preferencesFxModel = new PreferencesFxModel(
                storageHandler, new SearchHandler(), new History(), categorys
        );
        preferencesFxModel.loadSettingValues();
        Config.formatPath();
    }

    private static PreferencesFx preferencesFx = null;

    public static void showSettingDialog() {
        if (preferencesFx == null) {
            preferencesFx = PreferencesFx.of(Config.class, categorys);
            preferencesFx.dialogTitle("设置");
            preferencesFx.instantPersistent(true);
            PreferencesFxDialog preferencesFxDialog = BeanUtil.getFieldValue(preferencesFx, "preferencesFxDialog");
            Dialog dialog = BeanUtil.getFieldValue(preferencesFxDialog, "dialog");
            preferencesFxDialog.getStylesheets().add("css/solar_javafx.css");
            preferencesFxDialog.getStylesheets().add("css/solar_rpg_editor_app.css");
            preferencesFxDialog.getButtonTypes().clear();
            preferencesFxDialog.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            preferencesFxDialog.setPrefSize(700, 500);
            dialog.initStyle(StageStyle.UTILITY);
        }
        preferencesFx.show(true);
    }
}
