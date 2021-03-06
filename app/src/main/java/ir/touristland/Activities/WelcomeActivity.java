package ir.touristland.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.R;


public class WelcomeActivity extends Activity {

    public static Activity ctx;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private TextView btnSkip, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == getIntent().getExtras() && Application.preferences.getString("IsFirstRun", "").equals("true")) {
            launchHomeScreen();
            finish();
        }

        setContentView(R.layout.activity_welcome);

        ctx = WelcomeActivity.this;
        viewPager = findViewById(R.id.view_pager);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);
        dotsLayout = findViewById(R.id.layout_dots);

        btnNext.setVisibility(View.VISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);

        try {
            if (getIntent().getExtras().getString("advantage").equals("advantage")) {
                btnNext.setVisibility(View.GONE);
                btnSkip.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }

        viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (null == getIntent().getExtras()) {
                    if (position == 0) {
                        btnNext.setVisibility(View.VISIBLE);
                        btnSkip.setVisibility(View.INVISIBLE);
                        btnNext.setText(getString(R.string.next));
                    } else if (position == 2) {
                        btnNext.setVisibility(View.VISIBLE);
                        btnNext.setText(getString(R.string.start));
                        btnSkip.setVisibility(View.VISIBLE);
                        btnSkip.setText(getString(R.string.skip));

                    } else {
                        btnNext.setText(getString(R.string.next));
                        btnNext.setVisibility(View.VISIBLE);
                        btnSkip.setVisibility(View.VISIBLE);
                        btnSkip.setText(getString(R.string.skip));
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };

        layouts = new int[]{
                R.layout.welcome_slide0,
                R.layout.welcome_slide1,
                R.layout.welcome_slide2
        };

        // adding bottom dots
        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setCurrentItem(0);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(-1);
                viewPager.setCurrentItem(current);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < 3; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        HSH.editor("IsFirstRun", "true");
        startActivity(new Intent(WelcomeActivity.this, IntroLoginActivity.class));
        finish();
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
