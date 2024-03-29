package org.woehlke.jakartaee.petclinic.application.framework.views;

import org.woehlke.jakartaee.petclinic.application.views.LanguageView;


/**
 *
 */
public interface LanguageChangeableView {


    LanguageView getLanguageView();

    void setLanguageView(LanguageView languageView);
}
