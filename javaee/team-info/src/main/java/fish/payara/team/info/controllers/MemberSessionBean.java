package fish.payara.team.info.controllers;

import fish.payara.team.info.models.MemberEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
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
