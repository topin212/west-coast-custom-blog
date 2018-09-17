package com.github.topin212.web.sboot.blog.tests.stubs;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PublisherRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Repository
public class PublisherRepositoryStub extends AbstractRepositoryStub<Publisher> implements PublisherRepository {

    public PublisherRepositoryStub() {
        this.storage = new ArrayList<>();

        Publisher p1 = new Publisher();
        p1.setId(1L);
        p1.setName("Petay");
        p1.setPasswordHash("hardtoDecryptpasshash");
        p1.setRegistrationDate(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        p1.setToken("trustMeHeHasOne");
        p1.setRoleId(1L);

        Publisher p2 = new Publisher();
        p2.setId(1L);
        p2.setName("Vasay");
        p2.setPasswordHash("hardtoDecryptpasshash");
        p2.setRegistrationDate(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        p2.setToken("trustMeHeHasOne");
        p2.setRoleId(1L);

        storage.add(p1);
        storage.add(p2);
    }
    @Override
    public Publisher save(Publisher s) {

        boolean contains = storage.stream()
                .anyMatch(publisher -> publisher.getName().toLowerCase() == s.getName().toLowerCase());

        if(contains){

            Publisher publisher = null;

            for (Publisher pub : storage) {
                if(pub.getName().toLowerCase() == s.getName().toLowerCase()){
                    publisher = pub;
                }
            }

            storage.add(publisher);
            return publisher;
        }

        storage.add(s);
        return s;
    }

    public Publisher findByName(String name) {

        for (Publisher publisher : storage) {
            if(publisher.getName().toLowerCase() == name.toLowerCase()){
                return publisher;
            }
        }
        return null;
    }
}
