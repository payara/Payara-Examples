package org.woehlke.jakartaee.petclinic.vet;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.vet.api.VetDto;
import org.woehlke.jakartaee.petclinic.vet.api.VetListDto;
import org.woehlke.jakartaee.petclinic.vet.db.VetService;
import org.woehlke.jakartaee.petclinic.vet.api.VetEndpointUtil;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 05.01.14
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */
@Log
@Path("/vet")
@Stateless
public class VetEndpoint implements Serializable {

    private static final long serialVersionUID = 607664665910620584L;

    @EJB
    private VetService vetService;

    @EJB
    private VetEndpointUtil vetEndpointUtil;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public VetListDto getList() {
        log.info("getList");
        return this.vetEndpointUtil.dtoListFactory(vetService.getAll());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VetDto getEntity(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.vetEndpointUtil.dtoFactory(vetService.findById(id));
    }

    @GET
    @Path("/list+json")
    @Produces(MediaType.APPLICATION_JSON)
    public VetListDto getListAsJson() {
        log.info("getList");
        return this.vetEndpointUtil.dtoListFactory(vetService.getAll());
    }

    @GET
    @Path("/{id}+json")
    @Produces(MediaType.APPLICATION_JSON)
    public VetDto getEntityAsJson(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.vetEndpointUtil.dtoFactory(vetService.findById(id));
    }

    @GET
    @Path("/list+xml")
    @Produces(MediaType.APPLICATION_XML)
    public VetListDto getListAsXml() {
        log.info("getListAsXml");
        return this.vetEndpointUtil.dtoListFactory(vetService.getAll());
    }

    @GET
    @Path("/{id}+xml")
    @Produces(MediaType.APPLICATION_XML)
    public VetDto getEntityAsXml(@PathParam("id") Long id) {
        log.info("getEntityAsXml");
        return this.vetEndpointUtil.dtoFactory(vetService.findById(id));
    }

}
