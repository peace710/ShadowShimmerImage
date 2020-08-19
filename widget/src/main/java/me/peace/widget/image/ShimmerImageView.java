package me.peace.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.peace.widget.ShimmerDrawable;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

public class ShimmerImageView extends UriImageView {
    private static final String TAG = "ShimmerImageView";
    private final Paint mContentPaint = new Paint();
    protected ShimmerDrawable mShimmerDrawable = new ShimmerDrawable();

    public ShimmerImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context, null);
    }

    public ShimmerImageView(Context context) {
        super(context);
        init(context, null);
    }

    public ShimmerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShimmerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ShimmerImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setWillNotDraw(false);
        mShimmerDrawable.setCallback(this);
    }

    public void shimmer(){
        mShimmerDrawable.updateDrawable();
    }

    public void startShimmer() {
        mShimmerDrawable.startShimmer();
    }

    public void stopShimmer() {
        mShimmerDrawable.stopShimmer();
    }

    public boolean isShimmerStarted() {
        return mShimmerDrawable.isShimmerStarted();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mShimmerDrawable.setBounds(0,0,getWidth(),getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(), mContentPaint, ALL_SAVE_FLAG);
        super.onDraw(canvas);
        mShimmerDrawable.draw(canvas);
        canvas.restoreToCount(layerId);
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || who == mShimmerDrawable;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopShimmer();
    }
}
