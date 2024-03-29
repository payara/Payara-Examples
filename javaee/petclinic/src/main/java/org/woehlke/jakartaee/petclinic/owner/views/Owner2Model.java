package org.woehlke.jakartaee.petclinic.owner.views;

import org.woehlke.jakartaee.petclinic.application.framework.views.CrudView2Model;

/**
 *
 */
public interface Owner2Model extends CrudView2Model {

    long serialVersionUID = 1490389883743112662L;

    void loadPetTypeList();
}
