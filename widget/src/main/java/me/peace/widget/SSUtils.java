package me.peace.widget;


import me.peace.widget.image.SSImageView;

public class SSUtils {
    public static void focus(SSImageView v,boolean hasFocus){
        if (hasFocus){
            v.start();
        }else{
            v.stop();
        }
    }
}
