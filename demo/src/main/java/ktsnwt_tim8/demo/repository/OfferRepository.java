package ktsnwt_tim8.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	//@Override
	//List<Offer> findAll();

	Offer findOneByID(Long offerId);
}
