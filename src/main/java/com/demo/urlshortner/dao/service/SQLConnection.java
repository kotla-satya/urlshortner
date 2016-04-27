package com.demo.urlshortner.dao.service;

import com.demo.urlshortner.Constants;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.demo.urlshortner.URLShortnerAPI;
import com.google.appengine.api.utils.SystemProperty;

/**
 * Created by skotla on 4/17/16.
 */
public class SQLConnection {

    public final static String DB_INSTANCE = "url45";
    public final static String SCHEMA_NAME = "urlshortner";
    public static final String GOOGLE_CLOUD_SQL_IP_ADDR = "2001:4860:4864:1:241b:52f9:d237:b2a7";
    private static final Logger log = Logger.getLogger(SQLConnection.class.getName());

    public static Connection getConnection(){
        Connection conn = null;
        try {
            String url;
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://"+Constants.PROJECT_ID+":"+DB_INSTANCE+"/"+SCHEMA_NAME+"?user=root&password=urlshort12";
                        //"?user=urlsdb&password=urlshort12";
            } else {
                //user=urlsdb&password=urlshort12
               Class.forName("com.mysql.jdbc.Driver");
               url = "jdbc:mysql://localhost:3306/"+SCHEMA_NAME+"?user=root&password=urlsdb";
            }
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "Exception while fetching SQL connection, Error Msg :"+e.getMessage());

        }

        return conn;
    }
}
