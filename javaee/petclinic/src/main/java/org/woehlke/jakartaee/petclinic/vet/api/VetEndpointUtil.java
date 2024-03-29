package org.woehlke.jakartaee.petclinic.vet.api;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.specialty.api.SpecialtyEndpointUtil;
import org.woehlke.jakartaee.petclinic.specialty.api.SpecialtyListDto;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log
@Stateless
public class VetEndpointUtil implements Serializable {

    private static final long serialVersionUID = 607664665910620584L;

    @EJB
    private SpecialtyEndpointUtil specialtyEndpointUtil;

    public VetDto dtoFactory(Vet vet) {
        VetDto dto = new VetDto();
        dto.setId(vet.getId());
        dto.setUuid(vet.getUuid());
        dto.setFirstName(vet.getFirstName());
        dto.setLastName(vet.getLastName());
        Set<Specialty> specialties = vet.getSpecialties();
        List<Specialty> specialtyList = new ArrayList<>();
        for(Specialty specialty:specialties ){
            specialtyList.add(specialty);
        }
        SpecialtyListDto specialtyDtoList = this.specialtyEndpointUtil.dtoListFactory(specialtyList);
        dto.setSpecialtyList(specialtyDtoList);
        return dto;
    }

    public VetListDto dtoListFactory(List<Vet> vetList) {
        List<VetDto> dtoList = new ArrayList<>();
        for (Vet vet : vetList) {
            VetDto dto = this.dtoFactory(vet);
            dtoList.add(dto);
        }
        return new VetListDto(dtoList);
    }

    public String dtoJsonFactory(VetDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    public String dtoListJsonFactory(VetListDto vetListDto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(vetListDto);
    }

    public String dtoXmlFactory(VetDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(VetDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    public String dtoListXmlFactory(VetListDto vetListDto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(VetListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(vetListDto, stringWriter);
        return stringWriter.toString();
    }
}
