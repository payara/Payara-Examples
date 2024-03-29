package org.woehlke.jakartaee.petclinic.application.framework.views;

import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;

import java.util.List;


/**
 * @param <T>
 */
public interface CrudView<T extends EntityBase> extends SearchableView, LanguageChangeableView {

    String showDetailsForm(T o);
    String cancelDetails();

    String showNewForm();
    String cancelNew();
    String saveNew();

    String showEditForm();
    String cancelEdited();
    String saveEdited();

    String showDeleteForm();
    String cancelDelete();
    String performDelete();

    T getEntity();
    void setEntity(T entity);

    List<T> getList();
    void setList(List<T> list);

}
