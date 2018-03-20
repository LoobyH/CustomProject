package com.goldmantis.wb.viewdemo.model;

/**
 * Created by 00027450 on 2017/11/15.
 */

public class PictureBean {

    private Integer positionId;
    private String positionName;
    private String url;
    public Boolean isSelector = false;   // 是否字体背景变灰 TRUE是  FALSE  不是
    public Boolean isCloseShow = false;
    public Boolean clickable = true;
    private String id;
    private String fileUrl;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSelector() {
        return isSelector;
    }

    public void setSelector(Boolean selector) {
        isSelector = selector;
    }

    public Boolean getCloseShow() {
        return isCloseShow;
    }

    public void setCloseShow(Boolean closeShow) {
        isCloseShow = closeShow;
    }

    public Boolean getClickable() {
        return clickable;
    }

    public void setClickable(Boolean clickable) {
        this.clickable = clickable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
