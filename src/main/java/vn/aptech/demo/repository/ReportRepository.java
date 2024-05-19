package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.aptech.demo.models.Report;

public interface ReportRepository extends JpaRepository<Report, Integer>{

}
