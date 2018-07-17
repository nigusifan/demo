package net.intelink.zmframework.model;

import java.io.Serializable;

public class ImageResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String imgUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
