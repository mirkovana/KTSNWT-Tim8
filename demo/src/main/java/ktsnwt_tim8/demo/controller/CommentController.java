package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.helper.CommentMapper;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.service.CommentService;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	private static CommentMapper mapper = new CommentMapper();
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<CommentDTO>> getAllComments(Pageable page){
		Page<Comment> comments = commentService.findAll(page);
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		for (Comment c: comments) {
			commentsDTO.add(mapper.toDto(c));
		}
		Page<CommentDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, comments.getPageable(), comments.getTotalElements());
        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Page<CommentDTO>> getCommentsFromPost(@PathVariable Long id, Pageable page){
		
		Page<Comment> comments = commentService.findAllByOfferID(id, page);
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for (Comment c: comments) {
			commentsDTO.add(mapper.toDto(c));
		}
		
		Page<CommentDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, comments.getPageable(), comments.getTotalElements());

        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<CommentDTO> addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
		
		Comment comment = mapper.toEntity(commentDTO);
		
		Comment c;
		try {
			c = commentService.create(id, comment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(c), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/{commentId}", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO){
		
		Comment c = mapper.toEntity(commentDTO);
		try {
			c = commentService.updateComment(commentId, c);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(c), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{commentId}", method=RequestMethod.DELETE)	
	public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long commentId){
		
		try {
			commentService.deleteComment(commentId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
        return new ResponseEntity<>(null, HttpStatus.OK);
	}

	

}
