package com.typee.typee.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.typee.typee.R;
import com.typee.typee.service.DataService;
import com.typee.typee.ui.base.BaseActivity;
import com.typee.typee.ui.event.EventDetailsFragment;
import com.typee.typee.ui.global.ViewFragmentTitles;
import com.typee.typee.util.MyPagerSlidingTabStrip;
import com.typee.typee.util.TypefaceHelper;

public class MainActivity extends BaseActivity {
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
	protected void onDestroy() {
		super.onDestroy();

		stopService(new Intent(this, DataService.class));
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

		Fragment[] viewFragments = new Fragment[getCount()];

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);

			for (int i = 0; i < getCount(); i++) {
				viewFragments[i] = new EventDetailsFragment(getPageTitle(i));
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return ViewFragmentTitles.getTitle(position);
		}

		@Override
		public int getCount() {
			return ViewFragmentTitles.values().length;
		}

		@Override
		public Fragment getItem(int position) {
			return viewFragments[position];
		}
	}
}