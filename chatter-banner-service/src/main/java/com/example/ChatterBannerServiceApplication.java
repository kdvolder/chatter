package com.example;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.lalyos.jfiglet.FigletFont;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class ChatterBannerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatterBannerServiceApplication.class, args);
	}

	@GetMapping(value="/", produces="text/plain; charset=utf-8")
	public String banner(@RequestParam(defaultValue="Toronto 2017") String text) throws IOException {
		return FigletFont.convertOneLine(text);
	}

}
