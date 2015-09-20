package vdian.utility;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
    public MyFastJsonHttpMessageConverter() {
        List<MediaType> types = Arrays.asList(
                new MediaType("text", "html", UTF8),
                new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8)
        );
        super.setSupportedMediaTypes(types);
    }
}