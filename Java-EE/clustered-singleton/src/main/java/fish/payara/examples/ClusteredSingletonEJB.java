/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples;

import fish.payara.cluster.Clustered;
import javax.ejb.Singleton;

/**
 *
 * @author lprimak
 */
@Clustered
@Singleton
public class ClusteredSingletonEJB {
    /**
     * simple clustered singleton business method
     */
    public void businessMethod() {
        System.out.println("businessMethod");
    }
}
