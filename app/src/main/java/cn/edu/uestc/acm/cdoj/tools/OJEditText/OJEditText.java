package cn.edu.uestc.acm.cdoj.tools.OJEditText;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Grea on 2016/10/20.
 */

public class OJEditText extends EditText {

    Boolean ctrlDown = false;
    Boolean shiftDown = false;

    public interface shortcutListener {
        void undo();
        void redo();
        void copy();
        void paste();
        void selectAll();
        void cut();
    }


    public OJEditText(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(event.getAction()== KeyEvent.ACTION_UP){
            myonKeyUp(event.getKeyCode(),event);
            return true;
        }
        if(event.getAction()== KeyEvent.ACTION_DOWN){
            myonKeyDown(event.getKeyCode(),event);
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_CTRL_LEFT||keyCode== KeyEvent.KEYCODE_CTRL_RIGHT){
            ctrlDown =true;
        }
        if(keyCode== KeyEvent.KEYCODE_SHIFT_LEFT||keyCode== KeyEvent.KEYCODE_SHIFT_RIGHT){
            shiftDown =true;
        }
        if(keyCode== KeyEvent.KEYCODE_Z&& ctrlDown &&!shiftDown){
            listener.undo();
        }
        if(keyCode== KeyEvent.KEYCODE_C&& ctrlDown &&!shiftDown){
            listener.copy();
        }
        if(keyCode== KeyEvent.KEYCODE_V&& ctrlDown &&!shiftDown){
            listener.paste();
        }
        if(keyCode== KeyEvent.KEYCODE_X&& ctrlDown &&!shiftDown){
            listener.cut();
        }
        if(keyCode== KeyEvent.KEYCODE_A&& ctrlDown &&!shiftDown){
            listener.selectAll();
        }
        if(keyCode== KeyEvent.KEYCODE_Z&& ctrlDown && shiftDown){
            listener.redo();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return super.onKeyShortcut(keyCode, event);
    }

    public boolean myonKeyUp(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_CTRL_LEFT||keyCode== KeyEvent.KEYCODE_CTRL_RIGHT){

            ctrlDown =false;
        }
        if(keyCode== KeyEvent.KEYCODE_SHIFT_LEFT||keyCode== KeyEvent.KEYCODE_SHIFT_RIGHT){
            shiftDown =false;
        }
        return super.onKeyUp(keyCode, event);
    }
}
