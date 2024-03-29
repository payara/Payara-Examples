package org.woehlke.jakartaee.petclinic.pettype.api;


import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Log
@Stateless
public class PetTypeEndpointUtil implements Serializable {

    private static final long serialVersionUID = 7444366391126982311L;

    public PetTypeDto dtoFactory(PetType petType) {
        PetTypeDto dto = new PetTypeDto();
        dto.setId(petType.getId());
        dto.setUuid(petType.getUuid());
        dto.setName(petType.getName());
        return dto;
    }

    public PetTypeListDto dtoListFactory(List<PetType> petTypeList) {
        List<PetTypeDto> dtoList = new ArrayList<>();
        for (PetType petType : petTypeList) {
            PetTypeDto dto = this.dtoFactory(petType);
            dtoList.add(dto);
        }
        return new PetTypeListDto(dtoList);
    }

    public String dtoJsonFactory(PetTypeDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    public String dtoListJsonFactory(PetTypeListDto petTypeListDto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(petTypeListDto);
    }

    public String dtoXmlFactory(PetTypeDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetTypeDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    public String dtoListXmlFactory(PetTypeListDto petTypeListDto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetTypeListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(petTypeListDto, stringWriter);
        return stringWriter.toString();
    }
}
