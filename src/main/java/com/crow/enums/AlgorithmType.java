package com.crow.enums;

public enum AlgorithmType {
    HOT_BASED_RECOMMEND("mostPopular_recommend.py"),
    ;
    private String algorithmType;
    private AlgorithmType (String algorithmType){
        this.algorithmType=algorithmType;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }
}
