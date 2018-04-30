package org.ChatApi.controller;


import org.ChatApi.entity.ChatUser;
import org.ChatApi.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    ChatUserRepository chatUserRepository;

    @RequestMapping(value = "/findBy", method=RequestMethod.GET)
    public List<ChatUser> findByFullName(@RequestParam(value="fullName") String fullName){
        return chatUserRepository.findByFullName(fullName);
    }

    @RequestMapping(value = "/findAll", method=RequestMethod.GET)
    public List<ChatUser> findAll(){
        return chatUserRepository.findAll();
    }

    @RequestMapping(value = "/init", method=RequestMethod.GET)
    public void init(){
        Stream.of(new ChatUser("Alexander"), new ChatUser("Nikita"), new ChatUser("Alesya"))
                .forEach(chatUser -> {
                    chatUserRepository.save(chatUser);
                });
    }


    @RequestMapping(value = "/addChatUser", method = RequestMethod.POST)
    public ResponseEntity<?> createChatUser(@RequestBody ChatUser chatUser){
        if (chatUser.getFullName() == null) return ResponseEntity.unprocessableEntity().build();

        ChatUser newChatUser = chatUserRepository.save(chatUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newChatUser)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/changeChatUser/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> changeChatUser(@PathVariable long id, @RequestBody ChatUser chatUser){
        if (chatUser.getFullName() == null) return ResponseEntity.unprocessableEntity().build();

        Optional<ChatUser> chatUserForChange = chatUserRepository.findById(id);
        if (chatUserForChange.isPresent()){
            chatUserForChange.get().setFullName(chatUser.getFullName());
            chatUserRepository.save(chatUserForChange.get());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(chatUserForChange.get().getId()).toUri();

            return ResponseEntity.created(location).build();
        }else{
            return ResponseEntity.notFound().build();

        }

    }

    @RequestMapping(value = "/deleteChatUser/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteChatUser(@PathVariable long id){
        Optional<ChatUser> chatUserForRemove = chatUserRepository.findById(id);
        if (chatUserForRemove.isPresent()){
            chatUserRepository.delete(chatUserForRemove.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
