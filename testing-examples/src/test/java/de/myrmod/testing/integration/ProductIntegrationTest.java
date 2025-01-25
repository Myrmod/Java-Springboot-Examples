package de.myrmod.testing.integration;

import de.myrmod.testing.Model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllProducts() {
		ResponseEntity<Product[]> response = restTemplate.getForEntity("/api/products", Product[].class);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void testCreateProduct() {
		Product newProduct = new Product();
		newProduct.setName("Laptop");

		ResponseEntity<Product> response = restTemplate.postForEntity("/api/products", newProduct, Product.class);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Laptop");
	}
}
