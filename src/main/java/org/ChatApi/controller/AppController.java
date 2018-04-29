package org.ChatApi.controller;


import org.ChatApi.entity.ChatUser;
import org.ChatApi.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    ChatUserRepository chatUserRepository;

    @RequestMapping(value = "/findBy", method=RequestMethod.GET)
    public List<ChatUser> findByFullName(@RequestParam(value="fullName") String fullName){
        return chatUserRepository.findByFullName(fullName);
    }
}
