package za.co.RecruitmentZone.repository;

import org.springframework.data.repository.CrudRepository;
import za.co.RecruitmentZone.entity.Admin;

public interface AdminRepository  extends CrudRepository<Admin, Integer> {
}
