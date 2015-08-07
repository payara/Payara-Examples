package fish.payara.simple.login.controllers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.sql.*;
/**
 * @author Fraser Savage
 */
public final class GetConnection {
    private static final Logger log = Logger.getLogger(GetConnection.class.getCanonicalName());
    /**
     * Uses JNDI and a DataSource to connect to the JDBC resource.
     * @return
     */
    public static Connection getJNDIConnection() {
        final String DATASOURCE_CONTEXT = "jdbc/simple-login";

        Connection result = null;

        try {
            Context initialContext = new InitialContext();
            if (initialContext == null) { log.log(Level.SEVERE, "Cannot get JNDI initial context."); }

            DataSource dataSource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);

            if (dataSource != null) {
                result = dataSource.getConnection();
            } else {
                log.log(Level.SEVERE, "Failed to lookup DataSource.");
            }
        } catch (NamingException e) {
            log.log(Level.SEVERE, "Could not get a connection to the DataSource, with exception: " + e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Could not get a connection to the DataSource, with exception: " + e);
        }

        return result;
    }
}
