package vn.aptech.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(@Param("email") String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	@Query(value = "SELECT *  FROM users u "
			+ " WHERE u.id IN (SELECT ru.user2_id FROM relationship_users  ru WHERE ru.user1_id = ?1 AND ru.user2_id <> ?1 AND ru.type = ?2)"
			+ " OR u.id IN (SELECT ru.user1_id FROM relationship_users  ru WHERE ru.user2_id = ?1 AND ru.user1_id <> ?1 AND ru.type = ?2);"
			  , nativeQuery = true)
	List<User> findRelationship(Long id,String type);
	Optional<User> findByUsername(@Param("username") String username);
	//Searching People by username or fullname
	@Query(value = "SELECT * FROM users u WHERE ( u.username LIKE CONCAT('%',:search,'%') OR u.fullname LIKE CONCAT('%',:search,'%')) AND u.id <> :id", nativeQuery = true)
	List<User> searchPeople( @Param("search") String search, @Param("id") Long id);
	
}
