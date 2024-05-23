package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Group;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	public List<Group> findByGroupnameContains(String groupname);
}
