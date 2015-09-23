package vdian.service;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import vdian.model.CatAddResponse;
import vdian.model.CatListResponse;
import vdian.model.CatListResponse.Result;
import vdian.model.ImageUploadResponse;
import vdian.model.ProductAddResponse;
import vdian.model.PubParam;
import vdian.model.VdianParam;
import vdian.model.VdianParam.AddProductParam;
import vdian.utility.MyGsonHttpMessageConverter;

public class VdianService {

	private static final Logger logger = LoggerFactory
			.getLogger(VdianService.class);

	static final String access_token = "25fed65189ba16a9b81e07b5f615aa050002331548";

	static final String baseAddress = "api.vdian.com";

	static final String ADD_CATE = "vdian.shop.cate.add";
	static final String GET_CATE = "vdian.shop.cate.get";

	static final String localImgFolder = "/tmp/vdian/";
	
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

		if (newNames == null || newNames.isEmpty()) {
			logger.warn("None added!");
			return;
		}

		// Adding new ones
		pubParam.setMethod(ADD_CATE);
		VdianParam vdianParam = new VdianParam(pubParam)
				.addParam(new VdianParam.AddCateParam(newNames));

		URI uri = prepareURI(vdianParam);

		logger.info(uri.toString());

		CatAddResponse catlogs = restTemplate.getForObject(uri,
				CatAddResponse.class);

		logger.info("" + catlogs.status.status_code
				+ catlogs.status.status_reason);

	}

	private URI prepareURI(VdianParam vdianParam) {
		try {
			return new URI("https", baseAddress, "/api", vdianParam.toUrl(), "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	public void copyProducts(List<vdian.model.ItemListResponse.Result> items) throws Exception {
		items.forEach(item -> {
			AddProductParam addProductParam = new AddProductParam(item.price,
					item.itemName);
			item.itemInfo.result.Imgs.forEach(url -> {
				String target = copyImage(url);
				addProductParam.addImg(target);
			});
			
			VdianParam vdianParam = new VdianParam(pubParam).addParam(addProductParam);
			URI uri = prepareURI(vdianParam);

			logger.info(uri.toString());

			ProductAddResponse protuct = restTemplate.getForObject(uri,
					ProductAddResponse.class);

			logger.info("" + protuct.status.status_code
					+ protuct.status.status_reason);
		});
	}

	public void copyFile(String url) {
		logger.info(url);
		url = StringUtils.substringBeforeLast(url, "?");
		String fileName = StringUtils.substringAfterLast(url, "/");
		try {
			FileUtils.copyURLToFile(new URL(url), new File(localImgFolder + fileName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public String copyImage(String url) {
		logger.info(url);
		
		// 1. download to local
		url = StringUtils.substringBeforeLast(url, "?");
		String fileName = StringUtils.substringAfterLast(url, "/");
		try {
			FileUtils.copyURLToFile(new URL(url),new File(localImgFolder+fileName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// upload to remote
		// URL Parameters
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("file", new FileSystemResource(localImgFolder+fileName));
	    parts.add("access_token", access_token);
	      		// Post
	    ImageUploadResponse rtn = restTemplate.postForObject("https://api.vdian.com/media/upload", parts, ImageUploadResponse.class);
	    
	    if (rtn.status.status_code != 0)
	    	throw new RuntimeException(rtn.status.status_reason);
	    
		return rtn.result;
		
	}

}
