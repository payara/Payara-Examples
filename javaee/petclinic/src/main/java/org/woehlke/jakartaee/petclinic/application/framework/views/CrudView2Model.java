package org.woehlke.jakartaee.petclinic.application.framework.views;


/**
 *
 */
public interface CrudView2Model {

    void loadList();
    void newEntity();
    void saveNewEntity();
    void saveEditedEntity();
    void deleteSelectedEntity();

    void postConstruct();
    void preDestroy();
}
