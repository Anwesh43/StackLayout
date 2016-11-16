package com.anwesome.games.stacklayoutview;

import android.animation.Animator;
import android.animation.ValueAnimator;

/**
 * Created by anweshmishra on 16/11/16.
 */
public class AnimationUtil {
    public static void doViewCloseAnimation(Animator.AnimatorListener endListener,final StackViewElement element,final StackLayoutView stackLayoutView,int width) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(element.getLeftOfElement(),width);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                element.setLeftOfElement((Float)valueAnimator.getAnimatedValue());
                stackLayoutView.requestLayout();
            }
        });
        valueAnimator.addListener(endListener);
        valueAnimator.start();
    }
}
