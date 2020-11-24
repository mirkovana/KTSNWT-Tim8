package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.User;

public interface UserInterface extends JpaRepository<User, Long> {

}
