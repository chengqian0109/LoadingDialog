package com.jack.loading;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

/**
 * Created by ybq.
 */
public class KeyFrameInterpolator implements Interpolator {

    private TimeInterpolator interpolator;
    private float[] fractions;


    public KeyFrameInterpolator(TimeInterpolator interpolator, float... fractions) {
        this.interpolator = interpolator;
        this.fractions = fractions;
    }

    public static KeyFrameInterpolator easeInOut(float... fractions) {
        KeyFrameInterpolator interpolator =
                new KeyFrameInterpolator(PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f));
        interpolator.setFractions(fractions);
        return interpolator;
    }

    public void setFractions(float... fractions) {
        this.fractions = fractions;
    }

    @Override
    public synchronized float getInterpolation(float input) {
        if (fractions.length > 1) {
            for (int i = 0; i < fractions.length - 1; i++) {
                float start = fractions[i];
                float end = fractions[i + 1];
                float duration = end - start;
                if (input >= start && input <= end) {
                    input = (input - start) / duration;
                    return start + (interpolator.getInterpolation(input)
                            * duration);
                }
            }
        }
        return interpolator.getInterpolation(input);
    }
}