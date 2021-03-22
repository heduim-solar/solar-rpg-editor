package org.solar.editor.plugin.util;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.solar.editor.plugin.common.DialogActionEventEventHandler;
import org.solar.jfx.control.SolarForm;
import org.solar.jfx.util.DialogUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.jfx.util.NodeUtil;
import org.solar.lang.StringUtil;

import java.util.Map;

public class BaseCodeGeneratorFxUtil {


    public static Pane newCodeGeneratorPane(String name, String tips, Map params, Runnable runnable) {
        return newCodeGeneratorPane(name, tips, params, runnable, null);
    }

    public static Pane newCodeGeneratorPane(String name, String tips, Map params, Runnable runnable, Dialog onEndCloseDialog) {
        VBox pane = new VBox();
        pane.setMinSize(200, 200);
        pane.setSpacing(10);
        //SolarForm
        if (tips != null && StringUtil.isNotEmpty(tips.trim())) {
            TitledPane tp = new TitledPane();
            tp.setText(name);
            Label content = new Label(tips.trim());
            NodeUtil.setOnMouseDoubleClicked(content, () -> {
                TextArea textArea = new TextArea(tips.trim());
                textArea.setEditable(true);
                textArea.setWrapText(true);
                textArea.setStyle("-fx-background-color: transparent;");
                tp.setContent(textArea);
            });
            tp.setContent(content);
            tp.setExpanded(true);
            FxUtil.setPadding(8, 8, 8, 8, (Region) tp.getContent());
            pane.getChildren().add(tp);
        }
        //SolarForm
        SolarForm paramsSolarForm = new SolarForm(params);
//        if (params.size() > 15) {
//            pane.getChildren().add(new TitledPane("参数设置", paramsSolarForm))
//        } else {
        pane.getChildren().add(paramsSolarForm);
//        }
        //line
        HBox line = FxUtil.line("#222222", 1, pane.widthProperty().subtract(5));
        pane.getChildren().add(line);
        if (runnable != null) {
            //button
            Button button = new JFXButton(String.valueOf(name));
            button.setWrapText(true);
            button.setStyle("-jfx-button-type: RAISED; -fx-background-color: #409EFF; -fx-text-fill: white;");
            FxUtil.setHeight(button, 60);

            if (onEndCloseDialog == null) {
                button.setOnAction(
                        new DialogActionEventEventHandler(button, String.valueOf(name), runnable));
            } else {

                button.setOnAction(
                        new DialogActionEventEventHandler(onEndCloseDialog, String.valueOf(name), runnable));
            }
            pane.getChildren().add(button);
            FxUtil.bindWidthProperty(button, pane.widthProperty().subtract(10));
        }
        return pane;
    }


}
