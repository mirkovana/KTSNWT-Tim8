package ktsnwt_tim8.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	@Override
	Page <Offer> findAll(Pageable pageable);

	Offer findOneByID(Long offerId);
}
