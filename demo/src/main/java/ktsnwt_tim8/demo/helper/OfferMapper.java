package ktsnwt_tim8.demo.helper;

import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.model.Offer;

public class OfferMapper implements MapperInterface<Offer, OfferDTO>{

	@Override
	public Offer toEntity(OfferDTO dto) {
		return new Offer(dto.getID(), dto.getTitle(), dto.getDescription(), dto.getLat(), dto.getLon());
	}

	@Override
	public OfferDTO toDto(Offer entity) {
		return new OfferDTO(entity);
	}

}
