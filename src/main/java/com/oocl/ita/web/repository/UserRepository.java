package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
