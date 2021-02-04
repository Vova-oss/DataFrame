package com.example.demo.controllers;

import com.example.demo.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    @Autowired
    Context context;

    @GetMapping("/select")
    public String select() {
        return context.select();
    }

    @PostMapping("/insert")
    public void insert(@RequestBody String json) {
        context.insert(json);
    }

    @GetMapping("/changeConnection")
    public String changeConnection(@RequestParam("name") String name){
        return context.changeConnection(name);
    }

    @GetMapping("/whatName")
    public String whatName(){
        return context.getNameInterface();
    }


}
