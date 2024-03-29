package org.woehlke.jakartaee.petclinic.visit;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.visit.api.VisitDto;
import org.woehlke.jakartaee.petclinic.visit.api.VisitListDto;
import org.woehlke.jakartaee.petclinic.visit.db.VisitService;
import org.woehlke.jakartaee.petclinic.visit.api.VisitEndpointUtil;

import java.io.Serializable;

/**
 *
 */
@Log
@Path("/visit")
@Stateless
public class VisitEndpoint implements Serializable {

    private static final long serialVersionUID = 7444366391126982311L;

    @EJB
    private VisitService visitService;

    @EJB
    private VisitEndpointUtil visitEndpointUtil;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public VisitListDto getList() {
        log.info("getList");
        return this.visitEndpointUtil.dtoListFactory(visitService.getAll());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VisitDto getEntity(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.visitEndpointUtil.dtoFactory(visitService.findById(id));
    }

    @GET
    @Path("/list+json")
    @Produces(MediaType.APPLICATION_JSON)
    public VisitListDto getListAsJson() {
        log.info("getList");
        return this.visitEndpointUtil.dtoListFactory(visitService.getAll());
    }

    @GET
    @Path("/{id}+json")
    @Produces(MediaType.APPLICATION_JSON)
    public VisitDto getEntityAsJson(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.visitEndpointUtil.dtoFactory(visitService.findById(id));
    }


    @GET
    @Path("/list+xml")
    @Produces(MediaType.APPLICATION_XML)
    public VisitListDto getListAsXml() {
        log.info("getListAsXml");
        return this.visitEndpointUtil.dtoListFactory(visitService.getAll());
    }

    @GET
    @Path("/{id}+xml")
    @Produces(MediaType.APPLICATION_XML)
    public VisitDto getEntityAsXml(@PathParam("id") Long id) {
        log.info("getEntityAsXml");
        return this.visitEndpointUtil.dtoFactory(visitService.findById(id));
    }

}
