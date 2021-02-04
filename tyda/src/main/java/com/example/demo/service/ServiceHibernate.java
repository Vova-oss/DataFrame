package com.example.demo.service;

import com.example.demo.Entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceHibernate implements InterfaceConnection {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PersonRepository personRepository;

    @Override
    public String select() {
        String result = "";
        try {
            result = objectMapper.writeValueAsString(personRepository.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void insert(String json)  {
        List<Person> people = null;
        try {
            people = objectMapper.readValue(json, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        personRepository.saveAll(people);
    }
}
