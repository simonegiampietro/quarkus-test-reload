package it.simonegiampietro.quarkusproxy.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class PeopleDB {

    private List<Person> people = new ArrayList<>();

    public void addPerson(Person person) {
        this.people.add(person);
    }

}
