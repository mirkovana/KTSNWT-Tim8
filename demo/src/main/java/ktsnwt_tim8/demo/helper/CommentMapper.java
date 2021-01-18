package ktsnwt_tim8.demo.helper;

import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.model.Comment;

public class CommentMapper implements MapperInterface<Comment, CommentDTO> {

	@Override
	public Comment toEntity(CommentDTO dto) {
		
		return new Comment(dto.getID(), dto.getText(), dto.getDate());
		
	}

	@Override
	public CommentDTO toDto(Comment entity) {
		
		return new CommentDTO(entity.getID(), entity.getText(), entity.getDate(), entity.getImagePath(), entity.getUser().getUsername());
	}

}
