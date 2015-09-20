package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import vdian.utility.MyGsonHttpMessageConverter;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MyGsonHttpMessageConverter());
        String url = "http://gturnquist-quoters.cfapps.io/api/random";
        url = "http://www.w3schools.com/website/Customers_MYSQL.php";
		Quote quote = restTemplate.getForObject(url, Quote.class);
        log.info(quote.toString());
    }
}