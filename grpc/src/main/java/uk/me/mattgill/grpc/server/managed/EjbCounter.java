package uk.me.mattgill.grpc.server.managed;

import javax.ejb.Singleton;

@Singleton
public class EjbCounter {

    private int count;

    public int incrementCount() {
        return count++;
    }

    public int getCount() {
        return count;
    }

}
