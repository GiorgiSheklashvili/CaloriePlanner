package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesViewPagerAdapter;


public class GroceriesViewpagerFragment extends AppCompatActivity {
    GroceriesViewPagerAdapter groceriesViewPagerAdapter;
    ViewPager mViewpager;
    TextView day;
    View slide;
    public GroceriesViewpagerFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_groceries_viewpager);
        day=(TextView) findViewById(R.id.day1);
        slide=findViewById(R.id.activity_slide);
        groceriesViewPagerAdapter = new GroceriesViewPagerAdapter(getSupportFragmentManager());
        mViewpager = (ViewPager) findViewById(R.id.pager);
        mViewpager.setAdapter(groceriesViewPagerAdapter);
        day.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params=slide.getLayoutParams();
                params.width=day.getWidth();
                params.height=day.getHeight();
                slide.setLayoutParams(params);
                day.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                slide.animate().x(position*slide.getWidth());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}


