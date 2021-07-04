package it.simonegiampietro.quarkusproxy;

import io.quarkus.runtime.StartupEvent;
import it.simonegiampietro.quarkusproxy.model.PeopleDB;
import it.simonegiampietro.quarkusproxy.proxy.PeopleProxy;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationListener {

    @Inject
    Logger logger;

    @Inject
    PeopleProxy proxy;

    public void onStartup(@Observes StartupEvent event) {
        PeopleDB peopleDB = proxy.get();
        logger.info("peopleDB " + peopleDB);
    }

}
