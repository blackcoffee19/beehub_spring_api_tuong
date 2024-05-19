package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	//@Query("select b from Post b where b.user_id=:id")
	@Query(value = "SELECT * "
			  + " from posts p"
			  + " where p.user_id = ?1"
			  , nativeQuery = true)
	List<Post> findByUserId( Long id);
}
