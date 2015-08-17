/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.bootstrap;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;

/**
 *
 * @author steve
 */
public class BootPayaraMicro {
    
    public static void main(String[] args) throws BootstrapException {
         PayaraMicro.bootstrap();
    }
    
}
