package com.example.demo.service;

import com.example.demo.Entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceJDBC implements InterfaceConnection {

    @Autowired
    ObjectMapper objectMapper;

    private static final String user = "poletaevvv";
    private static final String password = "Tjed_913";
    private static final String url = "jdbc:postgresql://10.10.10.142:5432/backtosch";


    @Override
    public String select() {

        String result = "";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user,password);
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
            result = objectMapper.writeValueAsString(people);

        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public void insert(String json) {

        List<Person> people = null;
        Connection connection = null;

        try {
            people = objectMapper.readValue(json, new TypeReference<>() {
            });
            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            for(Person gay: people){
                statement.executeUpdate("insert into public.person1(pk, email, firstname, gender, id, lastname) values ("
                        +gay.getPk()+", '"+gay.getEmail()+"','"+gay.getFirstname()+"','"+gay.getGender()+
                        "',"+gay.getId()+",'"+gay.getLastname()+"')");
            }

            connection.close();

        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }

    }

}
