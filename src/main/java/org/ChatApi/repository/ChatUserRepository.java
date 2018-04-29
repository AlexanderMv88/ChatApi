package org.ChatApi.repository;

import org.ChatApi.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.CharBuffer;
import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findByFullName(String fullName);
}
