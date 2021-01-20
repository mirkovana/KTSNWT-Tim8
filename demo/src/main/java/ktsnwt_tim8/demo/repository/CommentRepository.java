package ktsnwt_tim8.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findAllByOfferIDOrderByDateDesc(Long id, Pageable page);


}
