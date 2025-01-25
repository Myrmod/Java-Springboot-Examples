package de.myrmod.testing.slice;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.myrmod.testing.Model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ProductJsonTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testSerializeProductToJson() throws Exception {
		// Arrange
		Product product = new Product();
		product.setId(1L);
		product.setName("Test Product");

		// Act
		String json = objectMapper.writeValueAsString(product);

		// Assert
		assertThat(json).contains("\"id\":1");
		assertThat(json).contains("\"name\":\"Test Product\"");
	}

	@Test
	public void testDeserializeJsonToProduct() throws Exception {
		// Arrange
		String json = "{\"id\":1,\"name\":\"Test Product\"}";

		// Act
		Product product = objectMapper.readValue(json, Product.class);

		// Assert
		assertThat(product.getId()).isEqualTo(1L);
		assertThat(product.getName()).isEqualTo("Test Product");
	}
}
