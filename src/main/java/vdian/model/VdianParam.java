package vdian.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class VdianParam {

	private static final Logger logger = LoggerFactory
			.getLogger(VdianParam.class);

	Map<String, Object> params = new HashMap<String, Object>();
	public PubParam pubParam;
	Object specialParam;

	public VdianParam(PubParam pubParam) {
		params.put("public", pubParam);
	}

	public VdianParam addParam(String name, Object value) {
		params.put(name, value);
		return this;
	}

	public VdianParam addParam(Object value) {
		return addParam("param", value);
	}

	/***
	 * Used for adding catalog
	 * @author frank
	 *
	 */
	static public class AddCateParam {
		public Cate cates[];

		public AddCateParam(List<String> cates) {
			this.cates = new Cate[cates.size()];
			for (int i = 0; i < this.cates.length; i++) {
				this.cates[i] = new Cate();
				this.cates[i].cate_name = cates.get(i);
			}
		}
	};

	static public class AddProductParam {
		List<String> imgs = new ArrayList<String>();
		int stock = 20;
		String price;
		String item_name;
		

		public AddProductParam(String price, String name) {
			this.price = price;
			item_name = name;
		}
		public void addImg(String url){
			imgs.add(url);
		}
	};

	static public class AddItemParam {
		List<String> imgs = new ArrayList<String>();
		int stock = 100;
		String price;
		String item_name;
		
	}
	
	static public class Cate {
		public String cate_name;
	};

	public String toUrl() throws Exception {
		Gson gson = new Gson();

		String strParam = params.entrySet().stream()
				.map(e -> e.getKey() + "=" + gson.toJson(e.getValue()))
				.collect(Collectors.joining("&"));
		// params = k +"=" + gson.toJson(v);

		logger.info(strParam);

		return strParam;// URLEncoder.encode(params, "UTF-8");
	}

}
