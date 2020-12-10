package ktsnwt_tim8.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Subcategory;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	//@Override
	//List<Offer> findAll();

	Offer findOneByID(Long offerId);
	
	@Query("SELECT o FROM Offer o WHERE lower(o.title) LIKE lower(concat('%',:title, '%'))"
			+ "and lower(o.place) LIKE lower(concat('%',:place, '%')) and o.subcategory in :subs order by o.avgRating desc")
	Page<Offer> filterByTitlePlaceAndSubcategory(@Param("title") String title, 
			@Param("place") String place, @Param("subs")List<Subcategory> subs, Pageable pageable);
	
	
	@Query("SELECT o FROM Offer o WHERE lower(o.title) LIKE lower(concat('%',:title, '%'))"
			+ "and lower(o.place) LIKE lower(concat('%',:place, '%')) order by o.avgRating desc")
	Page<Offer> filterByTitleAndPlace(@Param("title") String title, 
			@Param("place") String place, Pageable pageable);
}
