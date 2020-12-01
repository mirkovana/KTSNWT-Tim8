package ktsnwt_tim8.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
		if (repo.existsById(commentId)) {
		    repo.deleteById(commentId);
		    return;
		}
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
	
	
	
}
