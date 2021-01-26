package ktsnwt_tim8.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.model.RegisteredUser;
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

	public Page<OfferImage> findAllByOfferID(Long id, Pageable page) {
		return repo.findAllByOfferID(id, page);
	}

	public Page<OfferImage> getAllImages(Long id, Pageable page) throws Exception {
		Offer offer = offerRepo.findOneByID(id);

		if (offer == null) {
			throw new Exception("Offer with passed ID does not exist.");
		}
		Page<OfferImage> images = repo.findAllByOfferID(id, page);

		return images;
	}

	public OfferImage create(Long offerID, OfferImageDTO imageDTO, MultipartFile imagefile) throws Exception {
		Random random = new Random();
		String path;

		Offer offer = offerRepo.findOneByID(offerID);

		if (offer == null) {
			throw new Exception("Offer with passed ID does not exist.");
		}

		if (imageDTO.getDescription().isEmpty()) {
			throw new Exception("Description cannot be empty.");
		}
		
		if(!imageDTO.getDescription().matches("[a-zA-Z0-9., ]*")) {
			throw new Exception("Description can only contains letters and numbers.");
		}

		int randNum = random.nextInt(100000);

		if (imagefile.isEmpty()) {
			throw new Exception("Image cannot be empty.");
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image cannot be empty");
		} else {
			path = "src/main/resources/images/offerImage" + randNum + ".jpg";
			File file = new File(path);

			try (OutputStream ostream = new FileOutputStream(file)) {
				ostream.write(imagefile.getBytes());
			} catch (FileNotFoundException e1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error image corrupted.");
			} catch (IOException e1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error image corrupted.");
			}
		}

		OfferImage offerImage = new OfferImage();
		offerImage.setDescription(imageDTO.getDescription());

		offerImage.setPath(path);
		offerImage.setOffer(offer);

		offer.getImages().add(offerImage);

		return repo.save(offerImage);
	}

	public void deleteImage(Long imageID) throws Exception {

		Optional<OfferImage> offerImage = repo.findById(imageID);
		String path;

		if (offerImage.isPresent()) {
			path = offerImage.get().getPath();
			if (path == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error.");
			} else {
				File image = new File(path);
				image.delete();
			}
			repo.deleteById(imageID);

			List<OfferImage> images = repo.findAllByOffer(offerImage.get().getOffer());
			for (Iterator<OfferImage> iterator = images.iterator(); iterator.hasNext();) {
				OfferImage img = iterator.next();
				if (img.getID().equals(imageID)) {
					iterator.remove();
				}
			}

		} else {
			throw new Exception("Offer image with given ID does not exits.");
		}
	}

	public OfferImage updateImageDesc(Long offerID, OfferImageDTO imageDTO) throws Exception {

		if (imageDTO.getDescription().isEmpty()) {
			throw new Exception("Description cannot be empty.");
		}
		if(!imageDTO.getDescription().matches("[a-zA-Z0-9., ]*")) {
			throw new Exception("Description can only contains letters and numbers.");
		}

		Optional<OfferImage> offerImage = repo.findById(imageDTO.getID());

		if (!offerImage.isPresent()) {
			throw new Exception("Offer image with given id does not exist.");
		}
		boolean uslov = true;
		Offer offer = offerRepo.findOneByID(offerID);
		Set<OfferImage>images = offer.getImages();
		for (Iterator<OfferImage> iterator = images.iterator(); iterator.hasNext();) {
			OfferImage img = iterator.next();
			if (img.getID().equals(imageDTO.getID())) {
				uslov = false;
			}
		}
		if(uslov) {
			throw new Exception("Offer image with given id does not exist in offer.");
		}

		OfferImage o;
		o = offerImage.get();

		o.setDescription(imageDTO.getDescription());

		return repo.save(o);
	}

}
