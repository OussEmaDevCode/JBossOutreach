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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link RepAdapter} knows how to create a list item layout for each rep
 * in the data source (a list of {@link Rep} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class RepAdapter extends ArrayAdapter<Rep> {
    /**
     * Constructs a new {@link RepAdapter}.
     *
     * @param context of the app
     * @param reps is the list of reps, which is the data source of the adapter
     */
    public RepAdapter(Context context, List<Rep> reps) {
        super(context, 0, reps);
    }

    /**
     * Returns a list item view that displays information about the rep at the given position
     * in the list of reps.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.rep_list_item, parent, false);
        }

        // Find the rep at the given position in the list of reps
        Rep currentRep = getItem(position);

        // Find the TextView with view name
       TextView title = (TextView) listItemView.findViewById(R.id.name);
       //set the text to be the rep name
       title.setText(currentRep.getName());
       //Find the text view with view description
       TextView description = (TextView) listItemView.findViewById(R.id.description);
       //Checking if description is null
       if(currentRep.getDesc().equals("null")){
           //then setting text to No description
           description.setText("No description");
       }
       else {
           //otherwise set text to be the description
           description.setText(currentRep.getDesc());
       }
        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }



}
