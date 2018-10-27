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

/**
 * An {@link Rep} object contains information related to a single earthquake.
 */
public class Rep {

    /** Magnitude of the earthquake */
    private String mName;
    private String mDesc;
    /** Website URL of the earthquake */
    private String mUrl;

    /**
     * Constructs a new {@link Rep} object.
     *
     * @param name is the magnitude (size) of the earthquake
     * @param desc is the location where the earthquake happened
     * @param  url is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url is the website URL to find more details about the earthquake
     */
    public Rep(String name, String desc, String url) {
        mName = name;
        mDesc = desc;
        mUrl = url;
    }

    /**
     * Returns the magnitude of the earthquake.
     */

    /**
     * Returns the location of the earthquake.
     */

    public String getName(){
        return mName;
    }
    /**
     * Returns the time of the earthquake.
     */
    public String getDesc(){
        return mDesc;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }
}
