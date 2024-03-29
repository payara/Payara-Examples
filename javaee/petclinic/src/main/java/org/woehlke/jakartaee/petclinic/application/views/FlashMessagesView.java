package org.woehlke.jakartaee.petclinic.application.views;

import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;

import java.io.Serializable;

/**
 *
 */
public interface FlashMessagesView extends Serializable {

    long serialVersionUID = 2936821773310905949L;

    void addInfoMessage(String summary, String detail);

    void addWarnMessage(String summary, String detail);

    void addInfoMessage(String summary, EntityBase entity);

    void addWarnMessage(String summary, EntityBase entity);

    void addErrorMessage(String summary, EntityBase entity);

    void addWarnMessage(RuntimeException e, EntityBase entity);

    void flashTheMessages();
}
