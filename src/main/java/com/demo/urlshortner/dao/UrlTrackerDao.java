package com.demo.urlshortner.dao;

import com.demo.urlshortner.bean.UrlObject;
import com.demo.urlshortner.dao.service.SQLConnection;
import com.demo.urlshortner.util.DateUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skotla on 4/17/16.
 */
public class UrlTrackerDao {

    private static final Logger log = Logger.getLogger(UrlTrackerDao.class.getName());

    public UrlObject addShortUrl(Long urlID, String shortUrl, String longUrl) throws Exception {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        UrlObject urlObject = null;
        try {
            conn = SQLConnection.getConnection();
            Timestamp ts = DateUtil.getCurrentTimeinSqlTimeStamp();
            String insertTableSQL = "INSERT INTO URL_TRACKER"
                    + "(URL_ID, ORIGINAL_URL, SHORT_URL, CREATED_DATETIME, ACCESSED_DATETIME, COUNTER) VALUES"
                    + "(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setLong(1, urlID);
            preparedStatement.setString(2, longUrl);
            preparedStatement.setString(3, shortUrl);
            preparedStatement.setTimestamp(4, ts);
            preparedStatement.setTimestamp(5, ts);
            preparedStatement.setInt(6, 0);
            int success = preparedStatement.executeUpdate();
            if(success == 1) {
                urlObject = new UrlObject();
                urlObject.setShortUrl(shortUrl);
                urlObject.setLongUrl(longUrl);
                urlObject.setSuccess(true);
                //urlObject.setUrlID(urlID);
                return  urlObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.log(Level.SEVERE, "Exception while adding new short URL entry to Database, error msg  :"
                    +e.getMessage());
        }finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (conn != null) {
                conn.close();
            }

        }

        return null;
    }


    public UrlObject getLongUrlDetails(String longUrl) throws Exception {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        UrlObject urlObject = null;
        try {
            conn = SQLConnection.getConnection();
            Timestamp ts = DateUtil.getCurrentTimeinSqlTimeStamp();
            String insertTableSQL = "SELECT ORIGINAL_URL, SHORT_URL FROM URL_TRACKER WHERE ORIGINAL_URL = ?";
            preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, longUrl);
            //int success = preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                urlObject = new UrlObject();
                urlObject.setShortUrl(result.getString("SHORT_URL"));
                urlObject.setLongUrl(result.getString("ORIGINAL_URL"));
                urlObject.setSuccess(true);
                return  urlObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.log(Level.SEVERE, "Exception while fetching Long URL Details, error msg  :"+e.getMessage());
        }finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (conn != null) {
                conn.close();
            }

        }

        return null;
    }


    public UrlObject getExpandedURL(Long urlID) throws Exception {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        UrlObject urlObject = null;
        try {
            conn = SQLConnection.getConnection();
            Timestamp ts = DateUtil.getCurrentTimeinSqlTimeStamp();
            String insertTableSQL = "SELECT ORIGINAL_URL, SHORT_URL, COUNTER FROM URL_TRACKER WHERE URL_ID = ?";
            preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setLong(1, urlID);
            ResultSet result = preparedStatement.executeQuery();
            long counter = 0;
            if(result.next()) {
                urlObject = new UrlObject();
                urlObject.setShortUrl(result.getString("SHORT_URL"));
                urlObject.setLongUrl(result.getString("ORIGINAL_URL"));
                counter = result.getLong("COUNTER");
                urlObject.setSuccess(true);
                String updateCounterSQL = "UPDATE URL_TRACKER SET COUNTER = ?, ACCESSED_DATETIME = ? WHERE URL_ID = ?";
                preparedStatement = conn.prepareStatement(updateCounterSQL);
                preparedStatement.setLong(1, ++counter);
                preparedStatement.setTimestamp(2, ts);
                preparedStatement.setLong(3, urlID);
                int success = preparedStatement.executeUpdate();
                //TODO need to handle counter update failures and do retry
                if(success == 1) {
                    urlObject.setAccessCount(counter);
                }else{
                    urlObject.setAccessCount(counter);
                }
            }

            return  urlObject;
        }catch (Exception e){
            e.printStackTrace();
            log.log(Level.SEVERE, "Exception while fetching Short URL, error msg :"+e.getMessage());
        }finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (conn != null) {
                conn.close();
            }

        }

        return null;
    }



}
