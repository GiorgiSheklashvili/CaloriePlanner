package home.gio.calorieplanner.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.presenter.ProductDetailsPagerAdapter;


public class GroceriesListFragment extends AppCompatActivity {
    ProductDetailsPagerAdapter productDetailsPagerAdapter;
    ViewPager mViewpager;
    TextView day;
    View slide;
    public GroceriesListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_groceries_list);
        day=(TextView) findViewById(R.id.day1);
        slide=findViewById(R.id.activity_slide);
        productDetailsPagerAdapter = new ProductDetailsPagerAdapter(getSupportFragmentManager());
        mViewpager = (ViewPager) findViewById(R.id.pager);
        mViewpager.setAdapter(productDetailsPagerAdapter);
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


