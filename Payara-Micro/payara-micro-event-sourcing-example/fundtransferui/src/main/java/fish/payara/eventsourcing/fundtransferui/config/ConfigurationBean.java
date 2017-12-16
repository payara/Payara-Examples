package fish.payara.eventsourcing.fundtransferui.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
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
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@ApplicationScoped
public class ConfigurationBean {

}
