package me.peace.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.peace.widget.config.ImageConfig;

public class SSImageView extends ShimmerImageView {
    private Drawable mDrawable;
    private int mOffset;
    private boolean mShadowFocused;
    private boolean mEnableShadow;
    private boolean mEnableShimmer;
    private Rect mNinePatchRect;


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
            if (mEnableShadow && mDrawable != null){
                setLayerType(LAYER_TYPE_NONE,null);
                if (mDrawable instanceof NinePatchDrawable){
                    getNinePatchPadding(mDrawable);
                }
            }
        }else{
            mDrawable = null;
            mOffset = 0;
            mEnableShadow = false;
            mEnableShimmer = false;
            if (mNinePatchRect != null){
                mNinePatchRect.setEmpty();
            }
        }
    }

    public void start(){
        mShadowFocused = true;
        if (mEnableShadow && mDrawable != null){
            int w = getWidth();
            int h = getHeight();
            if (mDrawable instanceof NinePatchDrawable){
                mDrawable.setBounds(-mNinePatchRect.left,-mNinePatchRect.top,
                    w + mNinePatchRect.right,h + mNinePatchRect.bottom);
            }else {
                mDrawable.setBounds(-mOffset, -mOffset, w + mOffset, h + mOffset);
            }
        }
        if (mEnableShimmer){
            shimmer();
            startShimmer();
        }
    }

    private void getNinePatchPadding(Drawable drawable){
        if (mNinePatchRect == null){
            mNinePatchRect = new Rect();
        }
        if (drawable != null){
            drawable.getPadding(mNinePatchRect);
        }
    }

    public void stop(){
        mShadowFocused = false;
        if (mEnableShimmer){
            stopShimmer();
        }
    }
}
