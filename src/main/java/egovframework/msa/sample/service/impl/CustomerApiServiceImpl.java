package egovframework.msa.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import egovframework.msa.sample.service.CustomerApiService;

@Service
public class CustomerApiServiceImpl implements CustomerApiService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(fallbackMethod = "getCustomerDetailFallback") // fallback 메소드는 customers 서비스가 에러 또는 지연될 경우 곧 바로 fallback 메소드를 호출하여 에러 전파를 방지한다.
	public String getCustomerDetail(String customerId) {
		throw new RuntimeException("I/O Exception"); // customers 서비스에 강제로 Exception 발생
		// return restTemplate.getForObject("http://localhost:8082/customers/" + customerId, String.class);
	}
	
	public String getCustomerDetailFallback(String customerId, Throwable ex) {
		System.out.println("Error:" + ex.getMessage());
		return "고객정보 조회가 지연되고 있습니다.";
	}

}
