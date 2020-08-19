package me.peace.widget;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ShimmerDrawable extends Drawable {
    private static final String TAG = "ShimmerDrawable";
    @ColorInt int highlightColor = 0x3CFAFFF0;
    @ColorInt int baseColor = 0x00000000;
    @FloatRange(from = 0, to = 1) float highlightAlpha = 0.45f;
    @FloatRange(from = 0, to = 1) float baseAlpha;

    float intensity = 0f;
    float dropoff = 0.5f;
    float degrees = 20f;

    final int[] colors = new int[4];

    final float[] positions = new float[4];

    void updateColors(){
        int base = (int) (clamp(0f, 1f, baseAlpha) * 255f);
        int highlight = (int) (clamp(0f, 1f, highlightAlpha) * 255f);

        colors[0] = alphaColor(base,baseColor);
        colors[1] = alphaColor(highlight,highlightColor);
        colors[2] = alphaColor(highlight,highlightColor);
        colors[3] = alphaColor(base,baseColor);
    }

    void updatePositions(){
        positions[0] = Math.max((1f - intensity - dropoff) / 2f, 0f);
        positions[1] = Math.max((1f - intensity - 0.001f) / 2f, 0f);
        positions[2] = Math.min((1f + intensity + 0.001f) / 2f, 1f);
        positions[3] = Math.min((1f + intensity + dropoff) / 2f, 1f);
    }


    private int alphaColor(int alpha,int color){
        return baseColor = alpha << 24 | (color & 0x00FFFFFF);
    }

    private static float clamp(float min, float max, float value) {
        return Math.min(max, Math.max(min, value));
    }

    private final ValueAnimator.AnimatorUpdateListener mUpdateListener =
        animation -> invalidateSelf();

    private final Paint mShimmerPaint = new Paint();


    private final Rect mDrawRect = new Rect();
    private final Matrix mShaderMatrix = new Matrix();

    private @Nullable ValueAnimator mValueAnimator;

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mShimmerPaint.getShader() == null){
            return;
        }

        final float tan = (float)Math.tan(Math.toRadians(degrees));
        final float translateHeight = mDrawRect.height() + tan * mDrawRect.width();
        final float translateWidth = mDrawRect.width() + tan * mDrawRect.height();
        final float dx;
        final float dy;
        final float animatedValue = mValueAnimator != null ? mValueAnimator.getAnimatedFraction() : 0f;
        dx = offset(-translateWidth, translateWidth, animatedValue);
        dy = 0;
        mShaderMatrix.reset();
        mShaderMatrix.setRotate(degrees, mDrawRect.width() / 2f, mDrawRect.height() / 2f);
        mShaderMatrix.postTranslate(dx, dy);
        mShimmerPaint.getShader().setLocalMatrix(mShaderMatrix);
        canvas.drawRect(mDrawRect, mShimmerPaint);
    }

    public ShimmerDrawable() {
        updateColors();
        updatePositions();
        mShimmerPaint.setAntiAlias(true);
        mShimmerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void updateDrawable(){
        updateShader();
        updateValueAnimator();
        invalidateSelf();
    }

    private void updateShader() {
        final Rect bounds = getBounds();
        final int boundsWidth = bounds.width();
        final int boundsHeight = bounds.height();
        if (boundsWidth == 0 || boundsHeight == 0){
            return;
        }
        final Shader shader = new LinearGradient(bounds.left, bounds.top,boundsWidth,0,colors,
            positions,
            Shader.TileMode.CLAMP);
        mShimmerPaint.setShader(shader);
    }

    private void updateValueAnimator() {
        final boolean started;
        if (mValueAnimator != null) {
            started = mValueAnimator.isStarted();
            mValueAnimator.cancel();
            mValueAnimator.removeAllUpdateListeners();
        } else {
            started = false;
        }

        mValueAnimator =
            ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(0);
        mValueAnimator.setDuration(1000L);
        mValueAnimator.addUpdateListener(mUpdateListener);
        if (started) {
            mValueAnimator.start();
        }
    }

    private float offset(float start, float end, float percent) {
        return start + (end - start) * percent;
    }

    public void startShimmer() {
        if (mValueAnimator != null && !isShimmerStarted() && getCallback() != null) {
            mValueAnimator.start();
        }
    }

    public void stopShimmer() {
        if (mValueAnimator != null && isShimmerStarted()) {
            mValueAnimator.cancel();
            mValueAnimator.end();
        }
    }

    public boolean isShimmerStarted() {
        return mValueAnimator != null && mValueAnimator.isStarted();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDrawRect.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        updateShader();
    }
}
