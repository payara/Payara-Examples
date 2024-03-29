package org.woehlke.jakartaee.petclinic.application.conf;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 05.01.14
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */
@Log
@Named("petclinicApplication")
@ApplicationScoped
@ApplicationPath("/rest")
public class PetclinicApplication extends Application implements Serializable {

    private static final long serialVersionUID = 6101321761195838117L;

    private MessageProvider provider;

    public ResourceBundle getMsg() {
        return this.provider.getBundle();
    }

    public void setMsg(ResourceBundle msg) {
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info(" postConstruct: " + PetclinicApplication.class.getSimpleName());
        this.provider = new MessageProvider();
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }
}
