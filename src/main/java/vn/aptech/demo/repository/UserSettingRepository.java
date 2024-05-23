package vn.aptech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.UserSetting;
@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long>{
	@Query(value = "SELECT * FROM user_setting us WHERE us.user_id=?1 AND us.id IN (SELECT p.setting_id FROM posts p WHERE p.group_id IS NULL)",nativeQuery = true)
	List<UserSetting> findAllSettingOfUser(Long id);
	
}
