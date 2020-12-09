package ktsnwt_tim8.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Service
public class CommentService {

	@Autowired
	private CommentRepository repo;
	
	@Autowired
	private OfferRepository offerRepo;
	

	public Page<Comment> findAllByOfferID(Long id, Pageable page) throws Exception {
		Optional<Offer> offer = offerRepo.findById(id);
		
		if (offer.isPresent()) {
			return repo.findAllByOfferID(id, page);
		}
		throw new Exception("Offer with the given ID does not exist.");
	}

	public Comment create(Long id, CommentDTO commentDTO, MultipartFile imageFile) throws Exception {
		
		
		if (commentDTO.getText().isEmpty()) {
			throw new Exception("Comment cannot be empty");
		}
		
		Comment comment = new Comment();
		comment.setText(commentDTO.getText());
		
		Offer offer = offerRepo.findOneByID(id);
		
		if (offer == null) {
			throw new Exception("Offer with passed ID does not exist");
		}
		
		Random random = new Random();
		int num = random.nextInt(100000);
		String path;
		
		// if file sent is empty, i.e., the comment is sent without the picture
		if (imageFile.isEmpty()) {
			path = null;	// then the path to the picture is null
		}
		else {
	
			path = "src/main/resources/images/commentpicture" + num + ".jpg";
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
		
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		comment.setUser((RegisteredUser) user);
		
		return repo.save(comment);
		
	}


	public Comment updateComment(Long commentId, CommentDTO commentDTO, MultipartFile imageFile) throws Exception {
		
		Optional<Comment> comm = repo.findById(commentId);
		Comment c;
		if (!comm.isPresent()) {
			throw new Exception("Comment with given id does not exist.");
		}
		
		c = comm.get();
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		if (commentDTO.getText().isEmpty()) {
			throw new Exception("Comment cannot be empty.");
		}
		
		if (!c.getUser().getID().equals(user.getID())) {
			throw new Exception("You can update only your comments.");
		}
			
		c.setText(commentDTO.getText());
		
		String path = c.getImagePath();
		
		// if the update does not contain an image
		if (imageFile.isEmpty()) {
			// if there previously was an image, we delete it
			if (path != null) {
				File file = new File(path);
				file.delete();
			}
			// and set the path to null
			c.setImagePath(null);
		}
		else {
			// if there is an image, and previously there was not one, we create a new path to it
			if (path == null) {
				Random random = new Random();
				int num = random.nextInt(100000);
				path = "src/main/resources/images/commentpicture" + num + ".jpg";
				
			}
	
			// if not, we override the old picture with the new
			File file = new File(path);

			try (OutputStream os = new FileOutputStream(file)) {
				os.write(imageFile.getBytes());
			} catch (FileNotFoundException e1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
			} catch (IOException e1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
			}
			// and set the image path if everything is alright
			c.setImagePath(path);
		}
		
		
		repo.save(c);
		return c;
	
	}
	
	
	public void deleteComment(Long commentId) throws Exception {
		
		Optional<Comment> c = repo.findById(commentId);
		
		if (c.isPresent()) {
			
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!c.get().getUser().getID().equals(user.getID())) {
				throw new Exception("You can delete only your comments.");
			}
			
			String fileName = c.get().getImagePath();
			// if there was an image in the comment, with the deletion of the comment, the image is also deleted
			if (fileName != null) {
				File image = new File(fileName);
				image.delete();
			}
			repo.deleteById(commentId);
			return;
		}
		throw new Exception("Comment with given id does not exits.");
	}
	
}
