package fish.payara.cruddatabase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.Server;

public class H2DatabaseWrapper {

    private void startH2Database() {

        //Shamelessly "borrowed" (with modifications) from http://stackoverflow.com/questions/29183503/start-h2-database-programmatically
        try {

            String dbFileName = "customerdb.mv.db";

            String tmpDir = System.getProperty("java.io.tmpdir");

            String dbFileNamePath = String.format("%s%s%s", tmpDir, System.getProperty("file.separator"), dbFileName);

            String jdbcUrl = String.format("jdbc:h2:tcp://localhost/%s/customerdb", tmpDir);

            System.out.println(String.format("Deleting existing database file at %s", dbFileNamePath));
            Files.deleteIfExists(Paths.get(dbFileNamePath));

            Server.createTcpServer("-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");

            Connection conn = DriverManager.getConnection(String.format(
                    jdbcUrl), "sa", "");
            System.out.println("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());
            System.out.println("JDBC URL: " + jdbcUrl);
            System.out.println("Login as user \"sa\" with no password");

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new H2DatabaseWrapper().startH2Database();
    }
}
