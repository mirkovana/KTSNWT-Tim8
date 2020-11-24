package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
