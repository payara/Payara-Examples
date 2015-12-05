/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.javaee.simpleremoteejb;

import javax.ejb.Stateless;

/**
 *
 * @author martin
 */
@Stateless
public class HelloWorldBean implements HelloWorldRemote{

    @Override
    public String helloWorld() {
        return "hello world";
    }


}
