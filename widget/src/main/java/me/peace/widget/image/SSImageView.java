package me.peace.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.peace.widget.config.ImageConfig;

public class SSImageView extends ShimmerImageView {
    private Drawable mDrawable;
    private int mOffset;
    private boolean mShadowFocused;
    private boolean mEnableShadow;
    private boolean mEnableShimmer;


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

    @Override
    protected void onDraw(Canvas canvas) {
        if (mEnableShadow && mShadowFocused && mDrawable != null) {
            mDrawable.draw(canvas);
        }
        super.onDraw(canvas,mEnableShimmer);

    }

    public void config(ImageConfig config) {
        if (config != null) {
            Context context = getContext();
            mDrawable = config.getShadow(context);
            mOffset = config.getOffset(context);
            mEnableShadow = config.isEnableShadow();
            mEnableShimmer = config.isEnableShimmer();
        }else{
            mDrawable = null;
            mOffset = 0;
            mEnableShadow = false;
            mEnableShimmer = false;
        }
    }

    public void start(){
        mShadowFocused = true;
        if (mEnableShadow && mDrawable != null){
            int w = getWidth();
            int h = getHeight();
            mDrawable.setBounds(-mOffset, -mOffset, w + mOffset, h + mOffset);
        }
        if (mEnableShimmer){
            shimmer();
            startShimmer();
        }
    }

    public void stop(){
        mShadowFocused = false;
        if (mEnableShimmer){
            stopShimmer();
        }
    }
}
