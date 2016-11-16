package com.anwesome.games.stacklayoutview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by anweshmishra on 16/11/16.
 */
public class StackViewElement extends View {
    private Bitmap closeIcon;
    private Bitmap viewBitmap;
    private int w,h,displayedHeight;
    private int time = 0;
    private String colorString = "#3F51B5";
    private float top,left;
    private float closeIconX,closeIconY;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean startClosing = false;
    private View initialView;
    public StackViewElement(Context context,Bitmap viewBitmap) {
        super(context);
        this.viewBitmap = viewBitmap;
    }
    public void setDisplayedHeight(int displayedHeight) {
        this.displayedHeight = displayedHeight;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            init(canvas.getWidth(),canvas.getHeight());
        }
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.parseColor(colorString));
        canvas.drawRect(new RectF(0,0,w,h/10),paint);
        canvas.drawBitmap(closeIcon,closeIconX,closeIconY,paint);
        if(viewBitmap!=null) {
            canvas.save();
            canvas.translate(w / 2,h/2);
            canvas.drawBitmap(viewBitmap,-viewBitmap.getWidth()/2,-viewBitmap.getHeight()/2,paint);
            canvas.restore();
        }
        time++;
        if(startClosing) {
            left+=w/10;
            try {
                Thread.sleep(20);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public void init(int w,int h) {
        this.w = w;
        this.h = h;
        closeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.close);
        this.closeIconX = 4*w/5;
        this.closeIconY = h/60;
    }


    public float getTopOfElement() {
        return top;
    }



    public float getLeftOfElement() {
        return left;
    }


    public void setTopOfElement(float top) {
        this.top = top;

    }
    public void setLeftOfElement(float left) {
        this.left = left;
    }
    public boolean containsTouch(float x,float y) {
        return x>=left && x<=left+w && y>=top && y<=top+displayedHeight;
    }
    public  boolean touchOnClose(float x,float y) {
        return x>= this.left+closeIconX && x<=this.left+closeIconX+closeIcon.getWidth() && y>=this.top+closeIconY && y<=this.top+closeIconY+closeIcon.getHeight();
    }
    public void handleTouch(MotionEvent event) {
        if(!startClosing && touchOnClose(event.getX(),event.getY())) {
            startClosing = true;
            postInvalidate();
        }
        else {
            //initialView.dispatchTouchEvent(event);
        }
    }

}
