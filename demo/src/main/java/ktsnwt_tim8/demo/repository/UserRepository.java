package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findOneByID(Long i);

}
