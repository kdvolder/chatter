package com.example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value="chatter-banner-service", fallback=BannerServiceOffline.class)
public interface BannerService {

	@RequestMapping(method=RequestMethod.GET, value="/")
	String convert(@RequestParam(value="text") String text);

}
