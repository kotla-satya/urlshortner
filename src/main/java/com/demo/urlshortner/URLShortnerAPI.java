package com.demo.urlshortner;

import com.demo.urlshortner.bean.UrlObject;
import com.demo.urlshortner.dao.UrlIdentifierDao;
import com.demo.urlshortner.dao.UrlTrackerDao;
import com.demo.urlshortner.util.Base62Util;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by skotla on 4/17/16.
 */
@Api(name = "urlshortner",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "sampleendpoint-1280.appspot.com",
                ownerName = "sampleendpoint-1280.appspot.com",
                packagePath = ""),
        description = "Api Service for URL Shortner. Shorten a URL, Expand a shorten URL")
public class URLShortnerAPI {

    private static final Logger log = Logger.getLogger(URLShortnerAPI.class.getName());

    @ApiMethod(name = "shorten", httpMethod = HttpMethod.POST)
    public UrlObject shortUrl(@Named("longUrl") String longUrl) {
        log.info("Start of shorturl" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date()));
        UrlObject urlObj;
        try {
            if ("".equals(longUrl) || longUrl.indexOf(Constants.BASE_URL) > -1) {
                urlObj = new UrlObject();
                urlObj.setSuccess(false);
                if ("".equals(longUrl)) {
                    urlObj.setErrMsg("Entered Long URL is not valid, it can not be blank.");
                } else {
                    urlObj.setErrMsg("Short URL can not be Shorten, entered URL is :" + longUrl);
                }
                log.log(Level.WARNING, "Long url input is not valid. Long Url :" + longUrl);
                return urlObj;
            }
            UrlTrackerDao urlTrackerDao = new UrlTrackerDao();
            urlObj = urlTrackerDao.getLongUrlDetails(longUrl);
            if (urlObj != null) {
                return urlObj;
            }
            UrlIdentifierDao urlIDDao = new UrlIdentifierDao();
            Long urlID = urlIDDao.getNextSeqID();
            urlObj = new UrlObject();
            if (urlID == null) {
                urlObj.setErrMsg("Error creating Unique ID");
                urlObj.setSuccess(false);
                return urlObj;
            }
            String shortUrlID = Base62Util.fromBase10(urlID);
            urlObj = urlTrackerDao.addShortUrl(urlID, Constants.BASE_URL + shortUrlID, longUrl);
        } catch (Exception e) {
            urlObj = new UrlObject();
            urlObj.setSuccess(false);
            urlObj.setErrMsg("Exception while creating Short URL :" + e.getMessage());
            log.log(Level.SEVERE, "Exception while creating Short URL for Long URL :" + longUrl +
                    "/n Error Msg :" + e.getMessage());
        }
        log.info("End of shorturl" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date()));
        return urlObj;
    }


    @ApiMethod(name = "expand", httpMethod = HttpMethod.GET)
    public UrlObject expandUrl(@Named("shortUrl") String shortUrl) {
        UrlObject urlObj;
        String shortUrlID;
        try {
            shortUrlID = shortUrl.substring(Constants.BASE_URL_LENGTH);
            if (!shortUrl.contains(Constants.BASE_URL) || "".equals(shortUrlID)) {
                urlObj = new UrlObject();
                urlObj.setSuccess(false);
                urlObj.setErrMsg("The url " + shortUrl + " is not a valid Short URL");
                log.log(Level.WARNING, "The url " + shortUrl + " is not a valid Short URL");
                return urlObj;
            }
            Long urlID = Base62Util.toBase10(shortUrlID);
            UrlTrackerDao urlTrackerDao = new UrlTrackerDao();
            urlObj = urlTrackerDao.getExpandedURL(urlID);
            if (urlObj == null) {
                urlObj = new UrlObject();
                urlObj.setSuccess(false);
                urlObj.setErrMsg("No Matching Short URL found to expand. Short URL entered is :" + shortUrl);
            }
        } catch (Exception e) {
            urlObj = new UrlObject();
            urlObj.setSuccess(false);
            urlObj.setErrMsg("Exception while fetching Long URL, Short URL :" + shortUrl + " Error :" + e.getMessage());
            log.log(Level.SEVERE, "Exception while fetching Long URL, Short URL :" + shortUrl
                    + "/n Error Msg: " + e.getMessage());
        }
        return urlObj;
    }

    //TODO: implement this method to query most used frequently expanded short urls.Counter and accessed datetime columns can be used
    @ApiMethod(name = "top10ShortUrls", httpMethod = HttpMethod.GET)
    public List<UrlObject> top10ShortUrls() {
        return new ArrayList<UrlObject>();
    }

}
