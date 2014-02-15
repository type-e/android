package com.typee.typee.ui.base;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.typee.typee.R;

/**
 * BaseFragment class extends from Fragment class
 * purpose of this class is to have common features here
 * so all fragments can use it by extending this class
 * <p/>
 * Every Fragment class must be extended from this class
 */
public class BaseFragment extends Fragment {
	public final String TAG = this.getClass().getSimpleName();

	private boolean isDisplayHomeAsUpEnabled = false;

	private View mProgressContainer;
	private View mContentContainer;
	private View mContentView;
	private View mEmptyView;
	private boolean mContentShown;
	private boolean mIsContentEmpty;


	public BaseFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Default loading ProgressBar template used is 'template_loading_bar'
		// We can modify it according to our usage
		return inflater.inflate(R.layout.template_loading_bar, container, false);
	}

	/**
	 * Attach to view once the view hierarchy has been created.
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		ensureContent();
	}

	/**
	 * Detach from view.
	 */
	@Override
	public void onDestroyView() {
		mContentShown = false;
		mIsContentEmpty = false;
		mProgressContainer = mContentContainer = mContentView = mEmptyView = null;
		super.onDestroyView();
	}

	/**
	 * Enable the auto hiding of soft keyboard when background is touched
	 *
	 * @param view The root view of the activity
	 */
	public void enableAutoHideKeyboard(View view) {
		//Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard();
					return false;
				}
			});
		}

		//If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);

				enableAutoHideKeyboard(innerView);
			}
		}
	}

	private void hideSoftKeyboard() {
		if (getActivity() == null) return;

		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	}

	/**
	 * Set title of the actionbar
	 *
	 * @param title Title of the action bar
	 */
	public void setTitle(String title) {
		if (getActivity() != null && getActivity().getActionBar() != null) getActivity().getActionBar().setTitle(title);
	}

	/**
	 * Finish the current activity
	 */
	public void finish() {
		if (getActivity() != null) getActivity().finish();
	}

	/**
	 * Get bundle from activity's intent
	 *
	 * @return bundle of activity's intent
	 */
	public Bundle getExtras() {
		if (getActivity() != null && getActivity().getIntent() != null) return getActivity().getIntent().getExtras();

		return null;
	}

	/**
	 * Set Home on action bar to be 'up'
	 *
	 * @param setAsEnabled True to set home button as up action; Else false
	 */
	public void setDisplayHomeAsUpEnabled(boolean setAsEnabled) {
		this.isDisplayHomeAsUpEnabled = setAsEnabled;

		if (getActivity() != null) {
			ActionBar actionBar = getActivity().getActionBar();
			if (actionBar != null) {
				actionBar.setDisplayHomeAsUpEnabled(setAsEnabled);
				if (setAsEnabled) actionBar.setTitle("");
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (this.isDisplayHomeAsUpEnabled) {
					finish();
					return true;
				}
		}

		return super.onOptionsItemSelected(item);
	}

	/************************************************************************
	 *                                                                      *
	 *                         Universal Loading Bar                        *
	 *                                                                      *
	 ***********************************************************************/


	/**
	 * Return content view or null if the content view has not been initialized.
	 *
	 * @return content view or null
	 * @see #setContentView(android.view.View)
	 * @see #setContentView(int)
	 */
	public View getContentView() {
		return mContentView;
	}

	/**
	 * Set the content view to an explicit view. If the content view was installed earlier,
	 * the content will be replaced with a new view.
	 *
	 * @param view The desired content to display. Value can't be null.
	 * @see #setContentView(int)
	 * @see #getContentView()
	 */
	public void setContentView(View view) {
		ensureContent();
		if (view == null) {
			throw new IllegalArgumentException("Content view can't be null");
		}
		if (mContentContainer instanceof ViewGroup) {
			ViewGroup contentContainer = (ViewGroup) mContentContainer;
			if (mContentView == null) {
				contentContainer.addView(view);
			} else {
				int index = contentContainer.indexOfChild(mContentView);
				// replace content view
				contentContainer.removeView(mContentView);
				contentContainer.addView(view, index);
			}
			mContentView = view;
		} else {
			throw new IllegalStateException("Can't be used with a custom content view");
		}
	}

	/**
	 * Set the content content from a layout resource.
	 *
	 * @param layoutResId Resource ID to be inflated.
	 * @see #setContentView(android.view.View)
	 * @see #getContentView()
	 */
	private void setContentView(int layoutResId) {
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View contentView = layoutInflater.inflate(layoutResId, null);
		setContentView(contentView);
	}

	/**
	 * The default content for a ProgressFragment has a TextView that can be shown when
	 * the content is empty {@link #setContentEmpty(boolean)}.
	 * If you would like to have it shown, call this method to supply the text it should use.
	 *
	 * @param resId Identification of string from a resources
	 * @see #setEmptyText(CharSequence)
	 */
	public void setEmptyText(int resId) {
		setEmptyText(getString(resId));
	}

	/**
	 * The default content for a ProgressFragment has a TextView that can be shown when
	 * the content is empty {@link #setContentEmpty(boolean)}.
	 * If you would like to have it shown, call this method to supply the text it should use.
	 *
	 * @param text Text for empty view
	 * @see #setEmptyText(int)
	 */
	public void setEmptyText(CharSequence text) {
		ensureContent();
		if (mEmptyView != null && mEmptyView instanceof TextView) {
			((TextView) mEmptyView).setText(text);
		} else {
			throw new IllegalStateException("Can't be used with a custom content view");
		}
	}

	/**
	 * To show loading progress bar
	 */
	public void showLoadingIndicator() {
		setContentShown(false);
	}

	/**
	 * To hide loading progress bar
	 */
	public void hideLoadingIndicator() {
		setContentEmpty(false);
		setContentShown(true);
	}

	/**
	 * To show loading progress bar, with no fading animation
	 */
	public void showLoadingIndicatorNoAnimation() {
		setContentShownNoAnimation(false);
	}

	/**
	 * To hide loading progress bar, with no fading animation
	 */
	public void hideLoadingIndicatorNoAnimation() {
		setContentEmpty(false);
		setContentShownNoAnimation(true);
	}

	/**
	 * To show empty content message
	 */
	public void showEmptyText() {
		setContentEmpty(true);
		setContentShownNoAnimation(true);
	}

	/**
	 * To hide empty content message
	 */
	public void hideEmptyText() {
		setContentEmpty(false);
	}

	/**
	 * Control whether the content is being displayed.  You can make it not
	 * displayed if you are waiting for the initial data to show in it.  During
	 * this time an indeterminant progress indicator will be shown instead.
	 *
	 * @param shown If true, the content view is shown; if false, the progress
	 *              indicator. The initial value is true.
	 * @see #setContentShownNoAnimation(boolean)
	 */
	private void setContentShown(boolean shown) {
		setContentShown(shown, true);
	}

	/**
	 * Like {@link #setContentShown(boolean)}, but no animation is used when
	 * transitioning from the previous state.
	 *
	 * @param shown If true, the content view is shown; if false, the progress
	 *              indicator. The initial value is true.
	 * @see #setContentShown(boolean)
	 */
	private void setContentShownNoAnimation(boolean shown) {
		setContentShown(shown, false);
	}

	/**
	 * Control whether the content is being displayed.  You can make it not
	 * displayed if you are waiting for the initial data to show in it.  During
	 * this time an indeterminant progress indicator will be shown instead.
	 *
	 * @param shown   If true, the content view is shown; if false, the progress
	 *                indicator.  The initial value is true.
	 * @param animate If true, an animation will be used to transition to the
	 *                new state.
	 */
	private void setContentShown(boolean shown, boolean animate) {
		try {
			ensureContent();
		} catch (IllegalStateException e) {
			return;
		}

		if (mContentShown == shown) {
			return;
		}
		mContentShown = shown;
		if (shown) {
			if (animate) {
				mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
				mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
			} else {
				mProgressContainer.clearAnimation();
				mContentContainer.clearAnimation();
			}
			mProgressContainer.setVisibility(View.GONE);
			mContentContainer.setVisibility(View.VISIBLE);
			mContentContainer.requestLayout();
		} else {
			if (animate) {
				mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
				mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
			} else {
				mProgressContainer.clearAnimation();
				mContentContainer.clearAnimation();
			}
			mProgressContainer.setVisibility(View.VISIBLE);
			mContentContainer.setVisibility(View.GONE);
		}
	}

	/**
	 * Returns true if content is empty. The default content is not empty.
	 *
	 * @return true if content is null or empty
	 * @see #setContentEmpty(boolean)
	 */
	public boolean isContentEmpty() {
		return mIsContentEmpty;
	}

	/**
	 * If the content is empty, then set true otherwise false. The default content is not empty.
	 * You can't call this method if the content view has not been initialized before
	 * {@link #setContentView(android.view.View)} and content view not null.
	 *
	 * @param isEmpty true if content is empty else false
	 * @see #isContentEmpty()
	 */
	private void setContentEmpty(boolean isEmpty) {
		try {
			ensureContent();
		} catch (IllegalStateException e) {
			return;
		}
		if (mContentView == null) {
			return;
//			throw new IllegalStateException("Content view must be initialized before");
		}
		if (isEmpty) {
			mEmptyView.setVisibility(View.VISIBLE);
			mContentView.setVisibility(View.GONE);
		} else {
			mEmptyView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
		}
		mIsContentEmpty = isEmpty;
	}

	/**
	 * Initialization views.
	 */
	private void ensureContent() {
		if (mContentContainer != null && mProgressContainer != null) {
			return;
		}

		if (getActivity() == null) {
			throw new NullPointerException("Activity has been finished");
		}

		View root = getView();
		if (root == null) {
			throw new IllegalStateException("Content view not yet created");
		}
		mProgressContainer = root.findViewById(R.id.progress_container);
		if (mProgressContainer == null) {
			throw new RuntimeException("Your content must have a ViewGroup whose id attribute is 'R.id.progress_container'. Tip: return inflater.inflate(R.layout.template_loading_bar, container, false);");
		}
		mContentContainer = root.findViewById(R.id.content_container);
		if (mContentContainer == null) {
			throw new RuntimeException("Your content must have a ViewGroup whose id attribute is 'R.id.content_container'. Tip: return inflater.inflate(R.layout.template_loading_bar, container, false);");
		}
		mEmptyView = root.findViewById(android.R.id.empty);
		if (mEmptyView != null) {
			mEmptyView.setVisibility(View.GONE);
		}
		mContentShown = true;
		// We are starting without a content, so assume we won't
		// have our data right away and start with the progress indicator.
		if (mContentView == null) {
			setContentShown(false, false);
		}
	}
}
