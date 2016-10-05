/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.requesttracing.traced.beans;

import fish.payara.examples.requesttracing.traced.interfaces.EJBInterface;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andrew Pielage
 */
@Stateless
public class EJB implements EJBInterface
{
    @Inject
    RequestBean requestBean;
    
    @Override
    public void traceMessage(String message)
    {
        System.out.println("Entering EJB!");
        requestBean.traceMessage(message);
        System.out.println("Exiting EJB!");
    }
}
