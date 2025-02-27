/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package abysmel.com.collapsibletoolbarhelper.helpers;

import android.os.Build;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.view.ViewParent;

/**
 * Utility helper for moving a {@link View} around using {@link View#offsetLeftAndRight(int)} and
 * {@link View#offsetTopAndBottom(int)}.
 * <p>
 * Also the setting of absolute offsets (similar to translationX/Y), rather than additive offsets.
 */
public class ViewOffsetHelper {

	private final View	mView;

	private int			mLayoutTop;
	private int			mLayoutLeft;
	private int			mOffsetTop;
	private int			mOffsetLeft;

	public ViewOffsetHelper(View view) {
		mView = view;
	}

	private static void tickleInvalidationFlag(View view) {
		final float x = ViewCompat.getTranslationX(view);
		ViewCompat.setTranslationY(view, x + 1);
		ViewCompat.setTranslationY(view, x);
	}

	public void onViewLayout() {
		// Now grab the intended top
		mLayoutTop = mView.getTop();
		mLayoutLeft = mView.getLeft();

		// And offset it as needed
		updateOffsets();
	}

	private void updateOffsets() {
		ViewCompat.offsetTopAndBottom(mView, mOffsetTop - (mView.getTop() - mLayoutTop));
		ViewCompat.offsetLeftAndRight(mView, mOffsetLeft - (mView.getLeft() - mLayoutLeft));

		// Manually invalidate the view and parent to make sure we get drawn pre-M
		if (Build.VERSION.SDK_INT < 23) {
			tickleInvalidationFlag(mView);
			final ViewParent vp = mView.getParent();
			if (vp instanceof View) {
				tickleInvalidationFlag((View) vp);
			}
		}
	}

	/**
	 * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view.
	 *
	 * @param offset
	 *            the offset in px.
	 * @return true if the offset has changed
	 */
	public boolean setTopAndBottomOffset(int offset) {
		if (mOffsetTop != offset) {
			mOffsetTop = offset;
			updateOffsets();
			return true;
		}
		return false;
	}

	/**
	 * Set the left and right offset for this {@link ViewOffsetHelper}'s view.
	 *
	 * @param offset
	 *            the offset in px.
	 * @return true if the offset has changed
	 */
	public boolean setLeftAndRightOffset(int offset) {
		if (mOffsetLeft != offset) {
			mOffsetLeft = offset;
			updateOffsets();
			return true;
		}
		return false;
	}

	public int getTopAndBottomOffset() {
		return mOffsetTop;
	}

	public int getLeftAndRightOffset() {
		return mOffsetLeft;
	}
}
