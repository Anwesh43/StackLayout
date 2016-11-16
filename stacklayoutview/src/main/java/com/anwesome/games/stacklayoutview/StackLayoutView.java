package com.anwesome.games.stacklayoutview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 16/11/16.
 */
public class StackLayoutView extends ViewGroup {
    private int width,height,currentIndex = 0;
    private boolean isDown = false;
    private float prevY = 0;
    private boolean loaded = false;
    private List<Bitmap> bitmaps = new ArrayList<>();

    private List<StackViewElement> elements = new ArrayList<>();
    public StackLayoutView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public StackLayoutView(Context context,List<Bitmap> bitmaps) {
        super(context);
        this.bitmaps = bitmaps;
    }
    public void onLayout(boolean changed,int a,int b,int w,int h){
        if(elements.size()>0) {
            int gap = height / (2 * elements.size());
            int index = 0, top = gap * index;
            int elevation = 5;
            for (StackViewElement element : elements) {
                if (element.getLeft() > w) {
                    removeView(element);
                    continue;
                }

                element.layout((int) element.getLeftOfElement(), top, (int) element.getLeftOfElement() + (3 * width) / 4, top + 3 * height / 4);
                element.setTopOfElement(top);
                element.setDisplayedHeight(gap);
                element.setScaleX(1+0.01f*(index-currentIndex));
                element.setScaleY(1+0.1f*(index-currentIndex));
                //element.setElevation(elevation);
                //element.setTranslationZ(elevation);
                top += gap;
                if (index == currentIndex) {
                    top += height / 2;
                    element.setDisplayedHeight(gap + height / 2);
                }
                elevation += 20;
                index++;
            }
        }

    }
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        Point size = new Point();
        getDisplay().getRealSize(size);
        width = size.x;
        height = size.y;
        if(!loaded) {
            for(Bitmap bitmap:bitmaps) {
                StackViewElement element = new StackViewElement(getContext(), bitmap);
                element.setLayoutParams(new LayoutParams((4 * width) / 5, (3 * height) / 4));
                element.setLeftOfElement(width / 10);
                elements.add(element);
            }
//            for (int i = 0; i < getChildCount(); i++) {
//                View child = getChildAt(i);
//                measureChild(child, widthMeasureSpec, heightMeasureSpec);
//                child.setDrawingCacheEnabled(true);
//                child.buildDrawingCache();
//                StackViewElement element = new StackViewElement(getContext(), child);
//                element.setLayoutParams(new LayoutParams((4 * width) / 5, (3 * height) / 4));
//                element.setLeft(width / 10);
//                elements.add(element);
//            }
//            removeAllViews();
            for (StackViewElement element : elements) {
                addView(element);
                measureChild(element, widthMeasureSpec, heightMeasureSpec);
            }
            loaded = true;
        }
        setMeasuredDimension(width, height);
    }
    private void enableScroll(float y) {
        prevY = y;
        isDown = true;
    }
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                boolean touchOnElement = false;
                StackViewElement touchedElement = null;
                for(int i=0;i<elements.size();i++) {
                    if(elements.get(i).containsTouch(event.getX(),event.getY())) {
                        touchOnElement = true;
                        touchedElement = elements.get(i);
                        break;
                    }
                }
                if(!isDown && !touchOnElement) {
                    enableScroll(event.getY());
                }
                else if(touchOnElement) {
                    //touchedElement.handleTouch(event);
                    final StackViewElement element = touchedElement;
                    if(touchedElement.touchOnClose(event.getX(),event.getY())) {
                        AnimationUtil.doViewCloseAnimation(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                removeView(element);
                                elements.remove(element);
                                if(currentIndex == elements.size()) {
                                    currentIndex = elements.size() - 1;
                                }

                            }
                        },touchedElement,this,width);
                    }
                    else {
                        enableScroll(event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDown && Math.abs(event.getY() - prevY) >= height / 20) {
                    if (event.getY() > prevY) {
                        currentIndex++;
                        currentIndex %= elements.size();
                    } else if (event.getY() < prevY ) {
                        currentIndex--;
                        if (currentIndex < 0) {
                            currentIndex = elements.size() - 1;
                        }
                    }
                    prevY = event.getY();
                }
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                isDown = isDown?false:true;
                break;
        }
        return true;
    }
}
