package org.solar.editor.core.jme.injfx.input;

import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.ss.rlib.common.util.linkedlist.LinkedList;
import javafx.scene.Scene;
import org.solar.editor.core.jme.injfx.JmeOffscreenSurfaceContext;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.ss.rlib.common.util.linkedlist.LinkedListFactory.newLinkedList;

/**
 * The implementation of the {@link KeyInput} for using in the {@link ImageView}.
 *
 * @author JavaSaBr
 */
public class JfxKeyInput extends JfxInput implements KeyInput {

    private static final Map<KeyCode, Integer> KEY_CODE_TO_JME = new HashMap<>();

    static {
        KEY_CODE_TO_JME.put(KeyCode.ESCAPE, KEY_ESCAPE);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT0, KEY_0);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT1, KEY_1);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT2, KEY_2);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT3, KEY_3);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT4, KEY_4);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT5, KEY_5);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT6, KEY_6);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT7, KEY_7);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT8, KEY_8);
        KEY_CODE_TO_JME.put(KeyCode.DIGIT9, KEY_9);
        KEY_CODE_TO_JME.put(KeyCode.MINUS, KEY_MINUS);
        KEY_CODE_TO_JME.put(KeyCode.EQUALS, KEY_EQUALS);
        KEY_CODE_TO_JME.put(KeyCode.BACK_SPACE, KEY_BACK);
        KEY_CODE_TO_JME.put(KeyCode.TAB, KEY_TAB);
        KEY_CODE_TO_JME.put(KeyCode.Q, KEY_Q);
        KEY_CODE_TO_JME.put(KeyCode.W, KEY_W);
        KEY_CODE_TO_JME.put(KeyCode.E, KEY_E);
        KEY_CODE_TO_JME.put(KeyCode.R, KEY_R);
        KEY_CODE_TO_JME.put(KeyCode.T, KEY_T);
        KEY_CODE_TO_JME.put(KeyCode.U, KEY_U);
        KEY_CODE_TO_JME.put(KeyCode.I, KEY_I);
        KEY_CODE_TO_JME.put(KeyCode.O, KEY_O);
        KEY_CODE_TO_JME.put(KeyCode.P, KEY_P);
        KEY_CODE_TO_JME.put(KeyCode.OPEN_BRACKET, KEY_LBRACKET);
        KEY_CODE_TO_JME.put(KeyCode.CLOSE_BRACKET, KEY_RBRACKET);
        KEY_CODE_TO_JME.put(KeyCode.ENTER, KEY_RETURN);
        KEY_CODE_TO_JME.put(KeyCode.CONTROL, KEY_LCONTROL);
        KEY_CODE_TO_JME.put(KeyCode.A, KEY_A);
        KEY_CODE_TO_JME.put(KeyCode.S, KEY_S);
        KEY_CODE_TO_JME.put(KeyCode.D, KEY_D);
        KEY_CODE_TO_JME.put(KeyCode.F, KEY_F);
        KEY_CODE_TO_JME.put(KeyCode.G, KEY_G);
        KEY_CODE_TO_JME.put(KeyCode.H, KEY_H);
        KEY_CODE_TO_JME.put(KeyCode.J, KEY_J);
        KEY_CODE_TO_JME.put(KeyCode.Y, KEY_Y);
        KEY_CODE_TO_JME.put(KeyCode.K, KEY_K);
        KEY_CODE_TO_JME.put(KeyCode.L, KEY_L);
        KEY_CODE_TO_JME.put(KeyCode.SEMICOLON, KEY_SEMICOLON);
        KEY_CODE_TO_JME.put(KeyCode.QUOTE, KEY_APOSTROPHE);
        KEY_CODE_TO_JME.put(KeyCode.DEAD_GRAVE, KEY_GRAVE);
        KEY_CODE_TO_JME.put(KeyCode.SHIFT, KEY_LSHIFT);
        KEY_CODE_TO_JME.put(KeyCode.BACK_SLASH, KEY_BACKSLASH);
        KEY_CODE_TO_JME.put(KeyCode.Z, KEY_Z);
        KEY_CODE_TO_JME.put(KeyCode.X, KEY_X);
        KEY_CODE_TO_JME.put(KeyCode.C, KEY_C);
        KEY_CODE_TO_JME.put(KeyCode.V, KEY_V);
        KEY_CODE_TO_JME.put(KeyCode.B, KEY_B);
        KEY_CODE_TO_JME.put(KeyCode.N, KEY_N);
        KEY_CODE_TO_JME.put(KeyCode.M, KEY_M);
        KEY_CODE_TO_JME.put(KeyCode.COMMA, KEY_COMMA);
        KEY_CODE_TO_JME.put(KeyCode.PERIOD, KEY_PERIOD);
        KEY_CODE_TO_JME.put(KeyCode.SLASH, KEY_SLASH);
        KEY_CODE_TO_JME.put(KeyCode.MULTIPLY, KEY_MULTIPLY);
        KEY_CODE_TO_JME.put(KeyCode.SPACE, KEY_SPACE);
        KEY_CODE_TO_JME.put(KeyCode.CAPS, KEY_CAPITAL);
        KEY_CODE_TO_JME.put(KeyCode.F1, KEY_F1);
        KEY_CODE_TO_JME.put(KeyCode.F2, KEY_F2);
        KEY_CODE_TO_JME.put(KeyCode.F3, KEY_F3);
        KEY_CODE_TO_JME.put(KeyCode.F4, KEY_F4);
        KEY_CODE_TO_JME.put(KeyCode.F5, KEY_F5);
        KEY_CODE_TO_JME.put(KeyCode.F6, KEY_F6);
        KEY_CODE_TO_JME.put(KeyCode.F7, KEY_F7);
        KEY_CODE_TO_JME.put(KeyCode.F8, KEY_F8);
        KEY_CODE_TO_JME.put(KeyCode.F9, KEY_F9);
        KEY_CODE_TO_JME.put(KeyCode.F10, KEY_F10);
        KEY_CODE_TO_JME.put(KeyCode.NUM_LOCK, KEY_NUMLOCK);
        KEY_CODE_TO_JME.put(KeyCode.SCROLL_LOCK, KEY_SCROLL);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD7, KEY_NUMPAD7);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD8, KEY_NUMPAD8);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD9, KEY_NUMPAD9);
        KEY_CODE_TO_JME.put(KeyCode.SUBTRACT, KEY_SUBTRACT);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD4, KEY_NUMPAD4);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD5, KEY_NUMPAD5);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD6, KEY_NUMPAD6);
        KEY_CODE_TO_JME.put(KeyCode.ADD, KEY_ADD);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD1, KEY_NUMPAD1);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD2, KEY_NUMPAD2);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD3, KEY_NUMPAD3);
        KEY_CODE_TO_JME.put(KeyCode.NUMPAD0, KEY_NUMPAD0);
        KEY_CODE_TO_JME.put(KeyCode.DECIMAL, KEY_DECIMAL);
        KEY_CODE_TO_JME.put(KeyCode.F11, KEY_F11);
        KEY_CODE_TO_JME.put(KeyCode.F12, KEY_F12);
        KEY_CODE_TO_JME.put(KeyCode.F13, KEY_F13);
        KEY_CODE_TO_JME.put(KeyCode.F14, KEY_F14);
        KEY_CODE_TO_JME.put(KeyCode.F15, KEY_F15);
        KEY_CODE_TO_JME.put(KeyCode.KANA, KEY_KANA);
        KEY_CODE_TO_JME.put(KeyCode.CONVERT, KEY_CONVERT);
        KEY_CODE_TO_JME.put(KeyCode.NONCONVERT, KEY_NOCONVERT);
        KEY_CODE_TO_JME.put(KeyCode.CIRCUMFLEX, KEY_CIRCUMFLEX);
        KEY_CODE_TO_JME.put(KeyCode.AT, KEY_AT);
        KEY_CODE_TO_JME.put(KeyCode.COLON, KEY_COLON);
        KEY_CODE_TO_JME.put(KeyCode.UNDERSCORE, KEY_UNDERLINE);
        KEY_CODE_TO_JME.put(KeyCode.STOP, KEY_STOP);
        KEY_CODE_TO_JME.put(KeyCode.DIVIDE, KEY_DIVIDE);
        KEY_CODE_TO_JME.put(KeyCode.PAUSE, KEY_PAUSE);
        KEY_CODE_TO_JME.put(KeyCode.HOME, KEY_HOME);
        KEY_CODE_TO_JME.put(KeyCode.UP, KEY_UP);
        KEY_CODE_TO_JME.put(KeyCode.PAGE_UP, KEY_PRIOR);
        KEY_CODE_TO_JME.put(KeyCode.LEFT, KEY_LEFT);
        KEY_CODE_TO_JME.put(KeyCode.RIGHT, KEY_RIGHT);
        KEY_CODE_TO_JME.put(KeyCode.END, KEY_END);
        KEY_CODE_TO_JME.put(KeyCode.DOWN, KEY_DOWN);
        KEY_CODE_TO_JME.put(KeyCode.PAGE_DOWN, KEY_NEXT);
        KEY_CODE_TO_JME.put(KeyCode.INSERT, KEY_INSERT);
        KEY_CODE_TO_JME.put(KeyCode.DELETE, KEY_DELETE);
        KEY_CODE_TO_JME.put(KeyCode.ALT, KEY_LMENU);
        KEY_CODE_TO_JME.put(KeyCode.META, KEY_RCONTROL);
    }

    @NotNull
    private final EventHandler<KeyEvent> processKeyPressed = this::processKeyPressed;

    @NotNull
    private final EventHandler<KeyEvent> processKeyReleased = this::processKeyReleased;

    @NotNull
    private final LinkedList<KeyInputEvent> keyInputEvents;

    public JfxKeyInput(@NotNull JmeOffscreenSurfaceContext context) {
        super(context);
        this.keyInputEvents = newLinkedList(KeyInputEvent.class);
    }

    @Override
    public void bind(@NotNull Node node) {
        super.bind(node);
        node.addEventHandler(KeyEvent.KEY_PRESSED, processKeyPressed);
        node.addEventHandler(KeyEvent.KEY_RELEASED, processKeyReleased);
    }

    public void bind(Node node, Scene scene) {
        super.bind(node);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, processKeyPressed);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, processKeyReleased);
    }

    @Override
    public void unbind() {

        if (hasNode()) {
            Node node = getNode();
            node.removeEventHandler(KeyEvent.KEY_PRESSED, processKeyPressed);
            node.removeEventHandler(KeyEvent.KEY_RELEASED, processKeyReleased);
        }

        super.unbind();
    }

    private void processKeyReleased(@NotNull KeyEvent keyEvent) {
        onKeyEvent(keyEvent, false);
    }

    private void processKeyPressed(@NotNull KeyEvent keyEvent) {
        onKeyEvent(keyEvent, true);
    }

    private void onKeyEvent(@NotNull KeyEvent keyEvent, boolean pressed) {
        if (!node.isFocused() && !node.isHover()) {
            return;
        }
        int code = convertKeyCode(keyEvent.getCode());
        String character = keyEvent.getText();
        char keyChar = character.isEmpty() ? '\0' : character.charAt(0);

        KeyInputEvent event = new KeyInputEvent(code, keyChar, pressed, false);
        event.setTime(getInputTimeNanos());

        EXECUTOR.addToExecute(() -> keyInputEvents.add(event));
    }

    @Override
    protected void updateImpl() {
        var listener = getListener();
        while (!keyInputEvents.isEmpty()) {
            listener.onKeyEvent(keyInputEvents.poll());
        }
    }

    private int convertKeyCode(@NotNull KeyCode keyCode) {
        var code = KEY_CODE_TO_JME.get(keyCode);
        return code == null ? KEY_UNKNOWN : code;
    }
}
