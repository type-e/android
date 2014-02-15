package com.typee.typee.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.typee.typee.R;
import com.typee.typee.util.Util;

public class BaseActivity extends Activity {
	public String TAG = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(Util.FRAGMENT_CLASS_NAME)) {
			Fragment fragment = Fragment.instantiate(this, bundle.getString(Util.FRAGMENT_CLASS_NAME));

			if (savedInstanceState == null) {
				getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	// todo implement sliding menu here (open / back)
}
