package org.woehlke.jakartaee.petclinic.application.framework.db;

import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 */
public interface CrudService<T extends EntityBase> extends Serializable {

    long serialVersionUID = 8240918516324226703L;

    List<T> getAll();

    T findById(long id);

    T addNew(T entity);

    T update(T entity);

    void delete(long id);

}
