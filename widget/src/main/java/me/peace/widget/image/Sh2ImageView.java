package me.peace.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.peace.widget.config.ImageConfig;

public class Sh2ImageView extends ShimmerImageView {
    private ImageConfig mConfig;

    public Sh2ImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public Sh2ImageView(Context context) {
        super(context);
    }

    public Sh2ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sh2ImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Sh2ImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
