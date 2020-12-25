package ktsnwt_tim8.demo.constants;

import org.springframework.beans.factory.annotation.Autowired;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.service.OfferService;

public class PostConstants {
	@Autowired
	public static OfferService service;
	public static final Long ID_POST= 1L;
	public static final String POST_TITLE_DB= "nova predstava";
	public static final String POST_CONTENT_DB= "ovo je neki post";
	
	public static final Integer PAGEABLE_PAGE = 0;
	public static final Integer PAGEABLE_SIZE = 2;
	public static final int FIND_ALL_NUMBER_OF_ITEMS = 2;
	//public static Offer DB_OFFER = service.get((long) 2);
}
