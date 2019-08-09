package com.crow.enums;

public enum LabelType {
    SPORT_COLOUMN("sport"),
    ENTERTAINMENT_COLOUMN("ent"),
    POLITICS_COLOUMN("politics"),
    RECOMMEND_COLOUMN("recommend"),
    ;



    private String label;
    private LabelType (String label){
        this.label=label;
    }
    public String getLabel() {
        return label;
    }
}
