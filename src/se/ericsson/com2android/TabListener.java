package se.ericsson.com2android;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	private Fragment fragment;
	private final Activity activity;
	private final String tag;
	private final Class<T> className;

	public TabListener(Activity activity, String tag, Class<T> clz) {
		this.activity = activity;
		this.tag = tag;
		this.className = clz;
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (fragment == null) {
			fragment = Fragment.instantiate(activity, className.getName());
			ft.add(android.R.id.content, fragment, tag);
		} else {
			ft.attach(fragment);
		}
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			ft.detach(fragment);
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1){

	}

	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1)
	{
		MainNavigationActivity.viewPager.setCurrentItem(arg0.getPosition());
	}

	public void onTabUnselected(Tab arg0,
			android.app.FragmentTransaction arg1){
		// TODO Auto-generated method stub

	}
}