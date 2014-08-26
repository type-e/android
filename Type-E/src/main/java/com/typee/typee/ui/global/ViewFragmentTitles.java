package com.typee.typee.ui.global;

/**
 * Created by winsonlim on 28/4/14.
 */

public enum ViewFragmentTitles {
	CURRENT("CONFIRMED"),
	FUTURE("PENDING");
//	PAST("PAST");

	private String title;
	private final static ViewFragmentTitles[] values = ViewFragmentTitles.values();

	private ViewFragmentTitles(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}

	/**
	 * Get the title of the View Page
	 *
	 * @param position Order of the page
	 *
	 * @return Title of the View Page
	 */
	public static String getTitle(int position) {
		switch (values[position]) {
			case CURRENT:
				return CURRENT.toString();
			case FUTURE:
				return FUTURE.toString();
//			case PAST:
//				return PAST.toString();
		}

		return null;
	}
}