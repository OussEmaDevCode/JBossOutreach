package com.example.android.JBossOutreach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * An {@link ContributerAdapter} knows how to create a list item layout for each contributer
 * in the data source (a list of {@link Contributer} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class ContributerAdapter extends ArrayAdapter<Contributer> {
    /**
     * Constructs a new {@link ContributerAdapter}.
     *
     * @param context of the app
     * @param contributers is the list of contrib, which is the data source of the adapter
     */
    public ContributerAdapter(Context context, List<Contributer> contributers) {
        super(context, 0, contributers);
    }

    /**
     * Returns a list item view that displays information about the contributer at the given position
     * in the list of Contributers.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.contrib_list_item, parent, false);
        }

        // Find the Contributer at the given position in the list of Contributers
        Contributer currentContributer = getItem(position);

        // Find the TextView with view name
        TextView title = (TextView) listItemView.findViewById(R.id.name);
        //set the text to be the Contributer name
        title.setText(currentContributer.getLogin());
        //Find the text view with view description
        TextView description = (TextView) listItemView.findViewById(R.id.description);
        //creating contributions text
        String contribs = "contributions: " + Integer.toString(currentContributer.getContribs());
        //adding it to the description view
        description.setText(contribs);
        ImageView icon = (ImageView) listItemView.findViewById(R.id.cont_icon);
        Picasso.get().load(currentContributer.getURL()).into(icon);
        return listItemView;
    }



}
