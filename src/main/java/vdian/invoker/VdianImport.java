package vdian.invoker;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vdian.model.CatListResponse;
import vdian.model.CatListResponse.Result;
import vdian.service.VdianService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VdianImport {

	private static final Logger log = LoggerFactory
			.getLogger(VdianImport.class);

	public static void main(String args[]) throws Exception {

		// https://api.vdian.com/token?grant_type=client_credential&appkey=632604&secret=71b50f36cef0c5849ca6f88c349f99c5
		ObjectMapper mapper = new ObjectMapper();
		CatListResponse cats = mapper
				.readValue(new File("c:\\usr\\projects\\vdian\\export.json"),
						CatListResponse.class);

		for (Result cat : cats.result) {
			log.info(cat.cate_name);
		}

		List<String> names = Stream.of(cats.result).map(o -> o.cate_name).skip(0).limit(10)
				.collect(Collectors.toList());
		
		VdianService service  = new VdianService();
		service.addCates(names);
		

	}
}