package org.EmployeeApi.repository;

import org.EmployeeApi.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findByFullName(String fullName);


}
