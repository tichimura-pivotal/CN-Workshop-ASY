package io.pivotal;

import java.util.Collections;

import io.pivotal.domain.City;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableCircuitBreaker
public class CloudNativeSpringUiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CloudNativeSpringUiApplication.class, args);
	}

//	@FeignClient("https://cloud-native-spring")
	@FeignClient(name = "cloud-native-spring", fallback = CityClientFallback.class)
	public interface CityClient {

		@RequestMapping(method=RequestMethod.GET, value="/cities", consumes="application/hal+json")
		Resources<City> getCities();
	}
		
	@Component
	public class CityClientFallback implements CityClient {
		@Override
		public Resources<City> getCities() {
			//We'll just return an empty response
		return new Resources(Collections.EMPTY_LIST);
		}
	}
	
	
}

