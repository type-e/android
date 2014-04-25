package com.typee.typee.ui.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.typee.typee.R;
import com.typee.typee.ui.event.EventDetailsFragment;
import com.typee.typee.util.MyPagerSlidingTabStrip;
import com.typee.typee.util.TypefaceHelper;

public class MainActivity extends Activity {
	public String TAG = this.getClass().getSimpleName();

	private MyPagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ViewPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		Bundle bundle = getIntent().getExtras();
//		if (bundle != null && bundle.containsKey(Util.FRAGMENT_CLASS_NAME)) {
//			Fragment fragment = Fragment.instantiate(this, bundle.getString(Util.FRAGMENT_CLASS_NAME));
//
//			if (savedInstanceState == null) {
//				getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
//			}
//		}

		tabs = (MyPagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new ViewPagerAdapter(getFragmentManager());

		pager.setPageMargin((int) getResources().getDimension(R.dimen.viewpager_margin));

		pager.setAdapter(adapter);

		tabs.setViewPager(pager);

		tabs.setShouldExpand(true);

		// Set Tabs Typeface (Font)
		tabs.setTypeface(TypefaceHelper.get(this, TypefaceHelper.ROBOTO_LIGHT), 0);

		// Set Tabs Height
		final int tabsHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.tabs_height), getResources().getDisplayMetrics());
		tabs.setIndicatorHeight(tabsHeight);
		tabs.setUnderlineHeight(0);

		// Set Tabs Color
		tabs.setTextColorResource(R.color.tabs_text_selector);
		tabs.setDividerColorResource(R.color.clear);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {


		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

// todo implement sliding menu here (open / back)


	public class ViewPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = {PageTitle.CURRENT.toString(), PageTitle.FUTURE.toString(), PageTitle.PAST.toString()};

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {

			if (PageTitle.CURRENT.intValue == position) {
//				return new AddEventFragment();

			} else if (PageTitle.FUTURE.intValue == position) {

			} else if (PageTitle.PAST.intValue == position) {

			}

			return new EventDetailsFragment();
		}
	}

	private static enum PageTitle {
		CURRENT("I'm going to...", 0),
		FUTURE("I'm invited to...", 1),
		PAST("I went to...", 2);

		private String stringValue;
		private int intValue;

		private PageTitle(String toString, int value) {
			stringValue = toString;
			intValue = value;
		}

		@Override
		public String toString() {
			return stringValue;
		}
	}
}
