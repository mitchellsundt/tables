/*
 * Copyright (C) 2014 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opendatakit.tables.fragments;

import org.opendatakit.logging.WebLogger;
import org.opendatakit.tables.R;

import android.os.Bundle;
import org.opendatakit.tables.views.webkits.OdkTablesWebView;

/**
 * The list view that is displayed in a map.
 * 
 * @author Chris Gelon
 * @author sudar.sam@gmail.com
 *
 */
public class MapListViewFragment extends ListViewFragment implements IMapListViewCallbacks {

  private static final String TAG = MapListViewFragment.class.getSimpleName();

  /**
   * Saves the index of the element that was selected.
   */
  private static final String INTENT_KEY_SELECTED_INDEX = "keySelectedIndex";

  /**
   * The index of an item that has been selected by the user.
   */
  protected int mSelectedItemIndex;
  public static final int INVALID_INDEX = -1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // AppName may not be available...
    if ( savedInstanceState != null ) {
      this.mSelectedItemIndex = savedInstanceState.containsKey(INTENT_KEY_SELECTED_INDEX) ?
          savedInstanceState.getInt(INTENT_KEY_SELECTED_INDEX) : -1;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(INTENT_KEY_SELECTED_INDEX, mSelectedItemIndex);
  }

  /**
   * Resets the webview (the list), and sets the visibility to visible.
   */
  void resetView() {
    WebLogger.getLogger(getAppName()).d(TAG, "[resetView]");

    OdkTablesWebView currentView = (OdkTablesWebView) this.getView().findViewById(R.id.webkit);
    // Just reload the page.
    currentView.reloadPage();
  }

  /**
   *
   * @return true if the user has selected a row that should be displayed as
   *         selected
   */
  protected boolean itemIsSelected() {
    return this.mSelectedItemIndex != INVALID_INDEX;
  }

  @Override
  public void onResume() {
    super.onResume();
    WebLogger.getLogger(getAppName()).d(TAG, "[onResume]");
  }

  /**
   * Sets the index of the list view, which will be the row of the data wanting
   * to be displayed.
   */
  @Override
  public void setIndexOfSelectedItem(final int index) {
    this.mSelectedItemIndex = index;
    // TODO: Make map index work with async API
    //this.mTableDataReference.setSelectedMapIndex(index);
    this.resetView();
  }

  /**
   * Informs the list view that no item is selected. Resets the state after a
   * call to {@link #setIndexOfSelectedItem(int)}.
   */
  @Override
  public void setNoItemSelected() {
    this.mSelectedItemIndex = INVALID_INDEX;
    // TODO: Make map index work with async API
    //this.mTableDataReference.setNoItemSelected();
    this.resetView();
  }

  public int getIndexOfSelectedItem() {
    return this.mSelectedItemIndex;
  }

}
