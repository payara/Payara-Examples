package org.woehlke.jakartaee.petclinic.specialty.api;

import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Log
@Stateless
public class SpecialtyEndpointUtil implements Serializable {

    private static final long serialVersionUID = 607664665910620584L;

    public SpecialtyDto dtoFactory(Specialty specialty) {
        SpecialtyDto dto = new SpecialtyDto();
        dto.setId(specialty.getId());
        dto.setUuid(specialty.getUuid());
        dto.setName(specialty.getName());
        return dto;
    }

    public SpecialtyListDto dtoListFactory(List<Specialty> specialtyList) {
        List<SpecialtyDto> dtoList = new ArrayList<>();
        for (Specialty petType : specialtyList) {
            SpecialtyDto dto = this.dtoFactory(petType);
            dtoList.add(dto);
        }
        return new SpecialtyListDto(dtoList);
    }

    public String dtoJsonFactory(SpecialtyDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    public String dtoListJsonFactory(SpecialtyListDto dtoList) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dtoList);
    }

    public String dtoXmlFactory(SpecialtyDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(SpecialtyDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    public String dtoListXmlFactory(SpecialtyListDto dtoList) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(SpecialtyListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dtoList, stringWriter);
        return stringWriter.toString();
    }

}
