package com.crow.enums;

public enum AlgorithmType {
    HOT_BASED_RECOMMEND("mostPopular_recommend.py"),
    CONTTENT_BASED_RECONMMEND("content_recommend.py"),
    UCF_BASED_RECOMMEND("ucf_recommend.py"),
    ;
    private String algorithmType;
    private AlgorithmType (String algorithmType){
        this.algorithmType=algorithmType;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }
}
