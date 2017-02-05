package com.plugapp.container;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nakyl on 02/02/2017.
 */

public class Status {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
