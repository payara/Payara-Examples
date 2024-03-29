package org.woehlke.jakartaee.petclinic.application.framework;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @param <T>
 */
public interface SearchableEntity<T extends EntityBase> {

    long serialVersionUID = -1799267609856447186L;

    List<T> search(@NotNull String searchterm);

    void resetSearchIndex();
}
