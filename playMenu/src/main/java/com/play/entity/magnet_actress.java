package com.play.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class magnet_actress {
    private int id;
    private String num;
    private String actress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getActress() {
        return actress;
    }

    public void setActress(String actress) {
        this.actress = actress;
    }
}
