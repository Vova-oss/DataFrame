package com.example.demo;

import com.example.demo.service.InterfaceConnection;
import com.example.demo.service.ServiceHibernate;
import com.example.demo.service.ServiceJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Context {
    @Autowired
    ServiceJDBC serviceJDBC;
    @Autowired
    ServiceHibernate serviceHibernate;

    @Autowired
    @Qualifier("serviceJDBC")
    InterfaceConnection interfaceConnection;

    public void setInterfaceConnection(InterfaceConnection interfaceConnection) {
        this.interfaceConnection = interfaceConnection;
    }

    public String select() {
        return interfaceConnection.select();
    }

    public void insert(String json) {
        interfaceConnection.insert(json);
    }

    public String changeConnection(String name)  {
        String result = "ok";

        switch (name){

            case "Hibernate": setInterfaceConnection(serviceHibernate);
            break;
            case "JDBC": setInterfaceConnection(serviceJDBC);
            break;
            default: result = "This is an invalid name";
        }
        return result;
    }

    public String getNameInterface(){
        return interfaceConnection.getClass().getName();
    }

}
