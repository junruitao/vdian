package vdian.service;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import vdian.model.AddCatalogParam;
import vdian.model.CatAddResponse;
import vdian.model.PubParam;

public class VdianService {
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(VdianService.class);

	static final String access_token = "c029db1b72b61e613ee4f783297e162e0002331548";

	static final String baseAddress = "api.vdian.com";
	
	static final String ADD_CATE = "vdian.shop.cate.add";

	RestTemplate restTemplate;

	private PubParam pubParam = new PubParam();

	public VdianService() {
		pubParam.setAccess_token(access_token);
		restTemplate = new RestTemplate();
//		restTemplate.getMessageConverters().add(
//				new MyGsonHttpMessageConverter());
	}

	public void addCates(List<String> names) throws Exception {
		pubParam.setMethod(ADD_CATE);
		AddCatalogParam param = new AddCatalogParam(pubParam, names);

		URI uri = new URI("https", baseAddress, "/api",param.toUrl(), "");

		logger.info(uri.toString());

		CatAddResponse catlogs = restTemplate.getForObject(uri,
				CatAddResponse.class);

		logger.info("" + catlogs.status.status_code
				+ catlogs.status.status_reason);

	}

}
