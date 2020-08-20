package me.peace.widget.config;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;

public class ImageConfig {
    private int mShadowOffset;
    private int mShadowResId;
    private boolean mEnableShadow;
    private boolean mEnableShimmer;

    public ImageConfig(Builder builder) {
        this.mShadowOffset = builder.mOffset;
        this.mShadowResId = builder.mShadow;
        this.mEnableShadow = builder.mEnableShadow;
        this.mEnableShimmer = builder.mEnableShimmer;
    }

    public int getOffset(Context context) {
        if (context != null && mShadowOffset > 0){
            return context.getResources().getDimensionPixelOffset(mShadowOffset);
        }
        return 0;
    }

    public Drawable getShadow(Context context) {
        if (mShadowResId > 0){
           return context.getResources().getDrawable(mShadowResId);
        }
        return null;
    }

    public boolean isEnableShadow() {
        return mEnableShadow;
    }

    public boolean isEnableShimmer() {
        return mEnableShimmer;
    }

    public static class Builder{
        private int mOffset;
        private int mShadow;
        private boolean mEnableShadow;
        private boolean mEnableShimmer;

        public Builder offset(@DimenRes int offset) {
            this.mOffset = offset;
            return this;
        }

        public Builder shadow(@DrawableRes int shadow) {
            this.mShadow = shadow;
            return this;
        }

        public Builder enableShadow(boolean enable) {
            this.mEnableShadow = enable;
            return this;
        }

        public Builder enableShimmer(boolean enable) {
            this.mEnableShimmer = enable;
            return this;
        }

        public ImageConfig build(){
            return new ImageConfig(this);
        }
    }
}
