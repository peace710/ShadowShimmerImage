package me.peace.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.peace.widget.config.ImageConfig;

public class SSImageView extends ShimmerImageView {
    private ImageConfig mConfig;
    private Drawable mDrawable;

    public SSImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public SSImageView(Context context) {
        super(context);
    }

    public SSImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SSImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SSImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void shadowBounds(boolean showShadow){
        if (mConfig != null){
            mDrawable = mConfig.getShadow(getContext());
            if (mDrawable != null) {
                int offset = mConfig.getOffset(getContext());
                int w = getWidth();
                int h = getHeight();
                if (showShadow) {
                    mDrawable.setBounds(-offset,-offset,w + offset,h + offset);
                }else{
                    mDrawable.setBounds(0,0,w,h);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null){
            mDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    public void config(ImageConfig config) {
        this.mConfig = config;
    }
}
