package com.jjs.Moviebook.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jjs.Moviebook.Entity.User;

@Repository
public interface MovieRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);       // for login

    Optional<User> findByUserName(String userName); // ✅ match entity field
}
