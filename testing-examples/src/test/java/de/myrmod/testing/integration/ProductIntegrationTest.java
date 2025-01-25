package de.myrmod.testing.integration;

import de.myrmod.testing.Model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

	@Test
	public void testGetProductById() {
		int productId = 1;
		ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/{id}", Product.class, productId);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(productId);
	}

	@Test
	public void testUpdateProduct() {
		int productId = 1;
		Product updatedProduct = new Product();
		updatedProduct.setName("Updated Laptop");

		HttpEntity<Product> request = new HttpEntity<>(updatedProduct);
		ResponseEntity<Void> response = restTemplate.exchange(
			"/api/products/{id}", HttpMethod.PUT, request, Void.class, productId);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		// verify the update by fetching the product again
		ResponseEntity<Product> getResponse = restTemplate.getForEntity("/api/products/{id}", Product.class, productId);
		assertThat(getResponse.getBody()).isNotNull();
		assertThat(getResponse.getBody().getName()).isEqualTo("Updated Laptop");
	}

	@Test
	public void testDeleteProduct() {
		int productId = 1;

		restTemplate.delete("/api/products/{id}", productId);

		// Optionally, verify deletion by fetching the product (expecting null or 404)
		ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/{id}", Product.class, productId);
		assertThat(response.getStatusCode().is4xxClientError()).isTrue();
	}

	@Test
	public void testExchange() {
		HttpEntity<Void> request = new HttpEntity<>(null);
		ResponseEntity<Product[]> response = restTemplate.exchange(
			"/api/products", HttpMethod.GET, request, Product[].class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
	}
}
