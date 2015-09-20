package vdian.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatAddResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Status status;

}
