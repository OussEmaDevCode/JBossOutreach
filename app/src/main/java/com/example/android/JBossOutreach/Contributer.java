package com.example.android.JBossOutreach;

public class Contributer {
    private String mLogin;
    private int mContribs;
    /**
     * Constructs a new {@link Rep} object.
     *
     * @param Login is the name of the Contributer
     * @param contrib are the contributions of the Contributer
     **/
    public Contributer(String Login, int contrib) {
        mLogin = Login;
        mContribs = contrib;
        
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
}

