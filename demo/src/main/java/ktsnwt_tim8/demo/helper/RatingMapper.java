package ktsnwt_tim8.demo.helper;

import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.model.Rating;

public class RatingMapper implements MapperInterface<Rating, RatingDTO> {

	@Override
	public Rating toEntity(RatingDTO dto) {
		return new Rating(dto.getRating());
	}

	@Override
	public RatingDTO toDto(Rating entity) {
	
		return new RatingDTO(entity.getID(), entity.getRating());
	}

	

}
