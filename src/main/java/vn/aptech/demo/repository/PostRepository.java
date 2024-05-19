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
	@Query(value = "SELECT DISTINCT p.id, p.text, p. media, p.user_id, p.group_id, p.create_at FROM posts p "
			+ " LEFT JOIN relationship_users  ru ON ru.user1_id = p.user_id OR ru.user2_id=p.user_id"
			+ " WHERE (ru.user1_id = ?1 OR ru.user2_id= ?1) AND p.user_id <> ?1 AND ru.type!='BLOCKED'", nativeQuery = true)
	List<Post> newestPostFromFriend(Long id);
}
