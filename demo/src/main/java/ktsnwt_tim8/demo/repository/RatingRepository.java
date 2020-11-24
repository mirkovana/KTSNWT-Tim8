package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
