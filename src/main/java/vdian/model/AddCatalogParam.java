package vdian.model;

import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vdian.invoker.VdianExport;

import com.google.gson.Gson;

public class AddCatalogParam {

	private static final Logger logger = LoggerFactory
			.getLogger(VdianExport.class);

	public PubParam pubParam;
	CateParam cateParam;

	public AddCatalogParam(PubParam pubParam, List<String> cates) {
		this.pubParam = pubParam;
		cateParam = new CateParam(cates);
	}

	static public class CateParam {
		public Cate cates[];

		public CateParam(List<String> cates) {
			this.cates = new Cate[cates.size()];
			for (int i = 0; i < this.cates.length; i++) {
				this.cates[i] = new Cate();
				this.cates[i].cate_name = cates.get(i);
			}
		}
	};

	static public class Cate {
		public String cate_name;
	};

	public String toUrl() throws Exception {
		Gson gson = new Gson();

		String params = "param=" + gson.toJson(cateParam) + "&public="
				+ gson.toJson(pubParam);

		logger.info(params);

		return params;//URLEncoder.encode(params, "UTF-8");
	}

}
