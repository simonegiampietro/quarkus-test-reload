package it.simonegiampietro.quarkusproxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.simonegiampietro.quarkusproxy.model.Person;

@Path("/person")
public class PersonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person hello() {
        Person person = Person.builder().name("Simone").age(25).build();
        return person;
    }
}