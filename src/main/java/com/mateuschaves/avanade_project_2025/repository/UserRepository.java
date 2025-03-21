package com.mateuschaves.avanade_project_2025.repository;

import com.mateuschaves.avanade_project_2025.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    boolean existsByAccountNumber(String accountNumber);

}
