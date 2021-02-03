package com.example.demo.controllers;

import com.example.demo.Entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.ServiceJDBC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ServiceJDBC serviceJDBC;

    @Autowired
    private PersonRepository repository;

    @GetMapping("/getAllByH")
    public String selectByHibernate() throws JsonProcessingException {

       return objectMapper.writeValueAsString((List<Person>) repository.findAll());
    }

    @PostMapping("/insertByH")
    public void insertByHibernate(@RequestBody String json) throws JsonProcessingException {
        repository.saveAll(objectMapper.readValue(json, new TypeReference<List<Person>>() {}));
    }

    @GetMapping("/getAll")
    public String select() throws SQLException {
        return serviceJDBC.findAll();
    }

    @PostMapping("/insert")
    public void insert(@RequestBody String json) throws JsonProcessingException, SQLException {
        serviceJDBC.insert(json);
    }

}
