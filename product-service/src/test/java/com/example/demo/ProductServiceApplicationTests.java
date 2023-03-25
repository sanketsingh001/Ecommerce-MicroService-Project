package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.example.demo.dto.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
 
//we are going to using this mongoDBContainer for our integration test.
@Container
static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));


//Here we are autowiring the mockmvc from the servlet package which will be used for integration testing

@Autowired
private MockMvc mockMvc;



//This converts POJO object to JSON Back and forth 
//Used because the .content function only accepts string but we have JSON with us so..

@Autowired
private ObjectMapper objectMapper;


///As we are doing an integration test we have to set the properties regarding database here while doing integration test
//We are using mongodb docker container thats why.


@DynamicPropertySource
static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
	dymDynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
}


//Now we are going to write the test for the application

//Firstly we want to send the data in requestBody of product request type to create new product 
//and save it in database


//This behind the scene uses the mock mvc object to make request to the controller


	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		
		String productRequestString =objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
		        .andExpect(status().isCreated());
				
		        
	}
	
	
	
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Samsung")
				.description("Phone")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
