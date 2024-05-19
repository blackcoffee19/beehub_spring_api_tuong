package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.GroupMember;
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer>{

}
