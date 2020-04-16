package com.springboot.microservice.microservices_item.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.springboot.microservice.microservices_item.RestTemplateConfig;
import com.springboot.microservice.microservices_item.response.ResponseRestTemplate;
import com.springboot.microservice.servicec_commons.model.entities.Product;

@Component
public class RestTemplateDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateDto.class);
	private static final String ENTRY_METHOD_MESSAGE = "entry in method {}.";
	private static final String GET_METHOD_MESSAGE = "get method {} {}.";

	@Autowired
	RestTemplateConfig restTemplateConfig;

	public List<ResponseRestTemplate> obtainInformationAllProducts() {

		LOGGER.info(GET_METHOD_MESSAGE, "obtainInformationAllProducts", "for get info.");

		LOGGER.info(GET_METHOD_MESSAGE, "http://192.168.99.100:8090/api/product/obtainInformationAllProducts",
				"for get info.");

		List<ResponseRestTemplate> productsList = Arrays.asList(restTemplateConfig.clientRestTemplate().getForObject(
				"http://192.168.99.100:8090/api/product/obtainInformationAllProducts", ResponseRestTemplate.class));

		productsList.removeIf(prod -> prod.getProducts().isEmpty());
		return productsList;
	}

	public ResponseRestTemplate obtainInformationByProductId(Long productId) {

		LOGGER.info(GET_METHOD_MESSAGE, "obtainProductInformation", " for get info.");
		Map<String, String> param = new HashMap<>();
		param.put("productId", productId.toString());
		LOGGER.info(GET_METHOD_MESSAGE, "/obtainProductInformation/{productId}", "for get info.");
		ResponseRestTemplate product = restTemplateConfig.clientRestTemplate().getForObject(
				"http://192.168.99.100:8090/api/product/productId/{productId}/obtainProductInformation",
				ResponseRestTemplate.class, param);
		return product;
	}

	public Product createNewProduct(Product product) {

		LOGGER.info(GET_METHOD_MESSAGE, "createNewProduct", "for create create product.");

		HttpEntity<Product> params = new HttpEntity<>(product);

		LOGGER.info(GET_METHOD_MESSAGE, "createNewProduct", "for get info.");

		ResponseEntity<ResponseRestTemplate> response = restTemplateConfig.clientRestTemplate().exchange(
				"http://192.168.99.100:8090/api/product/createNewProduct", HttpMethod.POST, params,

				ResponseRestTemplate.class);
		return response.getBody().getProduct();

	}

	public void deleteProduct(Long productId) {

		LOGGER.info(GET_METHOD_MESSAGE, " deleteProduct", " for delete product.");

		Map<String, String> param = new HashMap<>();
		param.put("productId", productId.toString());
		LOGGER.info(GET_METHOD_MESSAGE, "http://http://192.168.99.100:8090/api/product/deleteProduct/{productId}",
				"for get info.");
		restTemplateConfig.clientRestTemplate()
				.delete("http://192.168.99.100:8090/api/product/deleteProduct/{productId}", param);

	}

}
