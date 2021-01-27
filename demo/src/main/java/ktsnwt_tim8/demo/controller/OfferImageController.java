package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.helper.Helper;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.service.OfferImageService;

@RestController
@RequestMapping(value = "/api/Offer-images")
public class OfferImageController {

	@Autowired
	private OfferImageService service;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<OfferImageDTO> addPhoto(@RequestParam("offerID") Long offerID,
			@RequestParam("description") String desc, @RequestParam("image") MultipartFile image) {
		OfferImage offerImage;
		OfferImageDTO offerImageDTO = new OfferImageDTO(desc);

		try {
			offerImage = service.create(offerID, offerImageDTO, image);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		OfferImageDTO ret = new OfferImageDTO(offerImage.getID(), offerImage.getDescription(),
				Helper.fromFileToBase64(offerImage.getPath()));

		return new ResponseEntity<>(ret, HttpStatus.CREATED);

	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/{imageID}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageID) {

		try {
			service.deleteImage(imageID);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<OfferImageDTO> updateImageDesc(@RequestParam("offerID") Long offerID,
			@RequestParam("description") String desc, @RequestParam("imageID") Long imageID) {
		OfferImageDTO imageDTO = new OfferImageDTO(desc, imageID);

		OfferImage img;

		try {
			img = service.updateImageDesc(offerID, imageDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		OfferImageDTO ret = new OfferImageDTO(img.getID(), img.getDescription(),
				Helper.fromFileToBase64(img.getPath()));

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{offerID}/{page}/{size}")
	public ResponseEntity<Page<OfferImageDTO>> getAllOfferImages(@PathVariable Long offerID, @PathVariable int page, @PathVariable int size) {

		Page<OfferImage> images;
		Pageable paging = PageRequest.of(page, size);

		try {
			images = service.getAllImages(offerID, paging);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		List<OfferImageDTO> imagesDTO = new ArrayList<OfferImageDTO>();

		for (OfferImage img : images) {
			OfferImageDTO imgDTO = new OfferImageDTO(img.getID(), img.getDescription(),
					Helper.fromFileToBase64(img.getPath()));
			imagesDTO.add(imgDTO);
			System.out.println(imgDTO.getID());
		}

		Page<OfferImageDTO> ret = new PageImpl<OfferImageDTO>(imagesDTO, paging, images.getTotalElements());

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
}
