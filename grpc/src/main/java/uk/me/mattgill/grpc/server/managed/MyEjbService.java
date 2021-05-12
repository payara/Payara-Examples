package uk.me.mattgill.grpc.server.managed;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import uk.me.mattgill.grpc.server.MyService;

@Dependent
public class MyEjbService extends MyService {

    @Inject
    private EjbCounter counter;

    @Override
    protected int getCount() {
        return counter.getCount();
    }

}
