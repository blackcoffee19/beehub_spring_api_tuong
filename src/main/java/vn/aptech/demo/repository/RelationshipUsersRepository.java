package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.RelationshipUsers;
@Repository
public interface RelationshipUsersRepository extends JpaRepository<RelationshipUsers, Integer> {

}
