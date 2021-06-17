package it.simonegiampietro.quarkusproxy.proxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.simonegiampietro.quarkusproxy.model.PeopleDB;
import it.simonegiampietro.quarkusproxy.persistence.DaoProtostuff;

@ApplicationScoped
public class PeopleProxy {

    private static final String PEOPLE_DB_PATH = "people.path";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ConfigProperty(name = PEOPLE_DB_PATH)
    String peopleDBPath;

    @Inject
    DaoProtostuff daoProtostuff;

    private PeopleDB db;
    private Date lastModifiedDate = new Date(0);

    public PeopleDB get() {
        checkForUpdates();
        return db;
    }

    private void checkForUpdates() {
        // File dbFile = new File(peopleDBPath + "people.json");
        File dbFile = new File(peopleDBPath + "people.protostuff");
        try {
            BasicFileAttributes readAttributes = Files.readAttributes(dbFile.toPath(), BasicFileAttributes.class);
            if (lastModifiedDate.getTime() < readAttributes.lastModifiedTime().toMillis()) {
                // db = MAPPER.readValue(dbFile, PeopleDB.class);
                db = daoProtostuff.load(dbFile.getAbsolutePath(), PeopleDB.class);
                System.out.println("DB loaded");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
