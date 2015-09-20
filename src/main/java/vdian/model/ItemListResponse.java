package vdian.model;

import java.io.Serializable;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemListResponse implements Serializable{
	public Result[] result;
	public Status status;

	public static class Result
	{
		public Result(){};
	    public String collectCount;

	    public String item_collect_count;

	    public String itemName;

	    public String a_time;

	    public String itemID;

	    public String status;

	    public String e_time;

	    public String edit_time;

	    public String img;

	    public String src_img;

	    public String endTime;

//	    public String[] sku;

	    public String h5url;

	    public String discount;

	    public String cateTop;

	    public Cates[] cates;

	    public String startTime;

	    public String edittime;

	    public String price;

	    public String stock;

	    public String isEditorChoice;

	    public String is_seckill;

	    public String priceKill;

	    public String isTop;

	    public String sold;
	    
	    public ItemResponse itemInfo;
	}
	
	public static class Cates
	{
	    public String sort_num;

	    public String cate_id;

	    public String cate_name;
	}

}
