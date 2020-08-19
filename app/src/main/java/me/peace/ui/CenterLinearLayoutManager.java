package me.peace.ui;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CenterLinearLayoutManager extends LinearLayoutManager {

    public CenterLinearLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        setRecycleChildrenOnDetach(true);
        setInitialPrefetchItemCount(2);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 50;
    }

    @Override
    public boolean requestChildRectangleOnScreen(@NonNull final RecyclerView parent, @NonNull final View child, @NonNull Rect rect, boolean immediate, boolean focusedChildVisible) {
        moveViewToCenter(child.findFocus(), parent);
        return true;
    }

    private View lastScrollView;

    public void moveViewToCenter(final View view, RecyclerView parent) {
        if (view == null || view == lastScrollView) {
            return;
        }
        if (parent.getScrollState() == 2) {
            parent.stopScroll();
            parent.scrollBy(0, make(lastScrollView, parent));
        }
        int dy = make(view, parent);
        if (Math.abs(dy) > 10) {
            parent.smoothScrollBy(0, dy);
        }
    }

    private int make(View view, View parent) {
        int rect[] = new int[2];
        int rect1[] = new int[2];
        lastScrollView = view;
        view.getLocationOnScreen(rect);
        parent.getLocationOnScreen(rect1);

        int viewHeight = view.getHeight();
        int ty = (parent.getHeight() - viewHeight) / 2;
        int dy = rect[1] - ty - rect1[1];
        return dy;
    }
}