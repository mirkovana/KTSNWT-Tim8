package ktsnwt_tim8.demo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
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
		return repo.findById(id).orElse(null);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public Page<Offer> findAllPageable(Pageable page) { 
		return repo.findAll(page);
	}

	/*REMOVES SUBSCRIBER FROM OFFER*/
	public Offer deleteSubscriber(Long id) throws Exception {
		Offer offer = repo.findOneByID(id);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(offer == null) {
			throw new Exception("Offer with given ID does not exits");
		}
		
		boolean uslov = true;
		for (Offer off : ((RegisteredUser)user).getSubscriptions()) {
			if(off.getID().equals(id)) {
				uslov = false;
			}
		}
		if(uslov) {
			throw new Exception("Cannot unsubscribe from not subscribed offer");
		}
		
		Set<RegisteredUser> users = offer.getUsers();
		for (Iterator<RegisteredUser> iterator = users.iterator(); iterator.hasNext();) {
		    RegisteredUser regUser =  iterator.next();
		    if (regUser.getID().equals(user.getID())) {
		        iterator.remove();
		    }       
		}
		return offer;
	}
	
	/*SUBSRCIBES USER TO OFFER*/
	public Offer subscribe(Long id) throws Exception{
		Offer offer = repo.findOneByID(id);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(offer == null) {
			throw new Exception("Offer with passed ID does not exist");
		}
		for (Offer off : ((RegisteredUser)user).getSubscriptions()) {
			if(off.getID().equals(id)) {
				throw new Exception("Already subscribed to this offer");
			}
		}
		
		offer.getUsers().add((RegisteredUser) user);
		return offer;
	}
	 
}
