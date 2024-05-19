package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Group;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
