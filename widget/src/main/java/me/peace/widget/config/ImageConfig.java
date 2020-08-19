package me.peace.widget.config;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;

public class ImageConfig {
    private int mShadowOffset;
    private int mShadowResId;

    public ImageConfig(Builder builder) {
        this.mShadowOffset = builder.mOffset;
        this.mShadowResId = builder.mShadow;
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


    public static class Builder{
        private int mOffset;
        private int mShadow;

        public Builder offset(@DimenRes int offset) {
            this.mOffset = offset;
            return this;
        }

        public Builder shadow(@DrawableRes int shadow) {
            this.mShadow = shadow;
            return this;
        }

        public ImageConfig build(){
            return new ImageConfig(this);
        }
    }
}
