package vdian.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAddResponse extends VdianResponse implements Serializable{

	private static final long serialVersionUID = 1L;

}
