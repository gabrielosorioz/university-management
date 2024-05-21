package br.com.idealizeall.universitymanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConfig {

    private DBConfig(){};

    private static final Logger log = Logger.getLogger(DBConfig.class.getName());

    private static Connection getConnection(Scheme scheme) {
        Connection conn = null;
        try {
            Class.forName(scheme.getDriver());
            conn = DriverManager.getConnection(scheme.getUrl(),scheme.getUsername(),
                    scheme.getPassword());
            if (conn != null) {
                log.info("------------------------Connection is established-------------------------");
                return conn;
            } else {
                log.severe("----------------------Error at establish connection with database--------------------------");
            }

        } catch (SQLException ex ) {
            log.severe("Error at database" + ex.getMessage() + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            log.severe("Error at load driver class of database.");
        }
        return null;
    }

    public static Connection getPSQLConnection(){
        return getConnection(Scheme.PSQL_DB);
    }

}
