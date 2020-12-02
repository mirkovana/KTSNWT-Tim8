package ktsnwt_tim8.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.helper.CommentMapper;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.service.CommentService;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	private static CommentMapper mapper = new CommentMapper();
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<CommentDTO>> getAllComments(Pageable page){
		Page<Comment> comments = commentService.findAll(page);
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		for (Comment c: comments) {
			//File file = new File(c.getImagePath());
			
			//byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
			//String encodedString = Base64.getEncoder().encodeToString(fileContent);
			
			//byte[] bytes = Files.readAllBytes(Paths.get(c.getImagePath()));
			//String value = Base64.encodeToString(bytes, Base64.DEFAULT);
			CommentDTO commentDTO = mapper.toDto(c);
			//commentDTO.setImage(value);
			commentsDTO.add(commentDTO);
			//commentsDTO.add(mapper.toDto(c));
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
	
	
	@RequestMapping(value="/old/{id}", method=RequestMethod.POST, consumes = "application/json")
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
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<CommentDTO> addCommentWithPicture(@RequestParam("offerId") Long id, @RequestParam("text") String comm,
			@RequestParam("image") MultipartFile imageFile){
		

	    //ObjectMapper objmapper = new ObjectMapper();
	    //JsonNode actualObj;
		//try {
		//	actualObj = objmapper.readTree(comm);
		//} catch (JsonMappingException e2) {
		//	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occured");
		//} catch (JsonProcessingException e2) {
		//	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
		//}
		
	    //CommentDTO commentDTO = new CommentDTO((actualObj.get("text").toString()));
	    
		CommentDTO commentDTO = new CommentDTO(comm);
		
		Comment comment = mapper.toEntity(commentDTO);
		
		
		Comment c;
		try {
			c = commentService.create(id, comment, imageFile);
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
