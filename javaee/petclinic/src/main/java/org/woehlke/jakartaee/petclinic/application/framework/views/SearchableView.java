package org.woehlke.jakartaee.petclinic.application.framework.views;


/**
 *
 */
public interface SearchableView {

    String search();
    void performSearch();

    String getSearchterm();
    void setSearchterm(String searchterm);
    String clearSearchterm();
}
