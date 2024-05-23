package vn.aptech.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.GroupMember;
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer>{
	@Query(value = "SELECT gm.* FROM group_members gm WHERE gm.group_id= ?1 AND gm.user_id=?2", nativeQuery = true)
	public Optional<GroupMember> findMemberInGroup(Long id_group, Long id_user);
}
