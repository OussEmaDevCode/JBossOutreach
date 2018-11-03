/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.example.android.JBossOutreach;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RepActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Rep>> {

    private static final String LOG_TAG = RepActivity.class.getName();

    /** URL for REPS data from GITHUB dataset */
    private static final String GITHUB_REQUEST_URL =
            "https://api.github.com/orgs/JBossOutreach/repos";

    /**
     * Constant value for the REP loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int REP_LOADER_ID = 1;

    /** Adapter for the list of reps */
    private RepAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    /** View that is displayed when the activity is loading */
    private View loadingIndicator;
    /** Animation that is displayed when the user clicks the menu item button */
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_activity);
        //setting up the animation
        animation = new AlphaAnimation(0.2f,1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(5000);
        animation.setFillAfter(true);
        // Find a reference to the {@link ListView} in the layout
        ListView RepsListView = (ListView) findViewById(R.id.list);
        // Find a reference to the emptyStateTextView in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        //Setting up the viw displayed when the list is empty
        RepsListView.setEmptyView(mEmptyStateTextView);
        // Find a reference to the loading indicator in the  layout
        loadingIndicator = findViewById(R.id.loading_indicator);
        // Create a new adapter that takes an empty list of reps as input
        mAdapter = new RepAdapter(this, new ArrayList<Rep>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        RepsListView.setAdapter(mAdapter);




        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected rep.
        RepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current rep that was clicked on
                Rep currentRep = mAdapter.getItem(position);
                // Create a new intent to the next activity
               Intent i = new Intent(RepActivity.this, ContributersActivity.class);
               i.putExtra("ContributersUrl",currentRep.getName());

                // Send the intent to launch a new activity
                RepActivity.this.startActivity(i);
            }
        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(REP_LOADER_ID, null, this);

        } else {
            // First, hide loading indicator so error message will be visible

            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

    }

    @Override
    public Loader<List<Rep>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(GITHUB_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new RepLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Rep>> loader, List<Rep> reps) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No reps found."
        mEmptyStateTextView.setText(R.string.no_reps);

        // Clear the adapter of previous rep data
        //mAdapter.clear();
        if(!mAdapter.isEmpty()) {
            mAdapter.clear();
        }

        // If there is a valid list of {@link Rep}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (reps != null && !reps.isEmpty()) {
            mAdapter.addAll(reps);

        }
    }


    @Override
    public void onLoaderReset(Loader<List<Rep>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu with our new button
        getMenuInflater().inflate(R.menu.main, menu);
        //Find reference to the button from the menu
        final Button locButton = (Button) menu.findItem(R.id.action_refresh).getActionView();
        //starting animation when button clicked
        if(locButton != null){
            locButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    locButton.startAnimation(animation);
                }
            });
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //wait for the animation to end
        //Refresh the listview with new data
        if (id == R.id.action_refresh) {
            Runnable r = new Runnable() {
                @Override
                public void run(){
                    recreate();
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 500);
        }
        return super.onOptionsItemSelected(item);
    }

}
