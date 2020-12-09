package ktsnwt_tim8.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findOneByID(Long i);

}
