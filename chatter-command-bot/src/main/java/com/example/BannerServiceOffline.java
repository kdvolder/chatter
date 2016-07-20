package com.example;

import org.springframework.stereotype.Component;

@Component
public class BannerServiceOffline implements BannerService {

	@Override
	public String convert(String argument) {
		return "BANNER SERVICE OFFLINE";
	}

}
