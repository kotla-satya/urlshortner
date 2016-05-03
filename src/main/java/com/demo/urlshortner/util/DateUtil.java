package com.demo.urlshortner.util;

import java.sql.Timestamp;

/**
 * Created by skotla on 4/22/16.
 */
public class DateUtil {

    public static Timestamp getCurrentTimeinSqlTimeStamp() {
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
        return ts;
    }
}
