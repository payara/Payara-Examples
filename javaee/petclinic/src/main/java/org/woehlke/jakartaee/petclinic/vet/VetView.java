package org.woehlke.jakartaee.petclinic.vet;

import org.primefaces.model.DualListModel;
import org.woehlke.jakartaee.petclinic.application.framework.views.CrudView;
import org.woehlke.jakartaee.petclinic.application.framework.views.LanguageChangeableView;
import org.woehlke.jakartaee.petclinic.application.framework.views.SearchableView;
import org.woehlke.jakartaee.petclinic.application.framework.views.CrudView2Model;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import java.io.Serializable;


/**
 *
 */
public interface VetView extends CrudView<Vet>, CrudView2Model {

    String JSF_PAGE = "veterinarian.jsf";

    Specialty findSpecialtyByName(String name);

    DualListModel<Specialty> getSpecialtiesPickList();
    void setSpecialtiesPickList(DualListModel<Specialty> specialtiesPickList);

    long serialVersionUID = -4141782100256382881L;
}
