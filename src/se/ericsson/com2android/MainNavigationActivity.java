package se.ericsson.com2android;

import java.util.ArrayList;

import com.example.com2android.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

public class MainNavigationActivity extends FragmentActivity {

	FragmentTransaction transaction;
	static public ViewPager viewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		Fragment tabCliFragment = new CliFragment();
		Fragment tabNcFragment = new NcFragment();
		Fragment tabHubFragment = new HubFragment();

		PagerAdapter pageAdapter = new PagerAdapter(getSupportFragmentManager());
		pageAdapter.addFragment(tabCliFragment);
		pageAdapter.addFragment(tabNcFragment);
		pageAdapter.addFragment(tabHubFragment);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(pageAdapter);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
		
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		

		viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab1 = ab.newTab().setText("CLI")
				.setTabListener(new TabListener<CliFragment>(this, "CLI", CliFragment.class));

		Tab tab2 = ab.newTab().setText("NC")
				.setTabListener(new TabListener<NcFragment>(this, "NC", NcFragment.class));

		Tab tab3 = ab.newTab().setText("HUB")
				.setTabListener(new TabListener<HubFragment>(this, "Hub", HubFragment.class));

		ab.addTab(tab1);
		ab.addTab(tab2);
		ab.addTab(tab3);
	}


	public void onTabSelected(Tab tab, android.app.FragmentTransaction arg1)
	{
		viewPager.setCurrentItem(tab.getPosition());
	}

	public class PagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

		public PagerAdapter(FragmentManager manager) {
			super(manager);
		}

		public void addFragment(Fragment fragment) {
			fragmentList.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}
	}

}



