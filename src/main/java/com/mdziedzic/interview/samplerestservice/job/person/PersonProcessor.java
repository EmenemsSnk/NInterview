package com.mdziedzic.interview.samplerestservice.job.person;

import com.mdziedzic.interview.samplerestservice.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@AllArgsConstructor
public class PersonProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person source) {
        return source;
    }
}
