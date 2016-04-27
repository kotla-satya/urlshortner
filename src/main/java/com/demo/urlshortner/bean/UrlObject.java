package com.demo.urlshortner.bean;

/**
 * Created by skotla on 4/17/16.
 */
public class UrlObject {

    private Long urlID;
    private String longUrl;
    private String shortUrl;
    private String errMsg;
    private boolean success;
    private Long accessCount;

    public Long getUrlID() {
        return urlID;
    }

    public void setUrlID(Long urlID) {
        this.urlID = urlID;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Long accessCount) {
        this.accessCount = accessCount;
    }
}
