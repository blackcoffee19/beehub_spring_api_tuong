package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Group;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	public List<Group> findByGroupnameContains(String groupname);
	@Query(value = "SELECT g.* FROM groups g"
			+ " WHERE g.id IN (SELECT gm.group_id FROM group_members gm WHERE gm.user_id = ?1)"
			+ " AND g.active =1", nativeQuery = true)
	public List<Group> findAllGroupJoined(Long id);
	@Query(value = "SELECT g.* FROM groups g"
			+ " WHERE g.id IN (SELECT gm.group_id FROM group_members gm WHERE gm.user_id = ?1 AND (gm.role='GROUP_CREATOR' OR gm.role='GROUP_MANAGER'))"
			+ " AND g.active =1", nativeQuery = true)
	public List<Group> findAllOwnGroup(Long id);
}
