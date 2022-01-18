package com.taniltekdemir.n11.dischargeSystem.user.repository;

import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByUsername(String username);
}
