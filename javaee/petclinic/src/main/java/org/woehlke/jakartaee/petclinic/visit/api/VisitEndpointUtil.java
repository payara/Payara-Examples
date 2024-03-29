package org.woehlke.jakartaee.petclinic.visit.api;


import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Log
@Stateless
public class VisitEndpointUtil implements Serializable {

    private static final long serialVersionUID = 7444366391126982311L;

    public VisitDto dtoFactory(Visit e) {
        VisitDto dto = new VisitDto();
        dto.setId(e.getId());
        dto.setUuid(e.getUuid());
        dto.setDate(e.getDate());
        dto.setDescription(e.getDescription());
        return dto;
    }

    public VisitListDto dtoListFactory(List<Visit> eList) {
        List<VisitDto> dtoList = new ArrayList<>();
        for (Visit e : eList) {
            VisitDto dto = this.dtoFactory(e);
            dtoList.add(dto);
        }
        return new VisitListDto(dtoList);
    }

    public String dtoJsonFactory(VisitDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    public String dtoListJsonFactory(VisitListDto listDto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(listDto);
    }

    public String dtoXmlFactory(VisitDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(VisitDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    public String dtoListXmlFactory(VisitListDto listDto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(VisitListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(listDto, stringWriter);
        return stringWriter.toString();
    }
}
