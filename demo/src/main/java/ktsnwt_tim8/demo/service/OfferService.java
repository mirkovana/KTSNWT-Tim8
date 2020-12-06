package ktsnwt_tim8.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.repository.OfferRepository;

@Service
public class OfferService {
	@Autowired
	private OfferRepository repo;
	
	@JsonIgnore
	public List<Offer> listAll() {
		return repo.findAll();
	}

	public Offer save(Offer offer) {
		return repo.save(offer);

	}

	public Offer get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public Page<Offer> findAllPageable(Pageable page) { 
		return repo.findAll(page);
	}
}
