package ktsnwt_tim8.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.CommentRepository;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.repository.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository repo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Page<Comment> findAll(Pageable page){
		return repo.findAll(page);
	}


	public Page<Comment> findAllByOfferID(Long id, Pageable page) {
		return repo.findAllByOfferID(id, page);
	}


	public Comment create(Long offerId, Comment comment) throws Exception {
		
		if (comment.getText().isEmpty()) {
			throw new Exception("Comment cannot be empty");
		}
		
		Offer offer = offerRepo.findOneByID(offerId);
		
		if (offer == null) {
			throw new Exception("Offer with passed if does not exist");
		}
		comment.setOffer(offer);
		comment.setDate(new Date());
		
		// this is a dummy user, we will get the user automatically once Spring Security is implemented
		User u = userRepo.findOneByID(2l);
		
		comment.setUser((RegisteredUser) u);
		
		return repo.save(comment);
		
	}


	public void deleteComment(Long commentId) throws Exception {
		Optional<Comment> c = repo.findById(commentId);
		if (c.isPresent()) {
			String fileName = c.get().getImagePath();
			if (fileName != null) {
				File image = new File(fileName);
				image.delete();
			}
			repo.deleteById(commentId);
			return;
		}
		//if (repo.existsById(commentId)) {
		//	Optional<Comment> c = repo.findById(commentId);
		//	
		//	File myObj = new File("filename.txt"); 
		//    repo.deleteById(commentId);
		//    
		 //   return;
		//}
		throw new Exception("Comment with given id does not exits.");
	}


	public Comment updateComment(Long commentId, Comment comment) throws Exception {
		
		Comment c = repo.getOne(commentId);
		
		if (c == null) {
			throw new Exception("Comment with given id does not exits.");
		}
		if (comment.getText().isEmpty()) {
			throw new Exception("Comment cannot be empty.");
		}
		c.setText(comment.getText());
		c.setImagePath(comment.getImagePath());
		
		repo.save(c);
		return c;
	}


	public Comment create(Long id, Comment comment, MultipartFile imageFile) throws Exception {
		if (comment.getText().isEmpty()) {
			throw new Exception("Comment cannot be empty");
		}
		
		Offer offer = offerRepo.findOneByID(id);
		
		if (offer == null) {
			throw new Exception("Offer with passed if does not exist");
		}
		
		Random random = new Random();
		int num = random.nextInt(100000);
		String path;
		
		if (imageFile.isEmpty()) {
			path = null;
		}
		else {
		// 2 is hardcoded for user
		path = "src/main/resources/commentpicture" + num + ".jpg";
		
		// check if file is in right format
		File file = new File(path);

		try (OutputStream os = new FileOutputStream(file)) {
		    os.write(imageFile.getBytes());
		} catch (FileNotFoundException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
		} catch (IOException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
		}
		}
		
		comment.setOffer(offer);
		comment.setDate(new Date());
		comment.setImagePath(path);
		
		// this is a dummy user, we will get the user automatically once Spring Security is implemented
		User u = userRepo.findOneByID(2l);
		
		comment.setUser((RegisteredUser) u);
		
		return repo.save(comment);
		
	}
	
	
	
}
