package com.lichao.tablayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    MyAdatper myAdatper;

    private final String[] titels = { "分类", "主页", "热门推荐", "热门收藏", "本月热榜", "今日热榜", "专栏", "随机" };
    private int[] drawbles={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toolbar.setTitle("天天向上");
        //顺序非常重要  设置 文字 样式调用setSupportActionBar之前 设置监听在setSupportActionBar之后
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        myAdatper = new MyAdatper(getSupportFragmentManager());
        viewPager.setAdapter(myAdatper);
        //设置联动
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                colorChage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        colorChage(0);
    }

    /**
     * 如果页面 发生切换  根据BItmap改变toolBar的颜色
     * @param position
     */
    private void colorChage(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawbles[position]);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //拿到鲜艳的颜色
                Palette.Swatch vibreant = palette.getVibrantSwatch();
                if (vibreant == null) {
                    for (Palette.Swatch swatch : palette.getSwatches()) {
                        vibreant=swatch;
                        break;
                    }
                }

                viewPager.setBackgroundColor(vibreant.getRgb());
                tabLayout.setSelectedTabIndicatorColor(vibreant.getRgb());
                tabLayout.setBackgroundColor(vibreant.getRgb());
                toolbar.setBackgroundColor(vibreant.getRgb());
                if (Build.VERSION.SDK_INT > 21) {
                    Window window=getWindow();
                    //状态栏 颜色加深
                    window.setStatusBarColor(colorBurn(vibreant.getRgb()));
                    window.setNavigationBarColor(vibreant.getRgb());
                }
            }
        });
    }

    private int colorBurn(int rgb) {
        //加深颜色
        int red=rgb>>16&0xFF;
        int gree=rgb>>8&0xFF;
        int blue=rgb&0xFF;

        red = (int) Math.floor(red * (1 - 0.2));
        gree = (int) Math.floor(gree * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, gree, blue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class MyAdatper extends FragmentPagerAdapter {

        public MyAdatper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ImageFrament();
            Bundle bundle = new Bundle();
            bundle.putInt("id", drawbles[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return drawbles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titels[position];
        }
    }
}
