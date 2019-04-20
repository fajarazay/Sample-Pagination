package com.fajarazay.github.samplepagination.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private ArrayList<UserItem> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<UserItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<UserItem> items) {
        this.items = items;
    }


}