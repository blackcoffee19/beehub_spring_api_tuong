package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Requirement;
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

}
