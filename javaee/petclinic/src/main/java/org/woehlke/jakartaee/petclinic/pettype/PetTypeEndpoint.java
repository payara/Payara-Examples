package org.woehlke.jakartaee.petclinic.pettype;

import jakarta.ejb.EJB;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeDto;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeEndpointUtil;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeListDto;
import org.woehlke.jakartaee.petclinic.pettype.db.PetTypeService;

import java.io.Serializable;

@Log
@Path("/petType")
@Stateless
public class PetTypeEndpoint implements Serializable {

    private static final long serialVersionUID = -105453087511255998L;

    @EJB
    private PetTypeService petTypeService;

    @EJB
    private PetTypeEndpointUtil petTypeEndpointUtil;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public PetTypeListDto getList() {
        log.info("getList");
        return this.petTypeEndpointUtil.dtoListFactory(petTypeService.getAll());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PetTypeDto getEntity(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.petTypeEndpointUtil.dtoFactory(petTypeService.findById(id));
    }

    @GET
    @Path("/list+json")
    @Produces(MediaType.APPLICATION_JSON)
    public PetTypeListDto getListAsJson() {
        log.info("getList");
        return this.petTypeEndpointUtil.dtoListFactory(petTypeService.getAll());
    }

    @GET
    @Path("/{id}+json")
    @Produces(MediaType.APPLICATION_JSON)
    public PetTypeDto getEntityAsJson(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.petTypeEndpointUtil.dtoFactory(petTypeService.findById(id));
    }

    @GET
    @Path("/list+xml")
    @Produces(MediaType.APPLICATION_XML)
    public PetTypeListDto getListAsXml() {
        log.info("getListAsXml");
        return this.petTypeEndpointUtil.dtoListFactory(petTypeService.getAll());
    }

    @GET
    @Path("/{id}+xml")
    @Produces(MediaType.APPLICATION_XML)
    public PetTypeDto getEntityAsXml(@PathParam("id") Long id) {
        log.info("getEntityAsXml");
        return this.petTypeEndpointUtil.dtoFactory(petTypeService.findById(id));
    }

}
