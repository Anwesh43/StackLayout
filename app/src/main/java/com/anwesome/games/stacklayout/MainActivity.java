package com.anwesome.games.stacklayout;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.anwesome.games.stacklayoutview.StackLayoutView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StackLayoutView stackLayoutView = new StackLayoutView(this,new ArrayList<Bitmap>(){{
            add(BitmapFactory.decodeResource(getResources(),R.drawable.popper_icon_256));
            add(BitmapFactory.decodeResource(getResources(),R.drawable.popper_icon_256));
            add(BitmapFactory.decodeResource(getResources(),R.drawable.popper_icon_256));
            add(BitmapFactory.decodeResource(getResources(),R.drawable.popper_icon_256));
            add(BitmapFactory.decodeResource(getResources(),R.drawable.popper_icon_256));
        }});
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        stackLayoutView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(stackLayoutView);
    }
}
