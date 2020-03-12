package com.jack.loading;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

/**
 * Created by ybq.
 */
public class ShapeSprite extends Sprite {

    private Paint mPaint;
    private int mUseColor;
    private int mBaseColor;

    public ShapeSprite() {
        setColor(Color.WHITE);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mUseColor);
    }

    @Override
    public int getColor() {
        return mBaseColor;
    }

    @Override
    public void setColor(int color) {
        mBaseColor = color;
        updateUseColor();
    }

    @SuppressWarnings("unused")
    public int getUseColor() {
        return mUseColor;
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        updateUseColor();
    }

    private void updateUseColor() {
        int alpha = getAlpha();
        alpha += alpha >> 7;
        final int baseAlpha = mBaseColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        mUseColor = (mBaseColor << 8 >>> 8) | (useAlpha << 24);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    protected final void drawSelf(Canvas canvas) {
        mPaint.setColor(mUseColor);
        drawShape(canvas, mPaint);
    }


    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.35f, 0.7f, 1f};
        return new SpriteAnimatorBuilder(this).scale(fractions, 1f, 0f, 1f, 1f)
                .duration(1300).easeInOut(fractions).build();
    }

    public void drawShape(Canvas canvas, Paint paint) {
        if (getDrawBounds() != null) {
            canvas.drawRect(getDrawBounds(), paint);
        }
    }
}
