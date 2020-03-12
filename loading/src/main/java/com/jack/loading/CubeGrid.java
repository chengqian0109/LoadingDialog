package com.jack.loading;

import android.graphics.Rect;


/**
 * Created by ybq.
 */
public class CubeGrid extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        int delays[] = new int[]{200, 300, 400, 100, 200, 300, 0, 100, 200};
        ShapeSprite[] gridItems = new ShapeSprite[9];
        for (int i = 0; i < gridItems.length; i++) {
            gridItems[i] = new ShapeSprite();
            gridItems[i].setAnimationDelay(delays[i]);
        }
        return gridItems;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int width = (int) (bounds.width() * 0.33f);
        int height = (int) (bounds.height() * 0.33f);
        for (int i = 0; i < getChildCount(); i++) {
            int x = i % 3;
            int y = i / 3;
            int l = bounds.left + x * width;
            int t = bounds.top + y * height;
            Sprite sprite = getChildAt(i);
            sprite.setDrawBounds(l, t, l + width, t + height);
        }
    }
}
