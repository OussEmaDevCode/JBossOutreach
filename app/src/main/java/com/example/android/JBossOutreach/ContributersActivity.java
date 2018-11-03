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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContributersActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Contributer>> {

    private static final String LOG_TAG = ContributersActivity.class.getName();

    /** URL for Contributers data from GITHUB dataset */
    private String GITHUB_REQUEST_URL ;

    /**
     * Constant value for the contributers loader ID.
     */
    private static final int Contributer_LOADER_ID = 2;

    /** Adapter for the list of contributers */
    private ContributerAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    /** View that is displayed when the activity is loading */
    private View loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributers);
        //getting the URL from the intent
        Intent i = getIntent();
        String repName = i.getStringExtra("ContributersUrl");
        //building the request url
        String url = "https://api.github.com/repos/JBossOutreach/" + repName;
        url = url + "/contributors";
        GITHUB_REQUEST_URL = url;
        //setting the title of the activity to be the rep name
        setTitle(repName);
        // Find a reference to the {@link ListView} in the layout
        ListView ContributersListView = (ListView) findViewById(R.id.list);
        // Find a reference to the emptyStateTextView in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        //Setting up the viw displayed when the list is empty
        ContributersListView.setEmptyView(mEmptyStateTextView);
        // Find a reference to the loading indicator in the  layout
        loadingIndicator = findViewById(R.id.loading_indicator);
        // Create a new adapter that takes an empty list of Contributers as input
        mAdapter = new ContributerAdapter(this, new ArrayList<Contributer>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        ContributersListView.setAdapter(mAdapter);
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
            loaderManager.initLoader(Contributer_LOADER_ID, null, this);

        } else {
            // First, hide loading indicator so error message will be visible

            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

    }

    @Override
    public Loader<List<Contributer>> onCreateLoader(int i, Bundle bundle) {
       //creates a uri form a URL
        Uri baseUri = Uri.parse(GITHUB_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        //returns the loader with the new URL in it
        return new ContribLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Contributer>> loader, List<Contributer> Contributers) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No Contributers found."
        mEmptyStateTextView.setText(R.string.no_contrib);

        // Clear the adapter of previous Contributers data
        //mAdapter.clear();
        if(!mAdapter.isEmpty()) {
            mAdapter.clear();
        }

        // If there is a valid list of {@link Contributer}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (Contributers != null && !Contributers.isEmpty()) {
            mAdapter.addAll(Contributers);

        }
    }


    @Override
    public void onLoaderReset(Loader<List<Contributer>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}