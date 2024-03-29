package org.woehlke.jakartaee.petclinic.pet.api;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pet.db.PetService;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeDto;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeEndpointUtil;
import org.woehlke.jakartaee.petclinic.visit.Visit;
import org.woehlke.jakartaee.petclinic.visit.api.VisitEndpointUtil;
import org.woehlke.jakartaee.petclinic.visit.api.VisitListDto;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


@Log
@Stateless
public class PetEndpointUtil implements Serializable {

    private static final long serialVersionUID = 7444366391126982311L;

    @EJB
    private PetTypeEndpointUtil petTypeEndpointUtil;

    @EJB
    private VisitEndpointUtil visitEndpointUtil;

    @EJB
    private PetService petService;

    public PetDto dtoFactory(Pet pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setUuid(pet.getUuid());
        dto.setBirthDate(pet.getBirthDate());
        dto.setName(pet.getName());
        PetTypeDto oPetTypeDto =this.petTypeEndpointUtil.dtoFactory(pet.getType());
        dto.setPetType(oPetTypeDto);
        List<Visit> visitList = petService.getVisits(pet);
        VisitListDto oVisitListDto =this.visitEndpointUtil.dtoListFactory(visitList);
        dto.setVisitList(oVisitListDto);
        return dto;
    }

    public PetListDto dtoListFactory(List<Pet> petList) {
        List<PetDto> dtoList = new ArrayList<>();
        for (Pet pet : petList) {
            PetDto dto = this.dtoFactory(pet);
            dtoList.add(dto);
        }
        return new PetListDto(dtoList);
    }

    public String dtoListJsonFactory(PetListDto listDto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(listDto);
    }

    public String dtoXmlFactory(PetDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    public String dtoListXmlFactory(PetListDto listDto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(listDto, stringWriter);
        return stringWriter.toString();
    }
}
