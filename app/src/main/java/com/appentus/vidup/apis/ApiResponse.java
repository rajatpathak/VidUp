package com.appentus.vidup.apis;

import com.appentus.vidup.apis.Models.LoginBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Warlock on 12/14/2017.
 */

public class ApiResponse implements Serializable {
    @SerializedName("results")
    private List<LoginBean> results;
    public List<LoginBean> getResults() {
        return results;
    }

    public void setResults(List<LoginBean> results) {
        this.results = results;
    }
}
