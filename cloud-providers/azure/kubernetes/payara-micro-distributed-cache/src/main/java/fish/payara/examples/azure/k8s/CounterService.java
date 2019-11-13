package fish.payara.examples.azure.k8s;

import fish.payara.cluster.Clustered;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
@Clustered
public class CounterService implements Serializable {

    private final AtomicInteger userCounter = new AtomicInteger(0);

    public Integer getNextValue() {
        return userCounter.incrementAndGet();
    }

    public Integer getCurrentValue(){
        return userCounter.get();
    }
}
