package com.example.pointme.decorator;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class CardViewAnimation {

    public CardViewAnimation() {

    }

    public static void collapseView(final View v, int minHeight) {

        ValueAnimator anim = ValueAnimator.ofInt(v.getMeasuredHeightAndState(),
                minHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);

            }
        });
        anim.start();
    }

    public static void expandView(final View v, int height) {

        ValueAnimator anim = ValueAnimator.ofInt(v.getMeasuredHeightAndState(),
                height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.start();

    }
}
