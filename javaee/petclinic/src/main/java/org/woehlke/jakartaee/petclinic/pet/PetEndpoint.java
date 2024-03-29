package org.woehlke.jakartaee.petclinic.pet;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBException;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.api.PetListDto;
import org.woehlke.jakartaee.petclinic.pet.db.PetService;
import org.woehlke.jakartaee.petclinic.pet.api.PetDto;
import org.woehlke.jakartaee.petclinic.pet.api.PetEndpointUtil;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Log
@Path("/pet")
@Stateless
public class PetEndpoint implements Serializable {

    private static final long serialVersionUID = 6505290301528514574L;

    @EJB
    private PetService petService;

    @EJB
    private PetEndpointUtil petEndpointUtil;

    private String dtoJsonFactory(PetDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public PetListDto getList() {
        log.info("------------------------------------------------------------");
        log.info("getList");
        log.info("------------------------------------------------------------");
        List<Pet> petList = petService.getAll();
        PetListDto dto = this.petEndpointUtil.dtoListFactory(petList);
        log.info(dto.toString());
        log.info("------------------------------------------------------------");
        return dto;
    }

    @GET
    @Path("/list+json")
    @Produces(MediaType.APPLICATION_JSON)
    public PetListDto getListAsJson() {
        log.info("------------------------------------------------------------");
        log.info("getList");
        log.info("------------------------------------------------------------");
        List<Pet> petList = petService.getAll();
        PetListDto dto = this.petEndpointUtil.dtoListFactory(petList);
        log.info(dto.toString());
        log.info("------------------------------------------------------------");
        return dto;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PetDto getEntity(@PathParam("id") Long id) {
        log.info("------------------------------------------------------------");
        log.info("getEntity");
        log.info("------------------------------------------------------------");
        return this.petEndpointUtil.dtoFactory(petService.findById(id));
    }

    @GET
    @Path("/{id}+json")
    @Produces(MediaType.APPLICATION_JSON)
    public PetDto getEntityAsJson(@PathParam("id") Long id) {
        log.info("------------------------------------------------------------");
        log.info("getEntity");
        log.info("------------------------------------------------------------");
        PetDto oPetDto = this.petEndpointUtil.dtoFactory(petService.findById(id));
        log.info("------------------------------------------------------------");
        log.info(oPetDto.toString());
        log.info("------------------------------------------------------------");
        return oPetDto;
    }

    @GET
    @Path("/list+xml")
    @Produces(MediaType.APPLICATION_XML)
    public PetListDto getListAsXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info("getListAsXml");
        log.info("------------------------------------------------------------");
        List<Pet> petList = petService.getAll();
        log.info("------------------------------------------------------------");
        PetListDto dto = this.petEndpointUtil.dtoListFactory(petList);
        log.info("------------------------------------------------------------");
        log.info(dto.toString());
        log.info("------------------------------------------------------------");
        return dto;
    }

    @GET
    @Path("/{id}+xml")
    @Produces(MediaType.APPLICATION_XML)
    public PetDto getEntityAsXml(@PathParam("id") Long id) throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info("getEntityAsXml");
        log.info("------------------------------------------------------------");
        PetDto oPetDto = this.petEndpointUtil.dtoFactory(petService.findById(id));
        log.info("------------------------------------------------------------");
        log.info(oPetDto.toString());
        log.info("------------------------------------------------------------");
        return oPetDto;
    }

}
