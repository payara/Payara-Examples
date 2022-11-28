/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2022 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.team.info.controllers;

import fish.payara.team.info.models.MemberEntity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import java.util.Collection;

/**
 * @author Fraser Savage
 */
@Stateless
public class MemberSessionBean {

    @PersistenceContext(unitName = "TeamInfoPU")
    EntityManager entityManager;

    public void createTeamMember(MemberEntity memberEntity) {
        try {
            entityManager.persist(memberEntity);
        } catch (ConstraintViolationException e) {
            System.out.println("Could not create team member:" + e);
        }
    }

    public MemberEntity getTeamMemberById(Long Id) {
        return entityManager.find(MemberEntity.class, Id);
    }

    public Collection<MemberEntity> getTeamMemberByName(String name) {
        Query query = entityManager.createQuery("SELECT m FROM MemberEntity m WHERE m.name = '" + name + "'");
        return (Collection<MemberEntity>) query.getResultList();
    }

    public void removeTeamMember(Long Id) {
        MemberEntity member = entityManager.find(MemberEntity.class, Id);
        if (member != null) {
            //entityManager.getTransaction().begin();
            entityManager.remove(member);
            //entityManager.getTransaction().commit();
        }
    }

    public void updateTeamMemberName(Long Id, String name) {
        MemberEntity member = entityManager.find(MemberEntity.class, Id);
        if (member != null) {
            //entityManager.getTransaction().begin();
            member.setName(name);
            //entityManager.getTransaction().commit();
        }
    }

    public void  updateTeamMemberBio(Long Id, String bio) {
        MemberEntity member = entityManager.find(MemberEntity.class, Id);
        if (member != null) {
            //entityManager.getTransaction().begin();
            member.setBio(bio);
            //entityManager.getTransaction().commit();
        }
    }

    public void updateTeamMemberEmail(Long Id, String email) {
        MemberEntity member = entityManager.find(MemberEntity.class, Id);
        if (member != null) {
            //entityManager.getTransaction().begin();
            member.setEmail(email);
            //entityManager.getTransaction().commit();
        }
    }

}
