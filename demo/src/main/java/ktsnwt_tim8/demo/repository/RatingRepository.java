package ktsnwt_tim8.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import ktsnwt_tim8.demo.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	Page<Rating> findAllByOfferID(Long id, Pageable page);

	Rating findOneByUserIDAndOfferID(Long id, Long id2);

}
