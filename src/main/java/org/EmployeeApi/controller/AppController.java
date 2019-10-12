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
@RequestMapping("/api")
public class AppController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "/findBy")
    public List<Employee> findBy(@RequestParam(value="fullName") String fullName){
        return employeeRepository.findByFullName(fullName);
    }

    @GetMapping(value = "/findAll")
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/init")
    public void init(){
        Stream.of(new Employee("Alexander"), new Employee("Nikita"), new Employee("Alesya"))
                .forEach(chatUser -> {
                    employeeRepository.save(chatUser);
                });
    }


    @PostMapping(value = "/addChatUser")
    public ResponseEntity<?> createChatUser(@RequestBody Employee employee){
        if (employee.getFullName() == null) return ResponseEntity.unprocessableEntity().build();

        Employee newEmployee = employeeRepository.save(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/changeChatUser/{id}")
    public ResponseEntity<?> changeChatUser(@PathVariable long id, @RequestBody Employee employee){
        if (employee.getFullName() == null) return ResponseEntity.unprocessableEntity().build();

        Optional<Employee> chatUserForChange = employeeRepository.findById(id);
        if (chatUserForChange.isPresent()){
            chatUserForChange.get().setFullName(employee.getFullName());
            employeeRepository.save(chatUserForChange.get());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(chatUserForChange.get().getId()).toUri();

            return ResponseEntity.created(location).build();
        }else{
            return ResponseEntity.notFound().build();

        }

    }

    @DeleteMapping(value = "/deleteChatUser/{id}")
    public ResponseEntity<?> deleteChatUser(@PathVariable long id){
        Optional<Employee> chatUserForRemove = employeeRepository.findById(id);
        if (chatUserForRemove.isPresent()){
            employeeRepository.delete(chatUserForRemove.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
