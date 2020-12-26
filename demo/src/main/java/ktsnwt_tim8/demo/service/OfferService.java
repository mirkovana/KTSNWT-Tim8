package ktsnwt_tim8.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.dto.FilterDTO;
import ktsnwt_tim8.demo.model.Category;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.CategoryRepository;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.repository.SubcategoryRepository;
import ktsnwt_tim8.demo.repository.UserRepository;

@Service
public class OfferService {
	@Autowired
	private OfferRepository repo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private SubcategoryRepository subcatRepo;
	
	@Autowired
	private UserRepository userRepo;

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

	/* REMOVES SUBSCRIBER FROM OFFER */
	public Offer deleteSubscriber(Long id) throws Exception {
		Offer offer = repo.findOneByID(id);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (offer == null) {
			throw new Exception("Offer with given ID does not exits");
		}

		boolean uslov = true;
		for (Offer off : ((RegisteredUser) user).getSubscriptions()) {
			if (off.getID().equals(id)) {
				uslov = false;
			}
		}
		if (uslov) {
			throw new Exception("Cannot unsubscribe from not subscribed offer");
		}

		Set<RegisteredUser> users = offer.getUsers();
		for (Iterator<RegisteredUser> iterator = users.iterator(); iterator.hasNext();) {
			RegisteredUser regUser = iterator.next();
			if (regUser.getID().equals(user.getID())) {
				iterator.remove();
			}
		}
		Set<Offer> subList = ((RegisteredUser) user).getSubscriptions();
		for (Iterator<Offer> iterator = subList.iterator(); iterator.hasNext();) {
			Offer off = iterator.next();
			if (off.getID().equals(offer.getID())) {
				iterator.remove();
			}
		}
		
		return offer;
	}

	/* SUBSRCIBES USER TO OFFER */
	public Offer subscribe(Long id) throws Exception {
		Offer offer = repo.findOneByID(id);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (offer == null) {
			throw new Exception("Offer with passed ID does not exist");
		}
		for (Offer off : ((RegisteredUser) user).getSubscriptions()) {
			if (off.getID().equals(id)) {
				throw new Exception("Already subscribed to this offer");
			}
		}

		offer.getUsers().add((RegisteredUser) user);
	
		((RegisteredUser) user).getSubscriptions().add(offer);
		userRepo.save(user);
		repo.save(offer);
		System.out.println(offer.getID());
		return offer;
	}

	public Page<Offer> filter(FilterDTO filterDTO, Pageable pageable) {

		String title = filterDTO.getTitle();
		String place = filterDTO.getPlace();
		List<Long> subcatIDs = filterDTO.getSubcatIDs(); // subcategory ids sent from frontend
		List<Long> catIDs = filterDTO.getCatIDs(); // category ids sent from frontend
		subcatIDs.remove(null); // removing null values if there are any
		catIDs.remove(null);

		if (title == null)
			title = ""; // in the filtering functions, comparing with an empty string returns true
		if (place == null)
			place = ""; // which is what works in this case

		if (subcatIDs.size() == 0 && catIDs.size() == 0) { // filtering only by title and place
			return repo.filterByTitleAndPlace(title, place, pageable);
		}

		List<Subcategory> subs = getSubcategoriesFromCatIdsAndSubcatIds(subcatIDs, catIDs);
		return repo.filterByTitlePlaceAndSubcategory(title, place, subs, pageable);
	}

	private ArrayList<Subcategory> getSubcategoriesFromCatIdsAndSubcatIds(List<Long> subcatIDs, List<Long> catIDs) {

		HashMap<Long, Subcategory> subMap = new HashMap<Long, Subcategory>(); // map made to avoid duplicates
		// each offer has a subcategory by which we can select it in jpa query
		// so from categories gotten by their ids, we extract their subcategories
		for (Long catID : catIDs) { // cat ids send from front
			Optional<Category> cat = catRepo.findById(catID);
			if (cat.isPresent()) { // isPresent to check if a category with the given id exists
				Set<Subcategory> subcats = cat.get().getSubcategories();
				for (Subcategory s : subcats) { // adding (id, subcat) to maps later to be user for filtering
					subMap.put(s.getID(), s);
				}
			}
		}
		for (Long id : subcatIDs) {
			Optional<Subcategory> subcat = subcatRepo.findById(id);
			if (subcat.isPresent()) {
				subMap.put(id, subcat.get()); // adding subcategories objects retrieved from repository by their id
			} // which was sent from front
		}
		return new ArrayList<Subcategory>(subMap.values()); // converting to list
	}

}
