package ktsnwt_tim8.demo.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class RestResponsePage<T> extends PageImpl<T>{

	  private static final long serialVersionUID = 3248189030448292002L;
	  
	  public RestResponsePage(List<T> content, Pageable pageable, long total) {
	    super(content, pageable, total);
	    // TODO Auto-generated constructor stub
	  }

	  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	    public RestResponsePage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
	                    @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
	                    @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
	                    @JsonProperty("numberOfElements") int numberOfElements) {
	        super(content, PageRequest.of(number, size), totalElements);
	    }
	  
	  
	  public RestResponsePage(List<T> content, int number, int size, int totalElements) {
		  super(content, PageRequest.of(number, size), totalElements);  
	  }
	  
	  public RestResponsePage(List<T> content) {
	    super(content);
	    // TODO Auto-generated constructor stub
	  }

	  /* PageImpl does not have an empty constructor and this was causing an issue for RestTemplate to cast the Rest API response
	   * back to Page.
	   */
	  public RestResponsePage() {
	    super(new ArrayList<>());
	  }

	} 
