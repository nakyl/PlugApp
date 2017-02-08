package com.plugapp.container;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nakyl on 02/02/2017.
 */

public class Switch {
    @SerializedName("statusPlug")
    private String statusPlug;

    public String getStatusPlug() {
        return statusPlug;
    }

    public void setStatusPlug(String statusPlug) {
        this.statusPlug = statusPlug;
    }
}
