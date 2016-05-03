package com.demo.urlshortner.dao;

import com.demo.urlshortner.dao.service.SQLConnection;
import com.demo.urlshortner.util.DateUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skotla on 4/18/16.
 */
public class UrlIdentifierDao {

    private static final Logger log = Logger.getLogger(UrlIdentifierDao.class.getName());

    public Long getNextSeqID() throws Exception {
        Long id = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = SQLConnection.getConnection();
            Timestamp ts = DateUtil.getCurrentTimeinSqlTimeStamp();
            String insertTableSQL = "INSERT INTO URL_IDENTIFIER"
                    + "(CREATED_DATETIME) VALUES"
                    + "(?)";
            preparedStatement = conn.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, ts);
            int success = preparedStatement.executeUpdate();
            if (success == 1) {
                ResultSet result = preparedStatement.getGeneratedKeys();
                if (result.next()) {
                    id = result.getLong(1);
                    System.out.print("ID Generated: " + id);
                }
            }
        } catch (Exception e) {
            System.out.print("Err Msg : " + e.getMessage());
            e.printStackTrace();
            log.log(Level.SEVERE, "Exception during creation of Unique ID, error msg  :"
                    + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return id;
    }
}
