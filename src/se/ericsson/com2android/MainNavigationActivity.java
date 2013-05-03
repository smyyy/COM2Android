package se.ericsson.com2android;
//
//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//
//public class MainNavigationActivity extends Activity {
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		final ActionBar actionBar = getActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//		Tab tabA = actionBar.newTab();
//		tabA.setText("CLI");
//		tabA.setTabListener(new TabListener<CliFragment>(this, "Tag A", CliFragment.class));
//		actionBar.addTab(tabA);
//
//		Tab tabB = actionBar.newTab();
//		tabB.setText("NC");
//		tabB.setTabListener(new TabListener<NcFragment>(this, "Tag B", NcFragment.class));
//		actionBar.addTab(tabB);
//
//		Tab tabC = actionBar.newTab();
//		tabC.setText("HUB");
//		tabC.setTabListener(new TabListener<HubFragment>(this, "Tag C", HubFragment.class));
//		actionBar.addTab(tabC);
//
//		if (savedInstanceState != null) {
//			int savedIndex = savedInstanceState.getInt("SAVED_INDEX");
//			getActionBar().setSelectedNavigationItem(savedIndex);
//		}
//
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		outState.putInt("SAVED_INDEX", getActionBar().getSelectedNavigationIndex());
//	}
//
//	public static class TabListener<T extends Fragment> 
//	implements ActionBar.TabListener{
//
//		private final Activity myActivity;
//		private final String myTag;
//		private final Class<T> myClass;
//
//		public TabListener(Activity activity, String tag, Class<T> cls) {
//			myActivity = activity;
//			myTag = tag;
//			myClass = cls;
//		}
//
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//
//			Fragment myFragment = myActivity.getFragmentManager().findFragmentByTag(myTag);
//
//			// Check if the fragment is already initialized
//			if (myFragment == null) {
//				// If not, instantiate and add it to the activity
//				myFragment = Fragment.instantiate(myActivity, myClass.getName());
//				ft.add(android.R.id.content, myFragment, myTag);
//			} else {
//				// If it exists, simply attach it in order to show it
//				ft.attach(myFragment);
//			}
//
//		}
//
//		@Override
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//
//			Fragment myFragment = myActivity.getFragmentManager().findFragmentByTag(myTag);
//
//			if (myFragment != null) {
//				// Detach the fragment, because another one is being attached
//				ft.detach(myFragment);
//			}
//
//		}
//
//		@Override
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//			// TODO Auto-generated method stub
//
//		}
//
//
//	}
//}

import java.util.ArrayList;

import com.example.com2android.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class MainNavigationActivity extends FragmentActivity {

	FragmentTransaction transaction;
	static public ViewPager mViewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		Fragment tabCliFragment = new CliFragment();
		Fragment tabNcFragment = new NcFragment();
		Fragment tabHubFragment = new HubFragment();

		PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mPagerAdapter.addFragment(tabCliFragment);
		mPagerAdapter.addFragment(tabNcFragment);
		mPagerAdapter.addFragment(tabHubFragment);

		//transaction = getSupportFragmentManager().beginTransaction();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setCurrentItem(0);

		mViewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
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

//	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
//		private Fragment mFragment;
//		private final Activity mActivity;
//		private final String mTag;
//		private final Class<T> mClass;
//
//		/** Constructor used each time a new tab is created.
//		 * @param activity  The host Activity, used to instantiate the fragment
//		 * @param tag  The identifier tag for the fragment
//		 * @param clz  The fragment's Class, used to instantiate the fragment
//		 */
//		public TabListener(Activity activity, String tag, Class<T> clz) {
//			mActivity = activity;
//			mTag = tag;
//			mClass = clz;
//		}
//
//		/* The following are each of the ActionBar.TabListener callbacks */
//
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//			// Check if the fragment is already initialized
//			if (mFragment == null) {
//				// If not, instantiate and add it to the activity
//				mFragment = Fragment.instantiate(mActivity, mClass.getName());
//				ft.add(android.R.id.content, mFragment, mTag);
//			} else {
//				// If it exists, simply attach it in order to show it
//				ft.attach(mFragment);
//			}
//		}
//
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//			if (mFragment != null) {
//				// Detach the fragment, because another one is being attached
//				ft.detach(mFragment);
//			}
//		}
//
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//			// User selected the already selected tab. Usually do nothing.
//		}
//
//		public void onTabReselected(Tab arg0,
//				android.app.FragmentTransaction arg1)
//		{
//			// TODO Auto-generated method stub
//
//		}
//
		public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1)
		{
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(arg0.getPosition());
		}
//
//		public void onTabUnselected(Tab arg0,
//				android.app.FragmentTransaction arg1)
//		{
//			// TODO Auto-generated method stub
//
//		}
//	}

	public class PagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

		public PagerAdapter(FragmentManager manager) {
			super(manager);
		}

		public void addFragment(Fragment fragment) {
			mFragments.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}
	}

}



