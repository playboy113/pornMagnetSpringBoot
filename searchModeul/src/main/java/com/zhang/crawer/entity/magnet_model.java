package com.zhang.crawer.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class magnet_model {

    private String num;//番号

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String title;
    private String magenet;
    private String types;
    private String actress;
    private String subline;
    private String HD;
    private String date;

    private String producer;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubline() {
        return subline;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubline(String subline) {
        this.subline = subline;
    }

    public String getHD() {
        return HD;
    }

    public void setHD(String HD) {
        this.HD = HD;
    }







    public String getMagenet() {
        return magenet;
    }

    public void setMagenet(String magenet) {
        this.magenet = magenet;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getActress() {
        return actress;
    }

    public void setActress(String actress) {
        this.actress = actress;
    }


}
