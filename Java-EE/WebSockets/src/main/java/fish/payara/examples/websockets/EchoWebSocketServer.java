/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.websockets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author steve
 */
@ApplicationScoped
@ServerEndpoint("/echo")
public class EchoWebSocketServer {
    
    private static final Logger logger = Logger.getLogger(EchoWebSocketServer.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.log(Level.INFO, "Opening Session {0}", session.getId());        
    }

    @OnClose
    public void onClose(Session session) {
        logger.log(Level.INFO, "Closing Session {0}", session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        logger.log(Level.INFO,"onError",t);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.log(Level.INFO, "Received Message on Session {0}", session.getId());  
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
}
