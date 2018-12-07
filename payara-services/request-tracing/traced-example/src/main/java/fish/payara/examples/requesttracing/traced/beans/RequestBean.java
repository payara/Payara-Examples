/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.requesttracing.traced.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import fish.payara.examples.requesttracing.traced.interfaces.RequestBeanInterface;
import org.eclipse.microprofile.opentracing.Traced;

/**
 *
 * @author Andrew Pielage
 */
@RequestScoped
@Traced
public class RequestBean implements RequestBeanInterface
{
    private final int sleepTime = 5000;
    
    @Override
    public void traceMessage(String message)
    {
        System.out.println("I'm being traced! "
                + "Sleeping to exceed the threshold...");
        try
        {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(RequestBean.class.getName()).log(Level.INFO, 
                    "Interrupted");
        }
        
        System.out.println("Finished sleeping! Here, have a message: " 
                + message);
        System.out.println("If you've enabled Request Tracing and set its "
                + "threshold to less than " + (sleepTime / 1000) 
                + " seconds, then you should see the trace once the "
                + "EJB has exited!");
    }
}
