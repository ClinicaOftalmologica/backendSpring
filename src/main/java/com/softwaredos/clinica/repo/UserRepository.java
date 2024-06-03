package com.softwaredos.clinica.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.Model.User.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);

}
