package com.example.venadosdefinitivo.models;

import com.google.gson.annotations.SerializedName;

public class data {

    @SerializedName("data")
    dataList dataList;

    public dataList getDataList() {
        return dataList;
    }

    public void setDataList(dataList dataList) {
        this.dataList = dataList;
    }
}
