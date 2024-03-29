package org.woehlke.jakartaee.petclinic.application.views;

import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.conf.PetclinicApplication;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Log
@Named("flashMessagesView")
@SessionScoped
public class FlashMessagesViewImpl implements FlashMessagesView, Serializable {

    private static final long serialVersionUID = -2267751568724878682L;

    @Inject
    private PetclinicApplication petclinicApplication;

    private List<FacesMessage> messageHolder = new ArrayList<>();

    public void flashTheMessages(){
        for(FacesMessage message :messageHolder){
            String clientId = null;
            FacesContext.getCurrentInstance().addMessage(clientId, message);
            messageHolder.remove(message);
        }

    }

    public void addInfoMessage(String summary, String detail) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_INFO;
        this.addMessage(messageSeverity, summary, detail);
    }

    public void addWarnMessage(String summary, String detail) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_WARN;
        this.addMessage(messageSeverity, summary, detail);
    }

    @Override
    public void addInfoMessage(String summary, EntityBase entity) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_INFO;
        this.addMessageForEntity(summary, entity,  messageSeverity);
    }

    @Override
    public void addWarnMessage(String summary, EntityBase entity) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_WARN;
        this.addMessageForEntity(summary, entity, messageSeverity);
    }

    @Override
    public void addErrorMessage(String summary, EntityBase entity) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_ERROR;
        this.addMessageForEntity(summary, entity, messageSeverity);
    }

    @Override
    public void addWarnMessage(RuntimeException e, EntityBase entity) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_WARN;
        this.addMessageForEntityAndRuntimeException(e, entity, messageSeverity);
    }

    private void addMessageForEntity(
            String summary,
            EntityBase entity,
            FacesMessage.Severity messageSeverity
    ) {
        log.info("summary:           " + summary);
        String detail = "";
        if (entity != null) {
            log.info("addFrontendMessageForEntity.PrimaryKey: " + entity.getPrimaryKey());
            log.info("addFrontendMessageForEntity.id:         " + entity.getId());
            log.info("addFrontendMessageForEntity.uud:        " + entity.getUuid());
            detail = entity.getPrimaryKey();
        } else {
            String msg = "entity == null ";
            log.info(msg);
            detail = msg;
        }
        this.addMessage(messageSeverity, summary, detail);
    }

    private void addMessageForEntityAndRuntimeException(
            RuntimeException e,
            EntityBase entity,
            FacesMessage.Severity messageSeverity
    ) {

       String summary = e.getLocalizedMessage();
       log.info("-----------------------------------------------------\n");
       log.info("entity Table       " + entity.getTableName() + "\n");
       log.info("entity Class       " + entity.getClass().getName() + "\n");
       log.info("entity UUID        " + entity.getUuid() + "\n");
       log.info("entity ID          " + entity.getId() + "\n");
       log.info("entity PK          " + entity.getPrimaryKey() + "\n");
       log.info("-----------------------------------------------------\n");
       log.info("RuntimeException Class   " + e.getClass().getName() + "\n");
       log.info("RuntimeException Message " + e.getLocalizedMessage() + "\n");
       log.info("Exception Cause Class    " + e.getCause().getClass().getName() + "\n");
       log.info("Exception Cause Message   " + e.getCause().getLocalizedMessage() + "\n");
       log.info("-----------------------------------------------------\n");
        long i = 0L;
        for (StackTraceElement element : e.getStackTrace()) {
            i++;
            StringBuilder lfdnr = new StringBuilder();
            if (i < 10) {
                lfdnr.append(" ");
            }
            if (i < 100) {
                lfdnr.append(" ");
            }
            lfdnr.append(i);
            log.info("StackTrace[" + lfdnr.toString() + "]: " + element.getClassName());
            log.info(" . " + element.getMethodName() + " in: \n");
            log.info("StackTrace[" + lfdnr.toString() + "]: " + element.getFileName());
            log.info(" ( Line " + element.getLineNumber() + ")\n");
        }
        log.info("-----------------------------------------------------\n");
        this.addMessageForEntity(summary, entity, messageSeverity);
    }

    private void addMessage(
            FacesMessage.Severity messageSeverity,
            String summary,
            String detail
    ) {
        log.info("addFrontendMessage.summary:   " + summary);
        log.info("addFrontendMessage.detail:    " + detail);
        FacesMessage message = new FacesMessage(messageSeverity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+ FlashMessagesViewImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+ FlashMessagesViewImpl.class.getSimpleName());
    }

}
