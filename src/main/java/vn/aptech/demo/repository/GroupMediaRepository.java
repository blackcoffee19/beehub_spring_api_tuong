package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.GroupMedia;

@Repository
public interface GroupMediaRepository extends JpaRepository<GroupMedia, Long>{
	List<GroupMedia> findByGroup_id(Long id);
}
