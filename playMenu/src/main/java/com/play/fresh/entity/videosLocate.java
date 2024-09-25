package com.play.fresh.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class videosLocate {

    private String num;
    private String locate;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }
}
