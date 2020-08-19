package me.peace.widget.image;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.peace.widget.config.ImageConfig;

public class ShadowImageView extends UriImageView {
    private ImageConfig mConfig;

    public ShadowImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ShadowImageView(Context context) {
        super(context);
    }

    public ShadowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShadowImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ShadowImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mConfig != null){
            Drawable drawable = mConfig.getShadow(getContext());
            if (drawable != null) {
                int offset = mConfig.getOffset(getContext());
                int w = getWidth();
                int h = getHeight();
                if (isFocused()) {
                    drawable.setBounds(-offset,-offset,w + offset,h + offset);
                    drawable.draw(canvas);
                }else{
                    drawable.setBounds(0,0,w,h);
                }
            }
        }
        super.onDraw(canvas);
    }

    public void config(ImageConfig config) {
        this.mConfig = config;
    }
}
