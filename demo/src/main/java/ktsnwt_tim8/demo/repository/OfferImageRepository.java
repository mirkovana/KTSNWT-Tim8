package ktsnwt_tim8.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;


public interface OfferImageRepository extends JpaRepository<OfferImage, Long> {
	List<OfferImage> findAllByOffer(Offer offer);
	
	Page<OfferImage> findAllByOfferID(Long id, Pageable page);
}
