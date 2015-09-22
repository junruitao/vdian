package vdian.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import vdian.model.CatAddResponse;
import vdian.model.CatListResponse;
import vdian.model.CatListResponse.Result;
import vdian.model.PubParam;
import vdian.model.VdianParam;
import vdian.utility.MyGsonHttpMessageConverter;

public class VdianService {

	private static final Logger logger = LoggerFactory
			.getLogger(VdianService.class);

	static final String access_token = "7259ce2525e47a258d6cbc7eb4aa214b0002331548";

	static final String baseAddress = "api.vdian.com";

	static final String ADD_CATE = "vdian.shop.cate.add";
	static final String GET_CATE = "vdian.shop.cate.get";

	RestTemplate restTemplate;

	private PubParam pubParam = new PubParam();

	public VdianService() {
		pubParam.setAccess_token(access_token);
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MyGsonHttpMessageConverter());
	}

	public void addCates(List<String> names) throws Exception {
		
		// filtered out existing ones
		List<String> existingNames = Stream.of(getCates())
				.map(o -> o.cate_name).collect(Collectors.toList());
		List<String> newNames = names.stream()
				.filter((name) -> !existingNames.contains(name))
				.collect(Collectors.toList());

		if (newNames == null || newNames.isEmpty()){
			logger.warn("None added!");
			return;
		}
		
		// Adding new ones
		pubParam.setMethod(ADD_CATE);
		VdianParam vdianParam = new VdianParam(pubParam).addParam("param",
				new VdianParam.CateParam(newNames));

		URI uri = prepareURI(vdianParam);

		logger.info(uri.toString());

		CatAddResponse catlogs = restTemplate.getForObject(uri,
				CatAddResponse.class);

		logger.info("" + catlogs.status.status_code
				+ catlogs.status.status_reason);

	}

	private URI prepareURI(VdianParam vdianParam) throws URISyntaxException,
			Exception {
		return new URI("https", baseAddress, "/api", vdianParam.toUrl(), "");
	}

	public Result[] getCates() throws Exception {
		pubParam.setMethod(GET_CATE);
		VdianParam vdianParam = new VdianParam(pubParam);
		URI uri = prepareURI(vdianParam);

		logger.info(uri.toString());

		CatListResponse catlogs = restTemplate.getForObject(uri,
				CatListResponse.class);

		logger.info("" + catlogs.status.status_code
				+ catlogs.status.status_reason);

		return catlogs.result;
	}

	public void addItems(List<vdian.model.ItemListResponse.Result> items) {
		items.forEach(item->{
			item.itemInfo.result.Imgs.forEach(url->{
				addImages(url);
				
			});
		});
		// 1. upload each images
		
		// 2. set up item
		
	}

	public String addImages(String url) {
		return url;
		
	}


}
