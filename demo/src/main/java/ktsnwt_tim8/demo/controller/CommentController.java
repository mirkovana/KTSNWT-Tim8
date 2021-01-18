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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.helper.Helper;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.service.CommentService;


@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Page<CommentDTO>> getCommentsFromPost(@PathVariable Long id, Pageable page){
		
		Page<Comment> comments;
		try {
			comments = commentService.findAllByOfferID(id, page);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		
		for (Comment c: comments) {
			CommentDTO cDTO = new CommentDTO(c.getID(), c.getText(), c.getDate(), Helper.fromFileToBase64(c.getImagePath()), c.getUser().getUsername());	
			commentsDTO.add(cDTO);
		}
		
		Page<CommentDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, comments.getPageable(), comments.getTotalElements());

        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
	}
		
	
	@RequestMapping(method=RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CommentDTO> addCommentWithPicture(@RequestParam("offerId") Long id, @RequestParam("text") String comm,
			@RequestParam("image") MultipartFile imageFile){
		    
		CommentDTO commentDTO = new CommentDTO(comm);
			
		Comment c;
		try {
			c = commentService.create(id, commentDTO, imageFile);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		CommentDTO cDTO = new CommentDTO(c.getID(), c.getText(), c.getDate(), Helper.fromFileToBase64(c.getImagePath()), c.getUser().getUsername());	
		
        return new ResponseEntity<>(cDTO, HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CommentDTO> updateComment(@RequestParam("commentId") Long commentId, @RequestParam("text") String comm,
			@RequestParam("image") MultipartFile imageFile){

		CommentDTO commentDTO = new CommentDTO(comm);
		
		Comment c;
		try {
			c = commentService.updateComment(commentId, commentDTO, imageFile);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		CommentDTO cDTO = new CommentDTO(c.getID(), c.getText(), c.getDate(), Helper.fromFileToBase64(c.getImagePath()), c.getUser().getUsername());	
		
        return new ResponseEntity<>(cDTO, HttpStatus.OK);
	}

	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_USER')")
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
