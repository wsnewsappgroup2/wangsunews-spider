package com.crow.entity;


import com.alibaba.fastjson.annotation.JSONField;

public class Algorithm {
    private Integer id;
    private String algorithm;
    @JSONField(name="name")
    private String algorithmCH;
    private String contributor;
    private String targetTypes;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithmCH() {
        return algorithmCH;
    }

    public void setAlgorithmCH(String algorithmCH) {
        this.algorithmCH = algorithmCH;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getTargetTypes() {
        return targetTypes;
    }

    public void setTargetTypes(String targetTypes) {
        this.targetTypes = targetTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
