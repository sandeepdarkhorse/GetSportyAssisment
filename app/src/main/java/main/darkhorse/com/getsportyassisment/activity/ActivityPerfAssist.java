package main.darkhorse.com.getsportyassisment.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.Serializable;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.fragment.FragmentPerAssistment;

public class ActivityPerfAssist
        extends AppCompatActivity implements Serializable {

    String sport;
    String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perf_assist);
        setTitle("Athlete Assessment");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tabview();
    }


    TabLayout tabLayout;
    PagerAdapterCreation mAdapter;

    public void tabview() {

        Bundle b = getIntent().getExtras();

        sport = b.getString("sport");
        usertype = b.getString("usertype");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Incomplete"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapter = new PagerAdapterCreation(ActivityPerfAssist.this, getSupportFragmentManager(), tabLayout.getTabCount(), sport, usertype);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(ActivityPerfAssist.this);
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setText(tab.getText());
                if (i == 0) {
                    //  tabTextView.setTypeface(custom_font, Typeface.BOLD);
                    tabTextView.setTextColor(ContextCompat.getColor(ActivityPerfAssist.this, R.color.colorPrimarytab));
                }
            }
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimarytab));
                tabLayout.setTabTextColors(getResources().getColor(R.color.text_color), getResources().getColor(R.color.colorPrimarytab));
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                // text.setTypeface(custom_font, Typeface.BOLD);
                text.setTextColor(ContextCompat.getColor(ActivityPerfAssist.this, R.color.colorPrimarytab));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(Color.parseColor("#2196f3"), Color.parseColor("#000000"));
                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                //  text.setTypeface(custom_font, Typeface.NORMAL);
                text.setTextColor(ContextCompat.getColor(ActivityPerfAssist.this, R.color.text_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityPerfAssist.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}


class PagerAdapterCreation extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Context context;
    Fragment fragment = null;
    String userId;

    String sport;
    String usertype;
    static FragmentPerAssistment tab1;

    static FragmentPerAssistment tab2;
    static FragmentPerAssistment tab3;

    public PagerAdapterCreation(Context context, FragmentManager fm, int NumOfTabs, String sport, String usertype) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
        this.sport = sport;
        this.usertype = usertype;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                tab1 = new FragmentPerAssistment().newInstance(sport, usertype);
                return tab1;
            case 1:

                tab2 = new FragmentPerAssistment().newInstance("", "");
                return tab2;
            case 2:
                tab3 = new FragmentPerAssistment().newInstance("", "");
                return tab3;
            default:
                return null;
        }


    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}

