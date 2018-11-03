package com.example.android.JBossOutreach;

public class Contributer {
    private String mLogin;
    private int mContribs;
    private String mURL;
    /**
     * Constructs a new {@link Rep} object.
     *
     * @param Login is the name of the Contributer
     * @param contrib are the contributions of the Contributer
     * @param URL is the icon of the Contributer
     **/
    public Contributer(String Login, int contrib, String URL) {
        mLogin = Login;
        mContribs = contrib;
        mURL = URL;
        
    }

    /**
     * Returns the name of Contributer
     */

    public String getLogin(){
        return mLogin;
    }
    /**
     * Returns the contributions of the Contributer
     */
    public int getContribs(){
        return mContribs;
    }
    /**
     * Returns the icon url of the Contributer
     */
    public String getURL(){
        return mURL;
    }

}

