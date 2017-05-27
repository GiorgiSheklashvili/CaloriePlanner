package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesViewPagerAdapter;


public class GroceriesViewpagerFragment extends Fragment {
    ViewPager mViewpager;
    TextView day;
    View slide;

    public GroceriesViewpagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewpager = (ViewPager) view.findViewById(R.id.pager);
        mViewpager.setAdapter(new GroceriesViewPagerAdapter(getChildFragmentManager()));
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                slide.animate().x(position * slide.getWidth());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_groceries_viewpager, container, false);
        day = (TextView) view.findViewById(R.id.day1);
        slide = view.findViewById(R.id.activity_slide);
        day.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = slide.getLayoutParams();
                params.width = day.getWidth();
                params.height = day.getHeight();
                slide.setLayoutParams(params);
                day.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return view;
    }


}


