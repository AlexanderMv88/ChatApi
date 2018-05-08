package org.EmployeeApi;

import org.EmployeeApi.entity.ChatUser;
import org.EmployeeApi.repository.ChatUserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChatUserRepositoryTests {
    @Autowired
    ChatUserRepository chatUserRepository;

    @Test
    public void test1JpaCreate() {
        //Insert
        Stream.of(new ChatUser("Alexander"), new ChatUser("Nikita"), new ChatUser("Alesya"))
                .forEach(chatUser -> {
                    chatUserRepository.save(chatUser);
                });
        List<ChatUser> chatUsers = chatUserRepository.findAll();
        assertThat(chatUsers != null).isTrue();
        assertThat(chatUsers.size() == 3).isTrue();
    }

    @Test
    public void test2JpaFind() {
        //Select
        ChatUser chatUser = chatUserRepository.findByFullName("Alexander").get(0);
        assertThat(chatUser.getFullName()).isEqualTo("Alexander");
    }


    @Test
    public void test3JpaChange() {
        //Update
        ChatUser chatUser = chatUserRepository.findByFullName("Alexander").get(0);
        chatUser.setFullName("AlexanderMv");
        chatUserRepository.save(chatUser);
        ChatUser changedChatUser = chatUserRepository.findByFullName("AlexanderMv").get(0);
        assertThat(changedChatUser).isEqualTo(chatUser);
    }

    @Test
    public void test4JpaDelete() {
        List<ChatUser> chatUsersForDelete = chatUserRepository.findAll();
        chatUserRepository.deleteAll(chatUsersForDelete);
        assertThat(chatUserRepository.findAll().size() == 0).isTrue();
    }
}
