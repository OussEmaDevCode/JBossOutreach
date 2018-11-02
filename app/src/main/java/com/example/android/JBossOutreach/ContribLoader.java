package com.example.android.JBossOutreach;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of reps by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class ContribLoader extends AsyncTaskLoader<List<Contributer>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ContribLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link ContribLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public ContribLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Contributer> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of Contributers.
        List<Contributer> Contributers = QueryUtilsContributers.fetchContData(mUrl);
        return Contributers;
    }
}