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
@RequestMapping("/api/employee")
public class AppController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> get(@RequestParam(value="fullName") String fullName){
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


    @PostMapping
    public ResponseEntity<?> post(@RequestBody Employee employee){
        if (employee.getFullName() == null) return ResponseEntity.unprocessableEntity().build();

        Employee newEmployee = employeeRepository.save(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Employee employee){
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Optional<Employee> chatUserForRemove = employeeRepository.findById(id);
        if (chatUserForRemove.isPresent()){
            employeeRepository.delete(chatUserForRemove.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
