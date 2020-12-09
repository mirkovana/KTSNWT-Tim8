package ktsnwt_tim8.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.repository.OfferImageRepository;
import ktsnwt_tim8.demo.repository.OfferRepository;

@Service
public class OfferImageService {
	@Autowired
	private OfferImageRepository repo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public List<OfferImage> findAllByOffer(Offer offer) {
		return repo.findAllByOffer(offer);
	}

}
