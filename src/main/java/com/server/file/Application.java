package com.server.file;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.server.file.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public HttpMessageConverters fastJsonHttpMessageConverters() {
//		// 1.定义一个converters转换消息的对象
//		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//		// 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		// 3.在converter中添加配置信息
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//		// 4.将converter赋值给HttpMessageConverter
//		HttpMessageConverter<?> converter = fastConverter;
//		// 5.返回HttpMessageConverters对象
//		return new HttpMessageConverters(converter);
//	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		List<MediaType> types = new ArrayList<>();
		types.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(types);
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
		converter.setFastJsonConfig(config);
		return new HttpMessageConverters(converter);
	}
}
