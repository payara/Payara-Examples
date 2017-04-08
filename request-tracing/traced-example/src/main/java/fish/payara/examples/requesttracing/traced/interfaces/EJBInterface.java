/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.requesttracing.traced.interfaces;

import javax.ejb.Remote;

/**
 *
 * @author Andrew Pielage
 */
@Remote
public interface EJBInterface
{
    public void traceMessage(String message);
}
