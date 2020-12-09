package ktsnwt_tim8.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	Page<Post> findAllByOffer(Offer offer, Pageable page);
	List<Post> findAllByOffer(Offer offer);
	//Page<Post> findAllByOffer(Offer offer, Pageable pageable);
	
}
