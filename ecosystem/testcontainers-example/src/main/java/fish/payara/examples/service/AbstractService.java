/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2026 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public abstract class AbstractService<E,P> {

    private final Class<E> entityClass;

    @PersistenceContext
    private EntityManager em;

    public AbstractService(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager(){
        return em;
    }

    @Transactional(REQUIRED)
    public void create(E entity) {
        getEntityManager().persist(entity);
    }

    @Transactional(REQUIRED)
    public E edit(E entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional(REQUIRED)
    public void remove(E entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public P getIdentifier(E entity) {
        return (P)getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }

    public E find(P id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<E> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<E> findRange(int startPosition, int size) {
        return findRange(startPosition, size, null);
    }
    
    public List<E> findRange(int startPosition, int size, String entityGraph) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(size);
        q.setFirstResult(startPosition);
        if (entityGraph != null) {
            q.setHint("jakarta.persistence.loadgraph", getEntityManager().getEntityGraph(entityGraph));
        }
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        Root<E> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(getEntityManager().getCriteriaBuilder().count(root));
        Query query = getEntityManager().createQuery(criteriaQuery);
        return ((Long) query.getSingleResult()).intValue();
    }

    public Optional<E> findSingleByNamedQuery(String namedQueryName) {
        return findOrEmpty(() -> getEntityManager().createNamedQuery(namedQueryName, entityClass).getSingleResult());
    }

    public Optional<E> findSingleByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return findSingleByNamedQuery(namedQueryName, null, parameters);
    }

    public Optional<E> findSingleByNamedQuery(String namedQueryName, String entityGraph, Map<String, Object> parameters) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        TypedQuery<E> query = getEntityManager().createNamedQuery(namedQueryName, entityClass);
        rawParameters.forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if(entityGraph != null){
            query.setHint("jakarta.persistence.loadgraph", getEntityManager().getEntityGraph(entityGraph));
        }
        return findOrEmpty(query::getSingleResult);
    }

    public List<E> findByNamedQuery(String namedQueryName) {
        return findByNamedQuery(namedQueryName, -1);
    }

    public List<E> findByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return findByNamedQuery(namedQueryName, parameters, -1);
    }

    public List<E> findByNamedQuery(String namedQueryName, int resultLimit) {
        return findByNamedQuery(namedQueryName, Collections.EMPTY_MAP, resultLimit);
    }

    public List<E> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        rawParameters.forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        return query.getResultList();
    }

    public static <E> Optional<E> findOrEmpty(final DaoRetriever<E> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            //log
        }
        return Optional.empty();
    }

    @FunctionalInterface
    public interface DaoRetriever<E> {

        E retrieve() throws NoResultException;
    }

}
