package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.ReportTypes;
@Repository
public interface ReportTypeRepository extends JpaRepository<ReportTypes, Integer> {

}
