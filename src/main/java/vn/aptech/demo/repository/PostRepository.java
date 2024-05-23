package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query(value = "SELECT * "
			  + " from posts p"
			  + " where p.user_id = ?1 && p.group_id IS NULL"
			  , nativeQuery = true)
	List<Post> findByUserId( Long id);
	
	//Post of user's friends and user
	//Post of the group joined
	//Except the Blocked user 
	@Query(value = "SELECT DISTINCT p.* FROM posts p"
			+ " LEFT JOIN relationship_users ru ON ru.user1_id = p.user_id OR ru.user2_id = p.user_id"
			+ " LEFT JOIN users u ON p.user_id = u.id"
			+ " LEFT JOIN user_setting s ON p.setting_id = s.id"
			+ " WHERE ((ru.user1_id = ?1 OR ru.user2_id = ?1) AND ru.type <> 'BLOCKED' AND p.group_id IS NULL AND s.setting_type<>'HIDDEN')"
			+ " OR ( p.group_id IN ( SELECT gm.group_id FROM group_members gm WHERE gm.user_id = 1 ) "
			+ " AND ( p.user_id NOT IN (SELECT ru2.user1_id FROM relationship_users ru2 WHERE ru2.type ='BLOCKED') "
			+ " OR p.user_id NOT IN (SELECT ru3.user2_id FROM relationship_users ru3 WHERE ru3.type ='BLOCKED')))"
			+ " ORDER BY p.create_at DESC", nativeQuery = true)
	List<Post> newestPostFromGroupAndFriend(Long id);
	//Post of user's friends and user
	//Post of the group joined
	//Except the Blocked user 
	//Take random() and limit 
	@Query(value = "SELECT * FROM posts WHERE id IN (SELECT DISTINCT p.id FROM posts p"
			+ " LEFT JOIN relationship_users ru ON ru.user1_id = p.user_id OR ru.user2_id = p.user_id"
			+ " LEFT JOIN users u ON p.user_id = u.id"
			+ " LEFT JOIN user_setting s ON p.setting_id = s.id"
			+ " WHERE ((ru.user1_id = ?1 OR ru.user2_id = ?1) AND ru.type <> 'BLOCKED' AND p.group_id IS NULL AND s.setting_type<>'HIDDEN')"
			+ " OR ( p.group_id IN ( SELECT gm.group_id FROM group_members gm WHERE gm.user_id = 1 ) "
			+ " AND ( p.user_id NOT IN (SELECT ru2.user1_id FROM relationship_users ru2 WHERE ru2.type ='BLOCKED') "
			+ " OR p.user_id NOT IN (SELECT ru3.user2_id FROM relationship_users ru3 WHERE ru3.type ='BLOCKED')))"
			+ " ORDER BY p.create_at DESC)"
			+ " ORDER BY RAND()"
			+ " LIMIT ?2", nativeQuery = true)
	List<Post>  randomNewestPostFromGroupAndFriend(Long id, int limit);
	
	//Search Public and Friend Posts contain string: search
	@Query(value = "SELECT p.* FROM posts p"
			+ " LEFT JOIN user_setting s ON p.setting_id = s.id"
			+ " WHERE p.text LIKE CONCAT('%',:search,'%')"
			+ " AND ((p.user_id NOT IN ( SELECT u.id FROM users u LEFT JOIN relationship_users ru ON ru.user1_id = u.id OR ru.user2_id = u.id"
			+ " WHERE ru.type='BLOCKED' AND (ru.user1_id = :id OR ru.user2_id = :id) "
			+ " ) AND s.setting_type='PUBLIC') OR (p.user_id IN (SELECT  u.id FROM users u LEFT JOIN relationship_users ru ON ru.user1_id = u.id OR ru.user2_id = u.id"
			+ " WHERE ru.type='FRIEND' AND (ru.user1_id = :id OR ru.user2_id = :id)) AND s.setting_type='FOR_FRIEND')) "
			+ " AND p.group_id IS NULL"
			+ " ORDER BY p.create_at DESC ", nativeQuery = true)
	
	List<Post> searchPublicPostsContain( @Param("search") String search, @Param("id") Long id);
	
	//Search Group joined Posts contain string: search
	@Query(value = "SELECT p.* FROM posts p"
			+ " WHERE p.group_id IN ( SELECT gm.group_id FROM group_members gm "
			+ " LEFT JOIN users u ON u.id = gm.user_id"
			+ " LEFT JOIN groups g ON g.id = gm.group_id"
			+ " WHERE gm.user_id = 1 AND g.public_group=1 )"
			+ " AND ( p.user_id NOT IN (SELECT ru.user1_id FROM relationship_users ru WHERE ru.user2_id=:id AND ru.type='BLOCKED')"
			+ " AND p.user_id NOT IN (SELECT ru.user2_id FROM relationship_users ru WHERE ru.user1_id=:id AND ru.type='BLOCKED') )"
			+ " AND p.text LIKE '%:search%'"
			+ " ORDER BY p.create_at DESC ", nativeQuery = true)
	List<Post> searchPostsInGroupJoinedContain( @Param("search") String search, @Param("id") Long id);
	
}
