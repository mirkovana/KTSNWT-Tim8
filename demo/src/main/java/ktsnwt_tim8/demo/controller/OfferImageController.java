package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/{imageID}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<OfferImageDTO> deleteImage(@PathVariable Long imageID) {

		try {
			service.deleteImage(imageID);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<OfferImageDTO> updateImageDesc(@RequestParam("offerID") Long offerID,
			@RequestParam("description") String desc) {
		OfferImageDTO imageDTO = new OfferImageDTO(desc);

		OfferImage img;

		try {
			img = service.updateImageDesc(offerID, imageDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		OfferImageDTO ret = new OfferImageDTO(img.getID(), img.getDescription(), img.getPath());

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{offerID}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<Page<OfferImageDTO>> getAllOfferImages(@PathVariable Long offerID, Pageable page) {

		Page<OfferImage> images;
		
		try {
			images = service.getAllImages(offerID, page);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		List<OfferImageDTO> imagesDTO = new ArrayList<OfferImageDTO>();

		for (OfferImage img : images) {
			OfferImageDTO imgDTO = new OfferImageDTO(img.getID(), img.getDescription(), img.getPath());
			imagesDTO.add(imgDTO);
		}

		Page<OfferImageDTO> ret = new PageImpl<OfferImageDTO>(imagesDTO, page, images.getTotalElements());

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
}
