package com.jack.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;


/**
 * Created by ybq.
 */
public class LoadingView extends ProgressBar {

    private int mColor;
    private Sprite mSprite;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.LoadViewStyle);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.SpinKitView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr,
                defStyleRes);
        mColor = a.getColor(R.styleable.LoadingView_view_color, Color.WHITE);
        a.recycle();
        init();
        setIndeterminate(true);
    }

    private void init() {
        Sprite sprite = new CubeGrid();
        setIndeterminateDrawable(sprite);
    }

    @Override
    public void setIndeterminateDrawable(Drawable d) {
        if (!(d instanceof Sprite)) {
            throw new IllegalArgumentException("this d must be instanceof Sprite");
        }
        setIndeterminateDrawable((Sprite) d);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public Sprite getIndeterminateDrawable() {
        return mSprite;
    }

    public void setIndeterminateDrawable(Sprite d) {
        super.setIndeterminateDrawable(d);
        mSprite = d;
        if (mSprite.getColor() == 0) {
            mSprite.setColor(mColor);
        }
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        if (getVisibility() == VISIBLE) {
            mSprite.start();
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who) {
        super.unscheduleDrawable(who);
        if (who instanceof Sprite) {
            ((Sprite) who).stop();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (mSprite != null && getVisibility() == VISIBLE) {
                mSprite.start();
            }
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (screenState == View.SCREEN_STATE_OFF) {
            if (mSprite != null) {
                mSprite.stop();
            }
        }
    }

}
