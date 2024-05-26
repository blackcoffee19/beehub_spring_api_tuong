package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Requirement;
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
	@Query(value = "SELECT r.* FROM requirements r WHERE r.group_id=?1", nativeQuery = true)
	List<Requirement> findByGroup_id(Long id);
}
