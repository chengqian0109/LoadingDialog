package com.jack.loading;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ybq.
 */
public class SpriteAnimatorBuilder {
    private Sprite sprite;
    private List<PropertyValuesHolder> propertyValuesHolders = new ArrayList<>();
    private Interpolator interpolator;
    private int repeatCount = Animation.INFINITE;
    private long duration = 2000;

    public SpriteAnimatorBuilder(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteAnimatorBuilder scale(float fractions[], float... scale) {
        holder(fractions, Sprite.SCALE, scale);
        return this;
    }

    private PropertyValuesHolder holder(float[] fractions, Property property, float[] values) {
        ensurePair(fractions.length, values.length);
        Keyframe[] keyframes = new Keyframe[fractions.length];
        for (int i = 0; i < values.length; i++) {
            keyframes[i] = Keyframe.ofFloat(fractions[i], values[i]);
        }
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.
                ofKeyframe(property
                        , keyframes
                );
        propertyValuesHolders.add(valuesHolder);
        return valuesHolder;
    }


    private void ensurePair(int fractionsLength, int valuesLength) {
        if (fractionsLength != valuesLength) {
            throw new IllegalStateException(String.format(
                    Locale.getDefault(),
                    "The fractions.length must equal values.length, " +
                            "fraction.length[%d], values.length[%d]",
                    fractionsLength,
                    valuesLength));
        }
    }


    public SpriteAnimatorBuilder interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public SpriteAnimatorBuilder easeInOut(float... fractions) {
        interpolator(KeyFrameInterpolator.easeInOut(fractions));
        return this;
    }


    public SpriteAnimatorBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    @SuppressWarnings("unused")
    public SpriteAnimatorBuilder repeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public ObjectAnimator build() {
        PropertyValuesHolder[] holders = new PropertyValuesHolder[propertyValuesHolders.size()];
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(sprite,
                propertyValuesHolders.toArray(holders));
        animator.setDuration(duration);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(interpolator);
        return animator;
    }
}
