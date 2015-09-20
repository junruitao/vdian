package vdian.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.client.RestTemplate;

import vdian.model.CatListResponse;
import vdian.utility.MyGsonHttpMessageConverter;

public class HttpClientVdian {
	private static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException, URISyntaxException {
		String url = "http://wd.koudai.com/wd/cate/getList?param=%7B%22userID%22%3A334916496%7D";

		HttpClient client = HttpClientBuilder.create().build();

		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("wd.koudai.com")
				.setPath("/wd/cate/getList")
				.setParameter("param", "{\"userID\":334916496}");
		URI uri = builder.build();
		uri = new URI(url);
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream instream = entity.getContent();
		 RestTemplate restTemplate = new RestTemplate();
		 restTemplate.getMessageConverters().add(new MyGsonHttpMessageConverter());
	        
		 CatListResponse resp = restTemplate.getForObject(uri, CatListResponse.class);
		System.out.println(read(instream));

		//
	}
}
