package com.chenguang.courserasearch.model;

public class PartnerModel {

    private static PartnerModel instance;

    private PartnerModel() {
    }

    public static PartnerModel getInstance() {
        if (instance == null) {
            instance = new PartnerModel();
        }
        return instance;
    }
}
