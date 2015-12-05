/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.javaee.simpleremoteejb;

import javax.ejb.Remote;

/**
 *
 * @author steve
 */
@Remote
public interface HelloWorldRemote {

    String helloWorld();
    
}
