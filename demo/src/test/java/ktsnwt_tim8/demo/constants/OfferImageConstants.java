package ktsnwt_tim8.demo.constants;

import ktsnwt_tim8.demo.helper.Helper;

public class OfferImageConstants {
	public static final Long OFFER_IMAGE_ID = 1L;
	public static final Long OFFER_IMAGE_BAD_ID = 120L;

	public static final Long OFFER_ID = 1L;
	public static final Long NEW_OFFER_ID = 3L;
	public static final Long BAD_OFFER_ID = 120L;

	public static final Long NEW_OFFER_IMAGE_ID = 6L;

	public static final String IMAGE_DESCRIPTION = "SOME DESCRIPTION";
	public static final String BAD_IMAGE_DESCRIPTION = "";
	public static final String NEW_IMAGE_DESCRIPTION = "NEW DESCRIPTION";

	public static final Integer PAGEABLE_PAGE = 0;
	public static final Integer PAGEABLE_SIZE = 20;

	public static final long FIND_ALL_NUMBER_OF_ITEMS = 5;
	public static final String IMAGE_PATH = "src/main/resources/images/beforeHouseWasDisco.jpg";
	public static final String IMAGE_BASE_64 = Helper.fromFileToBase64(IMAGE_PATH);
	
	public static final int TOTAL_SUBS = 1;
	//For offer_id
	public static final int TOTAL_SUBS_OFFER_ID = 2;
	
	
}
