package fish.payara.examples.microprofile.metrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mike on 12/01/18.
 */
@Path("tickets")
@ApplicationScoped
public class TicketResource {

    private final String SOLD = "sold";
    List<String> tickets = Arrays.asList(new String[100]);

    @Inject
    @Metric
    Counter ticketCount;

    @PostConstruct
    private void init() {
        // initialise counter with beginning number
        ticketCount.inc(100);
    }

    /*
        We need to set the "name" attribute to make sure the metrics
￼       are differentiated, otherwise there will be a single
￼       Meter for the overloaded methods, rather than one each.
     */

    @GET
    @Path("buy")
    @Metered(name = "Buy any ticket")
    public String buyTicket() {
        int i = 0;
        for (String t : tickets) {
            if (t != SOLD) {
                tickets.set(i, SOLD);
                ticketCount.dec();
                return "Success! You have bought ticket number " + i;
            } else {
                i++;
            }
        }
        return "Error! Sold out";
    }

    @GET
    @Path("buy/{id}")
    @Metered(name = "Buy specific ticket")
    public String buyTicket(@PathParam("id") int id) {
        if (tickets.get(id) != SOLD) {
            tickets.set(id, SOLD);
            ticketCount.dec();
            return "Success! You have bought ticket number " + id;
        } else {
            return "Error! Ticket number " + id + " is not available!";
        }
    }

    @GET
    @Path("return/{id}")
    @Metered
    public String returnTicket(@PathParam("id") int id) {
        if (tickets.get(id) == SOLD) {
            tickets.set(id, "");
            ticketCount.inc();
            return "Success! Ticket number " + id + " has been returned!";
        } else {
            return "Error! Ticket number " + id + " has not been sold!";
        }
    }

}