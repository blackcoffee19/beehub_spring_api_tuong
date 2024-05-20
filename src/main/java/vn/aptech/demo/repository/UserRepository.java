package vn.aptech.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(@Param("email") String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
