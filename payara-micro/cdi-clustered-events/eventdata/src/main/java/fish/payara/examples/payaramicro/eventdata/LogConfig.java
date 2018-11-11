/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.eventdata;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsie
 */
public class LogConfig {

    public static void init() {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        Logger.getAnonymousLogger().addHandler(consoleHandler);
    }
}
