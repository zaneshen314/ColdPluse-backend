package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByEmail(String email);
}
