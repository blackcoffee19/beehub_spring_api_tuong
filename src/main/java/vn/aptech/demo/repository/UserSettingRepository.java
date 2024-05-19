package vn.aptech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.aptech.demo.models.UserSetting;
@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long>{

}
