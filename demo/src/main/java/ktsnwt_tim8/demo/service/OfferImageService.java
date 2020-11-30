package ktsnwt_tim8.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.repository.OfferImageRepository;

@Service
public class OfferImageService {
	@Autowired
	private OfferImageRepository repo;
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public List<OfferImage> findAllByOffer(Offer offer) {
		return repo.findAllByOffer(offer);
	}

}
