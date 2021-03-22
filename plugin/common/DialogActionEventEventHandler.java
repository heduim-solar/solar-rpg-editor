package org.solar.editor.plugin.common;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import org.solar.concurrent.ThreadUtil;
import org.solar.jfx.app.SolarApp;
import org.solar.jfx.util.DialogUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.jfx.util.PopupUtil;
import org.solar.log.Log;

import java.util.concurrent.Callable;

public class DialogActionEventEventHandler implements EventHandler<ActionEvent> {
    private String name;
    private Runnable runnable;
    private Node inSceneNode;
    private Dialog onEndCloseDialog;

    public DialogActionEventEventHandler(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }

    public DialogActionEventEventHandler(Node inSceneNode, String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
        this.inSceneNode = inSceneNode;
    }

    public DialogActionEventEventHandler(Dialog OnEndCloseDialog, String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
        this.inSceneNode = OnEndCloseDialog.getDialogPane();
        this.onEndCloseDialog = OnEndCloseDialog;
    }

    @Override
    public void handle(ActionEvent event) {
        if (runnable == null) {
            return;
        }
        ThreadUtil.refreshStartTime();
        if (runnable instanceof Task) {
            executeTask((Task) runnable);
        } else if (runnable instanceof Callable) {
            Callable closure = (Callable) runnable;
            Object result = null;
            try {
                result = closure.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result instanceof Task) {
                executeTask((Task) result);
            } else {
                Log.info("【" + name + "】:执行完毕");
                OnEnd();
            }
        } else {
            runnable.run();
            int useTime = ThreadUtil.getUseTime();
            if (useTime > 200) {
                Log.info("【" + name + "】:执行完毕 耗时：" + ThreadUtil.getUseTimeInfo() + " ");
            } else {
                Log.info("【" + name + "】:执行完毕");
            }
            OnEnd();
        }

    }


    public void executeTask(Task task) {
        Dialog dialog = DialogUtil.ProgressDialog(task);
        task.setOnSucceeded(e -> {
            int useTime = ThreadUtil.getUseTime();
            if (useTime > 200) {
                Log.info("【" + name + "】:执行完毕 耗时：" + ThreadUtil.getUseTimeInfo() + " ");
            } else {
                Log.info("【" + name + "】:执行完毕");
            }
            OnEnd();

        });
        task.setOnCancelled(e -> {
            Log.info("【" + name + "】:task OnCancelled");
        });
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            Log.info("【" + name + "】:task OnFailed");
        });
        Platform.runLater(() -> {
            dialog.show();//如果这里不延迟弹dialog 会影响按钮的setOnMouseClicked 不触发
        });
        ThreadUtil.execute(task);
    }

    public void OnEnd() {
        Popup popup = null;
        if (inSceneNode != null) {
            popup = PopupUtil.showTipCenter(inSceneNode, "【" + name + "】:执行完毕");
        } else {
            popup = PopupUtil.tip("【" + name + "】:执行完毕", 1);
            popup.show(SolarApp.stage);
        }

        if (onEndCloseDialog != null) {
            popup.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    FxUtil.runLater(() -> {
                        onEndCloseDialog.hide();
                    });
                }
            });
        }

    }

}
