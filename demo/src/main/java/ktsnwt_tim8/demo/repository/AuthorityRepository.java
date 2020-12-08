package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
