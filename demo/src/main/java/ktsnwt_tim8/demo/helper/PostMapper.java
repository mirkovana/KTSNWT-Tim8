package ktsnwt_tim8.demo.helper;

import ktsnwt_tim8.demo.dto.PostDTO;
import ktsnwt_tim8.demo.model.Post;

public class PostMapper implements MapperInterface<Post, PostDTO>{

	@Override
	public Post toEntity(PostDTO dto) {
		// TODO Auto-generated method stub
		return new Post(dto.getID(), dto.getTitle(), dto.getContent(), dto.getDate());
	}

	@Override
	public PostDTO toDto(Post entity) {
		// TODO Auto-generated method stub
		return new PostDTO(entity.getID(), entity.getTitle(), entity.getContent(), entity.getDate());
	}

}
