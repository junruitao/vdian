package vdian.invoker;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import vdian.model.CatListResponse;
import vdian.model.ItemListResponse;
import vdian.model.ItemResponse;
import vdian.utility.MyGsonHttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VdianExport {

	private static final Logger log = LoggerFactory.getLogger(VdianExport.class);

	public static void main(String args[]) throws Exception {
		String userId = "334916496"; //"710672440";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MyGsonHttpMessageConverter());
		String url = "http://wd.koudai.com/wd/cate/getList?param=%7B%22userID%22%3A"
				+ userId + "%7D";

		// using uri instead of url makes it work
		URI uri = new URI(url);

		CatListResponse catlogs = restTemplate.getForObject(uri,
				CatListResponse.class);

		for (vdian.model.CatListResponse.Result cat : catlogs.result) {
			url = "http://wd.koudai.com/wd/cate/getItems?param="+ URLEncoder.encode("{\"userID\":" + userId +",\"cate_id\":"
					+ cat.cate_id + ",\"limitStart\":0,\"limitNum\":100}", "UTF-8");
			uri = new URI(url);
			ItemListResponse items = restTemplate.getForObject(uri,
					ItemListResponse.class);
			cat.items = items;
			
			for (vdian.model.ItemListResponse.Result item:items.result){
				
				url = "http://item.weidian.com/wd/item/getPubInfo?param="+URLEncoder.encode("{\"itemID\":"+item.itemID+",\"page\":1}", "UTF-8");
				uri = new URI(url);
				ItemResponse itemInfo = restTemplate.getForObject(uri,
						ItemResponse.class);
				item.itemInfo = itemInfo;
			}
//			break;
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File("c:\\usr\\projects\\vdian\\export.json"), catlogs);
		System.out.println(mapper.writeValueAsString(catlogs));
	}
}