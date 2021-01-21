package ktsnwt_tim8.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findOneByID(Long i);
	
	@Query(value = "SELECT offer_id from USER_OFFER where user_id = ?1",  nativeQuery = true)
	List<Long> getSubs(Long id);

}
