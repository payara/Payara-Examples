package org.woehlke.jakartaee.petclinic.it.api;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.woehlke.jakartaee.petclinic.deployments.Deployments;
import org.woehlke.jakartaee.petclinic.owner.api.OwnerDto;
import org.woehlke.jakartaee.petclinic.owner.api.OwnerListDto;
import org.woehlke.jakartaee.petclinic.pet.api.PetDto;
import org.woehlke.jakartaee.petclinic.pet.api.PetListDto;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeDto;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeListDto;
import org.woehlke.jakartaee.petclinic.specialty.api.SpecialtyDto;
import org.woehlke.jakartaee.petclinic.specialty.api.SpecialtyListDto;
import org.woehlke.jakartaee.petclinic.vet.api.VetDto;
import org.woehlke.jakartaee.petclinic.vet.api.VetListDto;
import org.woehlke.jakartaee.petclinic.visit.api.VisitDto;
import org.woehlke.jakartaee.petclinic.visit.api.VisitListDto;

import java.io.StringReader;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@ExtendWith(ArquillianExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestEndpointIT {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment();
    }

    @ArquillianResource
    private URL base;

    private Client client;

    @BeforeEach
    public void setup() {
        log.info("------------------------------------------------------------");
        log.info(" call BeforeAll");
        log.info("------------------------------------------------------------");
        this.client = ClientBuilder.newClient();
    }

    @AfterEach
    public void teardown() {
        log.info("------------------------------------------------------------");
        log.info(" call AfterAll");
        log.info("------------------------------------------------------------");
        if (this.client != null) {
            this.client.close();
        }
    }

    private void assertHttpStatus200(Response response){
        boolean ok = Response.Status.OK.getStatusCode()==response.getStatus();
        assertThat(ok);
    }

    @Test
    @InSequence(1)
    public void testGetSpecialtyListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetSpecialtyListJson:  ");
        String endpoint = base + "/rest" + "/specialty" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        JsonbConfig config = new JsonbConfig().setProperty(org.eclipse.yasson.YassonConfig.ZERO_TIME_PARSE_DEFAULTING,true);
        Jsonb jsonb = JsonbBuilder.create(config);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String json = response.readEntity(String.class);
        SpecialtyListDto petTypeListDto = jsonb.fromJson(json, SpecialtyListDto.class);
        for(SpecialtyDto dto: petTypeListDto.getSpecialty()){
            log.info("fetched dto: "+dto.toString());
        }
        json = "\n\n" + json +  "\n\n";
        log.info(json);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetSpecialtyListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(2)
    public void testGetSpecialtyListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetSpecialtyListXml:  ");
        String endpoint = base + "/rest" + "/specialty" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String xml = response.readEntity(String.class);
        JAXBContext jc = JAXBContext.newInstance(SpecialtyListDto.class);
        Unmarshaller m = jc.createUnmarshaller();
        StringReader r  = new StringReader(xml);
        SpecialtyListDto o = (SpecialtyListDto) m.unmarshal(r);
        for(SpecialtyDto dto: o.getSpecialty()){
            log.info("fetched dto: "+dto.toString());
        }
        xml = "\n\n" + xml +  "\n\n";
        log.info(xml);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetSpecialtyListXml: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(3)
    public void testGetPetTypeListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetPetTypeListJson:  ");
        String endpoint = base + "/rest" + "/petType" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Jsonb jsonb = JsonbBuilder.create();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String json = response.readEntity(String.class);
        PetTypeListDto petTypeListDto = jsonb.fromJson(json, PetTypeListDto.class);
        for(PetTypeDto dto: petTypeListDto.getPetType()){
            log.info("fetched dto: "+dto.toString());
        }
        json = "\n\n" + json +  "\n\n";
        log.info(json);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetPetTypeListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(4)
    public void testGetPetTypeListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetPetTypeListXml:  ");
        String endpoint = base + "/rest" + "/petType" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String xml = response.readEntity(String.class);
            JAXBContext jc = JAXBContext.   newInstance(PetTypeListDto.class);
            Unmarshaller m = jc.createUnmarshaller();
            StringReader r  = new StringReader(xml);
            PetTypeListDto o = (PetTypeListDto) m.unmarshal(r);
        for(PetTypeDto dto: o.getPetType()){
            log.info("fetched dto: "+dto.toString());
        }
        xml = "\n\n" + xml +  "\n\n";
        log.info(xml);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetPetTypeListXml: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(5)
    public void testGetVetListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetVetListJson:  ");
        String endpoint = base + "/rest" + "/vet" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Jsonb jsonb = JsonbBuilder.create();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String json = response.readEntity(String.class);
        VetListDto petTypeListDto = jsonb.fromJson(json, VetListDto.class);
        for(VetDto dto: petTypeListDto.getVetList()){
            log.info("fetched dto: "+dto.toString());
        }
        json = "\n\n" + json +  "\n\n";
        log.info(json);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetVetListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(6)
    public void testGetVetListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetVetListXml:  ");
        String endpoint = base + "/rest" + "/vet" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String xml = response.readEntity(String.class);
        JAXBContext jc = JAXBContext.newInstance(VetListDto.class);
        Unmarshaller m = jc.createUnmarshaller();
        StringReader r  = new StringReader(xml);
        VetListDto o = (VetListDto) m.unmarshal(r);
        for(VetDto dto: o.getVetList()){
            log.info("fetched dto: "+dto.toString());
        }
        xml = "\n\n" + xml +  "\n\n";
        log.info(xml);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetVetListXml: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(7)
    public void testGetVisitListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetVisitListJson:  ");
        String endpoint = base + "/rest" + "/visit" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        JsonbConfig config = new JsonbConfig().setProperty(org.eclipse.yasson.YassonConfig.ZERO_TIME_PARSE_DEFAULTING,true);
        Jsonb jsonb = JsonbBuilder.create(config);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String json = response.readEntity(String.class);
        VisitListDto visitListDto = jsonb.fromJson(json, VisitListDto.class);
        for(VisitDto dto: visitListDto.getVisit()){
            log.info("fetched dto: "+dto.toString());
        }
        json = "\n\n" + json +  "\n\n";
        log.info(json);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetVisitListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(8)
    public void testGetVisitListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetVisitListXml:  ");
        String endpoint =  base + "/rest" + "/visit" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        String xml = response.readEntity(String.class);
        JAXBContext jc = JAXBContext.newInstance(VisitListDto.class);
        Unmarshaller m = jc.createUnmarshaller();
        StringReader r  = new StringReader(xml);
        VisitListDto o = (VisitListDto) m.unmarshal(r);
        for(VisitDto dto: o.getVisit()){
            log.info("fetched dto: "+dto.toString());
        }
        xml = "\n\n" + xml +  "\n\n";
        log.info(xml);
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetVisitListXml: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(9)
    public void testGetPetListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetPetListJson:  ");
        String endpoint = base + "/rest" + "/pet" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        JsonbConfig config = new JsonbConfig().setProperty(org.eclipse.yasson.YassonConfig.ZERO_TIME_PARSE_DEFAULTING,true);
        Jsonb jsonb = JsonbBuilder.create(config);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        log.info("------------------------------------------------------------");
        assertHttpStatus200(response);
        log.info("------------------------------------------------------------");
        String json = response.readEntity(String.class);
        log.info("------------------------------------------------------------");
        /*
        PetListDto petTypeListDto = jsonb.fromJson(json, PetListDto.class);
        for(PetDto dto: petTypeListDto.getPet()){
          log.info("fetched dto: "+dto.toString());
        }
        */
        json = "\n\n" + json +  "\n\n";
        log.info("============================================================");
        log.info(json);
        log.info("============================================================");
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetPetListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(10)
    public void testGetPetListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetPetListXml:  ");
        String endpoint = base + "/rest" + "/pet" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        log.info("------------------------------------------------------------");
        assertHttpStatus200(response);
        log.info("------------------------------------------------------------");
        String xml = response.readEntity(String.class);
        log.info("------------------------------------------------------------");
        JAXBContext jc = JAXBContext.newInstance(PetListDto.class);
        Unmarshaller m = jc.createUnmarshaller();
        StringReader r  = new StringReader(xml);
        PetListDto o = (PetListDto) m.unmarshal(r);
        log.info("------------------------------------------------------------");
        for(PetDto dto: o.getPet()){
            log.info("fetched dto: "+dto.toString());
        }
        log.info("------------------------------------------------------------");
        xml = "\n\n" + xml +  "\n\n";
        log.info(xml);
        log.info("------------------------------------------------------------");
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetPetListXml: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(10)
    public void testGetOwnerListJson() {
        log.info("------------------------------------------------------------");
        log.info(" testGetOwnerListJson:  ");
        String endpoint = base + "/rest" + "/owner" + "/list";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        JsonbConfig config = new JsonbConfig().setProperty(org.eclipse.yasson.YassonConfig.ZERO_TIME_PARSE_DEFAULTING,true);
        Jsonb jsonb = JsonbBuilder.create(config);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        log.info("------------------------------------------------------------");
        assertHttpStatus200(response);
        log.info("------------------------------------------------------------");
        String json = response.readEntity(String.class);
        log.info("------------------------------------------------------------");
        OwnerListDto ownerListDto = jsonb.fromJson(json, OwnerListDto.class);
        log.info("------------------------------------------------------------");
        for(OwnerDto dto: ownerListDto.getOwner()){
            log.info("fetched dto: "+dto.toString());
        }
        json = "\n\n" + json +  "\n\n";
        log.info("------------------------------------------------------------");
        log.info(json);
        log.info("------------------------------------------------------------");
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetOwnerListJson: OK ");
        log.info("------------------------------------------------------------");
    }

    @Test
    @InSequence(11)
    public void testGetOwnerListXml() throws JAXBException {
        log.info("------------------------------------------------------------");
        log.info(" testGetOwnerListXml:  ");
        String endpoint = base + "/rest" + "/owner" + "/list+xml";
        log.info("------------------------------------------------------------");
        log.info(" endpoint URL: " + endpoint);
        log.info("------------------------------------------------------------");
        WebTarget target = client.target(endpoint);
        Response response = target.request().get();
        assertHttpStatus200(response);
        log.info("------------------------------------------------------------");
        String xml = response.readEntity(String.class);
        log.info("------------------------------------------------------------");
        JAXBContext jc = JAXBContext.newInstance(OwnerListDto.class);
        Unmarshaller m = jc.createUnmarshaller();
        StringReader r  = new StringReader(xml);
        OwnerListDto o = (OwnerListDto) m.unmarshal(r);
        for(OwnerDto dto: o.getOwner()){
            log.info("fetched dto: "+dto.toString());
        }
        xml = "\n\n" + xml +  "\n\n";
        log.info("------------------------------------------------------------");
        log.info(xml);
        log.info("------------------------------------------------------------");
        response.close();
        client.close();
        log.info("------------------------------------------------------------");
        log.info(" testGetOwnerListXml: OK ");
        log.info("------------------------------------------------------------");
    }

}
