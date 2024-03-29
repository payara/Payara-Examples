package org.woehlke.jakartaee.petclinic.owner;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBException;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.api.OwnerDto;
import org.woehlke.jakartaee.petclinic.owner.api.OwnerListDto;
import org.woehlke.jakartaee.petclinic.owner.db.OwnerService;
import org.woehlke.jakartaee.petclinic.owner.api.OwnerEndpointUtil;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pet.api.PetEndpointUtil;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Log
@Path("/owner")
@Stateless
public class OwnerEndpoint implements Serializable {

    private static final long serialVersionUID = 532726561254887897L;

    @EJB
    private OwnerService ownerService;

    @EJB
    private OwnerEndpointUtil ownerEndpointUtil;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public OwnerListDto getList() {
        log.info("getList");
        return ownerEndpointUtil.dtoListFactory(ownerService.getAll());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OwnerDto getEntity(@PathParam("id") Long id) {
        log.info("getEntity");
        Owner owner = ownerService.findById(id);
        OwnerDto dto = ownerEndpointUtil.dtoFactory(owner);
        return dto;
    }

    @GET
    @Path("/list+json")
    @Produces(MediaType.APPLICATION_JSON)
    public OwnerListDto getListAsJson() {
        log.info("getList");
        return ownerEndpointUtil.dtoListFactory(ownerService.getAll());
    }

    @GET
    @Path("/{id}+json")
    @Produces(MediaType.APPLICATION_JSON)
    public OwnerDto getEntityAsJson(@PathParam("id") Long id) {
        log.info("getEntity");
        return ownerEndpointUtil.dtoFactory(ownerService.findById(id));
    }

    @GET
    @Path("/list+xml")
    @Produces(MediaType.APPLICATION_XML)
    public OwnerListDto getListAsXml() throws JAXBException {
        log.info("getListAsXml");
        return ownerEndpointUtil.dtoListFactory(ownerService.getAll());
    }

    @GET
    @Path("/{id}+xml")
    @Produces(MediaType.APPLICATION_XML)
    public OwnerDto getEntityAsXml(@PathParam("id") Long id) throws JAXBException {
        log.info("getEntityAsXml");
        return ownerEndpointUtil.dtoFactory(ownerService.findById(id));
    }

}
