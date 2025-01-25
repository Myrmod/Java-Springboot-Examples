package de.myrmod.testing.slice;

import de.myrmod.testing.Model.Product;
import de.myrmod.testing.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testSaveAndFindProduct() {
		// Arrange
		Product product = new Product();
		product.setName("Test Product");

		// Act
		productRepository.save(product);
		List<Product> products = productRepository.findAll();

		// Assert
		assertThat(products).hasSize(1);
		assertThat(products.get(0).getName()).isEqualTo("Test Product");
	}
}
