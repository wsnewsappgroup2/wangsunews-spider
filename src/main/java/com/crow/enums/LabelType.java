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

    public static LabelType getlabelType(String label){
        LabelType type=LabelType.SPORT_COLOUMN;
        for(LabelType labelType:LabelType.values()){
            if(label.equals(labelType.getLabel())){
                type=labelType;
            }
        }
        return type;
    }
}
