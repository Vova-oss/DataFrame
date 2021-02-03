package com.example.demo.service;

import com.example.demo.Entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceJDBC {

    @Autowired
    ObjectMapper objectMapper;

    private String user = "poletaevvv";
    private String password = "Tjed_913";
    private String url = "jdbc:postgresql://10.10.10.142:5432/backtosch";

    public ServiceJDBC() throws SQLException {
    }

    public String findAll() throws SQLException {

        String result = "";

        Connection connection = DriverManager.getConnection(url,user,password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from public.person1");

        List<Person> people = new ArrayList<>();
        while(resultSet.next()){
            Person current = new Person();
            current.setPk(resultSet.getLong("pk"));
            current.setEmail(resultSet.getString("email"));
            current.setFirstname(resultSet.getString("firstname"));
            current.setGender(resultSet.getString("gender"));
            current.setId(resultSet.getInt("id"));
            current.setLastname(resultSet.getString("lastname"));

            people.add(current);
        }

        connection.close();

        try {
            result = objectMapper.writeValueAsString(people);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;


    }

    public void insert(String json) throws SQLException, JsonProcessingException {

        List<Person> people = objectMapper.readValue(json, new TypeReference<>() {
        });

        Connection connection = DriverManager.getConnection(url,user,password);

        Statement statement = connection.createStatement();
        for(Person gay: people){
            statement.executeUpdate("insert into public.person1(email, firstname, gender, id, lastname) values ("
            +"'"+gay.getEmail()+"','"+gay.getFirstname()+"','"+gay.getGender()+
            "',"+gay.getId()+",'"+gay.getLastname()+"')");
        }

        connection.close();
    }
}
