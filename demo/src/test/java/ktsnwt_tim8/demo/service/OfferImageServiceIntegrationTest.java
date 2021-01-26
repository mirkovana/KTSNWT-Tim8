package ktsnwt_tim8.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;

import javax.transaction.Transactional;

//import ktsnwt_tim8.constants.OfferImageConstants.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.constants.OfferImageConstants;
import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.model.OfferImage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class OfferImageServiceIntegrationTest {
	@Autowired
	private OfferImageService offerImageService;

	@Test
	public void findAllByOfferByIDPageable() throws InterruptedException {
		Pageable pageable = PageRequest.of(OfferImageConstants.PAGEABLE_PAGE, OfferImageConstants.PAGEABLE_SIZE);
		Page<OfferImage> found = offerImageService.findAllByOfferID(OfferImageConstants.OFFER_IMAGE_ID, pageable);

		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}

	@Test
	public void getAllImages() throws Exception {
		Pageable pageable = PageRequest.of(OfferImageConstants.PAGEABLE_PAGE, OfferImageConstants.PAGEABLE_SIZE);
		Page<OfferImage> found = offerImageService.getAllImages(OfferImageConstants.OFFER_IMAGE_ID, pageable);

		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}

	@Test(expected = java.lang.Exception.class)
	public void getAllImagesBadID() throws Exception {
		Pageable pageable = PageRequest.of(OfferImageConstants.PAGEABLE_PAGE, OfferImageConstants.PAGEABLE_SIZE);
		Page<OfferImage> found = offerImageService.getAllImages(OfferImageConstants.OFFER_IMAGE_BAD_ID, pageable);
	}

	@Test
	@Transactional 
	public void create() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.NEW_OFFER_IMAGE_ID,
				OfferImageConstants.IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		FileInputStream inputFile = new FileInputStream(OfferImageConstants.IMAGE_PATH);
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
		OfferImage img = offerImageService.create(OfferImageConstants.OFFER_ID, dto, file);

		assertEquals(OfferImageConstants.NEW_OFFER_IMAGE_ID, img.getID());
	}

	@Test(expected = java.lang.Exception.class)
	public void createBadOfferID() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.NEW_OFFER_IMAGE_ID,
				OfferImageConstants.IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		FileInputStream inputFile = new FileInputStream(OfferImageConstants.IMAGE_PATH);
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
		OfferImage img = offerImageService.create(OfferImageConstants.BAD_OFFER_ID, dto, file);
	}

	@Test(expected = java.lang.Exception.class)
	public void createNoDescription() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.NEW_OFFER_IMAGE_ID,
				OfferImageConstants.BAD_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		FileInputStream inputFile = new FileInputStream(OfferImageConstants.IMAGE_PATH);
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
		OfferImage img = offerImageService.create(OfferImageConstants.BAD_OFFER_ID, dto, file);
	}

	@Test(expected = java.lang.Exception.class)
	public void createNoImage() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.NEW_OFFER_IMAGE_ID,
				OfferImageConstants.BAD_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);

		OfferImage img = offerImageService.create(OfferImageConstants.BAD_OFFER_ID, dto, null);
	}

	@Test
	@Transactional
	public void deleteImage() throws Exception {
		offerImageService.deleteImage(OfferImageConstants.OFFER_IMAGE_ID);

		Pageable pageable = PageRequest.of(OfferImageConstants.PAGEABLE_PAGE, OfferImageConstants.PAGEABLE_SIZE);
		Page<OfferImage> found = offerImageService.findAllByOfferID(OfferImageConstants.OFFER_IMAGE_ID, pageable);

		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS - 1, found.getNumberOfElements());

		// Returns deleted element, because of other tests
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.NEW_OFFER_IMAGE_ID,
				OfferImageConstants.IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		FileInputStream inputFile = new FileInputStream(OfferImageConstants.IMAGE_PATH);
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
		OfferImage img = offerImageService.create(OfferImageConstants.OFFER_ID, dto, file);

	}

	@Test(expected = java.lang.Exception.class)
	public void deleteImageBadID() throws Exception {
		offerImageService.deleteImage(OfferImageConstants.OFFER_IMAGE_BAD_ID);
	}

	@Test
	@Transactional
	public void updateImageDesc() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.IMAGE_ID,
				OfferImageConstants.NEW_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		OfferImage offer = offerImageService.updateImageDesc(OfferImageConstants.OFFER_ID, dto);

		assertEquals(OfferImageConstants.NEW_IMAGE_DESCRIPTION, offer.getDescription());
	}

	@Test(expected = java.lang.Exception.class)
	public void updateImageDescBadDescripiton() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.IMAGE_ID,
				OfferImageConstants.BAD_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		OfferImage offer = offerImageService.updateImageDesc(OfferImageConstants.OFFER_ID, dto);
		
	}

	@Test(expected = java.lang.Exception.class)
	public void updateImageDescBadID() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.IMAGE_ID,
				OfferImageConstants.NEW_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		OfferImage offer = offerImageService.updateImageDesc(OfferImageConstants.BAD_OFFER_ID, dto);

	}
	
	@Test(expected = java.lang.Exception.class)
	public void updateImageDescBadImageID() throws Exception {
		OfferImageDTO dto = new OfferImageDTO(OfferImageConstants.OFFER_IMAGE_BAD_ID,
				OfferImageConstants.NEW_IMAGE_DESCRIPTION, OfferImageConstants.IMAGE_BASE_64);
		OfferImage offer = offerImageService.updateImageDesc(OfferImageConstants.BAD_OFFER_ID, dto);

	}
}
