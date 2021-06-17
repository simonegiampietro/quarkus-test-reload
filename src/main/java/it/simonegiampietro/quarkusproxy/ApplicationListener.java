package it.simonegiampietro.quarkusproxy;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import it.simonegiampietro.quarkusproxy.model.PeopleDB;
import it.simonegiampietro.quarkusproxy.proxy.PeopleProxy;

@ApplicationScoped
public class ApplicationListener {

    @Inject
    PeopleProxy proxy;
    
    public void onStartup(@Observes StartupEvent event) {
        PeopleDB peopleDB = proxy.get();
        peopleDB.toString();
    }

}
