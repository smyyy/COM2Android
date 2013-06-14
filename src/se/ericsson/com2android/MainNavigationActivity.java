package se.ericsson.com2android;

import java.util.ArrayList;

import com.example.com2android.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainNavigationActivity extends FragmentActivity {

	FragmentTransaction transaction;
	static public ViewPager viewPager;

	boolean cliTab, ncTab,terminalTab;
	
	Fragment tabCliFragment,tabNcFragment, tabHubFragment ;
	PagerAdapter pageAdapter;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		cliTab = getIntent().getBooleanExtra("cli_checkbox", true);
		ncTab = getIntent().getBooleanExtra("nc_checkbox", true);
		terminalTab = getIntent().getBooleanExtra("terminal_checkbox", false);

		pageAdapter = new PagerAdapter(getSupportFragmentManager());

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

		generatActionBar();
	}


	private void generatActionBar(){
		
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayUseLogoEnabled(true);
		ab.setLogo(R.drawable.ericssonwhitesmall);

		if (cliTab){
			tabCliFragment = new CliFragment();
		
			Tab cli = ab.newTab().setText("CLI")
					.setTabListener(new TabListener<CliFragment>(this, "CLI", CliFragment.class));
			ab.addTab(cli);
			pageAdapter.addFragment(tabCliFragment);
		}
		
		if (ncTab){
			tabNcFragment = new CliFragment();
			Tab tab2 = ab.newTab().setText("NC")
					.setTabListener(new TabListener<CliFragment>(this, "NC", CliFragment.class));
			ab.addTab(tab2);
			pageAdapter.addFragment(tabNcFragment);
		}
		if (terminalTab){
			tabHubFragment = new HubFragment();
			Tab tab3 = ab.newTab().setText("HUB")
					.setTabListener(new TabListener<HubFragment>(this, "Hub", HubFragment.class));
			ab.addTab(tab3);
			pageAdapter.addFragment(tabHubFragment);
		}
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



