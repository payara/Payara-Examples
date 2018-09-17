package fish.payara.eventsourcing.savings.init;

import fish.payara.eventsourcing.jpa.dao.AcctHolderFacade;
import fish.payara.eventsourcing.jpa.dao.SavingsAcctFacade;
import fish.payara.eventsourcing.jpa.entities.AcctHolder;
import fish.payara.eventsourcing.jpa.entities.SavingsAcct;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport;
import org.h2.tools.Server;

@ConnectionFactoryDefinition(name = "java:module/env/KafkaConnectionFactory",
        description = "Kafka Connection Factory",
        interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory",
        resourceAdapter = "kafka-rar-0.1.0",
        minPoolSize = 2,
        maxPoolSize = 2,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
        properties = {
            "bootstrapServersConfig=localhost:9092",
            "clientId=PayaraMicroMessenger"
        })
@DataSourceDefinition(
        // global to circumvent https://java.net/jira/browse/GLASSFISH-21447
        name = "java:global/savingsDS",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:~/savings_db;DB_CLOSE_ON_EXIT=FALSE;FILE_LOCK=NO"
)

@ApplicationScoped
public class SavingsInitializer {

    private static final Logger LOGGER = Logger.getLogger(SavingsInitializer.class.getName());

    @Inject
    private AcctHolderFacade acctHolderFacade;

    @Inject
    private SavingsAcctFacade savingsAcctFacade;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        populateDatabase();
        startDBServer();
    }

    private void populateDatabase() {
        LOGGER.log(Level.INFO, "Initializing database");

        AcctHolder david = new AcctHolder();
        AcctHolder dominika = new AcctHolder();
        AcctHolder matt = new AcctHolder();

        SavingsAcct davidsAcct = new SavingsAcct();
        SavingsAcct dominikasAcct = new SavingsAcct();
        SavingsAcct mattsAcct = new SavingsAcct();

        david.setFirstName("David");
        david.setLastName("Heffelfinger");

        dominika.setFirstName("Dominika");
        dominika.setLastName("Tasarz");

        matt.setFirstName("Matthew");
        matt.setLastName("Gill");

        acctHolderFacade.create(david);
        acctHolderFacade.create(dominika);
        acctHolderFacade.create(matt);

        LOGGER.log(Level.INFO, "Created the following account holders:");
        List<AcctHolder> allAcctHolders = acctHolderFacade.findAll();

        allAcctHolders.forEach(acctHolder -> {
            LOGGER.log(Level.INFO, acctHolder.toString());
        });

        davidsAcct.setAcctHolder(david);
        davidsAcct.setAcctNbr(1123L);
        davidsAcct.setCheckingAcctNbr(1234L);
        davidsAcct.setAcctBalance(250.00);

        dominikasAcct.setAcctHolder(dominika);
        dominikasAcct.setAcctNbr(2123L);
        dominikasAcct.setCheckingAcctNbr(2234L);
        dominikasAcct.setAcctBalance(1_000_000.00);

        mattsAcct.setAcctHolder(matt);
        mattsAcct.setAcctNbr(3123L);
        mattsAcct.setCheckingAcctNbr(3234L);
        mattsAcct.setAcctBalance(100_000.00);

        savingsAcctFacade.create(davidsAcct);
        savingsAcctFacade.create(dominikasAcct);
        savingsAcctFacade.create(mattsAcct);

        LOGGER.log(Level.INFO, "Created the following savings accounts:");
        List<SavingsAcct> allSavingsAccts = savingsAcctFacade.findAll();

        allSavingsAccts.forEach(acctHolder -> {
            LOGGER.log(Level.INFO, acctHolder.toString());
        });

    }

    private void startDBServer() {
        try {
            // start a TCP server
            Server server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();

            LOGGER.log(Level.INFO, "Server started and connection is open.");
            LOGGER.log(Level.INFO, "URL: jdbc:h2:{0}/~/savings_db", server.getURL());
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, "SQL Exception caught", sqlException);
        }
    }

}
