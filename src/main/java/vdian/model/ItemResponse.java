package vdian.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	public Result result;
	public Status status;

	public static class Result implements Serializable {
		private static final long serialVersionUID = 1L;
		public Result(){};
		public String is_score;

		public String collectCount;

		public String express_fee;

		public String itemID;

		public String seckill_or_discount;

		public String remote_free_delivery;

		public String isDelete;

		public String stock;

		public String allowBuy;

		public String lock_status;

		public String sold;

		public String shop_img;

		public String is_active_limit;

		public String[] thumbImgs;

		// public String sku;

		public String sf_cod;

		public String discount;

		public String is_fx;

		public String price;

		public String update_time;

		public String is_warrant;

		public String buy_stock;

		public String free_delivery;

//		public String[] titles;

		public String seller_id;

		public String front_cover;

		public String is_kdpay;

		public String credit_num;

		public String isClose;

		public String addTime;

		public List<String> Imgs;

		public String is_COD;

		public Voice voice;

		public String allowAddCart;

		public String credit_score;

		public String rate;

		public String discount_price;

		public String next_page;

		public String credit_type;

		public String itemName;

		public String is_active;

		public Extend extend;

		public String is_liutao;

		public String shop_logo;

		public String remote_area;

		public String shop_name;

		public String itemLogo;

		public String sf_cod_rate;

		public String can_gift;

		public String shop_url;

	}

	public static class Voice {
		public String seconds;

		public String url;
	}

	public static class Extend {
		public String total_score;

		public String score;

		public String total_number;
	}
}
