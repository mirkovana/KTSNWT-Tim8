package ktsnwt_tim8.demo.dto;

import java.util.List;

public class FilterDTO {

	private String title;
	private String place;
	private List<Long> subcatIDs;
	
	public FilterDTO() {}

	
	public FilterDTO(String title, String place, List<Long> catIDs, List<Long> subcatIDs) {
		super();
		this.title = title;
		this.place = place;
		this.subcatIDs = subcatIDs;
	}

	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public List<Long> getSubcatIDs() {
		return subcatIDs;
	}
	public void setSubcatIDs(List<Long> subcatIDs) {
		this.subcatIDs = subcatIDs;
	}
	
	
	
}
