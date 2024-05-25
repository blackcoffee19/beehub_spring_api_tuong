package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.Gallery;
@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
	List<Gallery> findByUser_id(Long id);
}
