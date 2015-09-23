package vdian.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatListResponse extends VdianResponse implements Serializable{

	public Result[] result;

	public static class Result {
		public Result(){};
		
		public String sort_num;

		public String cate_id;

		public String cate_item_num;

		public String add_time;

		public String cate_name;
		
		public ItemListResponse items;
	}

}
