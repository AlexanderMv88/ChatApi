package org.EmployeeApi.controller;


import org.EmployeeApi.entity.Employee;
import org.EmployeeApi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@RestController
@RequestMapping("/users")
public class SecurityExperimentsController {

    @GetMapping
    public String get(){
        return "this page for all users";
    }

    @GetMapping(value = "/admin")
    public String adminPage(){
        return "this page for admin only";
    }


}
