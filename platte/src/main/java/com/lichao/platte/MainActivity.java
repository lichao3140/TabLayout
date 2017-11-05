package com.lichao.platte;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            //发生主线程    Palette调色板   总共六种颜色
            @Override
            public void onGenerated(Palette palette) {
                //柔和而暗的颜色
                int darkMutedColor= palette.getDarkMutedColor(Color.BLUE);
                //鲜艳和暗的颜色
                int darkVibrantColor= palette.getDarkVibrantColor(Color.BLUE);
                //亮和鲜艳的颜色
                int lightVibrantColor=palette.getLightVibrantColor(Color.BLUE);
                //亮和柔和的颜色
                int lightMutedColor=palette.getLightMutedColor(Color.BLUE);
                //柔和颜色
                int mutedColor=palette.getMutedColor(Color.BLUE);
                int vibrantColor=palette.getVibrantColor(Color.BLUE);

                t1.setBackgroundColor(darkMutedColor);
                t2.setBackgroundColor(darkVibrantColor);
                t3.setBackgroundColor(lightVibrantColor);
                t4.setBackgroundColor(lightMutedColor);
                t5.setBackgroundColor(mutedColor);
                t6.setBackgroundColor(vibrantColor);
            }
        });
    }

}
