package de.myrmod.testing.unit;

import de.myrmod.testing.Model.Product;
import de.myrmod.testing.Repository.ProductRepository;
import de.myrmod.testing.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	// since @MockBean is deprecated and will be removed in Spring Boot 3.6
	@MockitoBean
	private ProductRepository productRepository;

	@Test
	public void testGetAllProducts() {
		// Arrange: Mock the repository's behavior
		when(productRepository.findAll()).thenReturn(Arrays.asList(
			new Product() {{
				setId(1L);
				setName("Product 1");
			}},
			new Product() {{
				setId(2L);
				setName("Product 2");
			}}
		));

		// Act: Call the service method
		List<Product> products = productService.getAllProducts();

		// Assert: Verify the result
		assertThat(products).hasSize(2);
		assertThat(products.get(0).getName()).isEqualTo("Product 1");
	}

	@Test
	public void testSaveProduct() {
		// Arrange
		Product product = new Product();
		product.setName("New Product");

		when(productRepository.save(Mockito.any(Product.class)))
			.thenReturn(new Product() {{
				setId(1L);
				setName("New Product");
			}});

		// Act
		Product savedProduct = productService.saveProduct(product);

		// Assert
		assertThat(savedProduct.getId()).isEqualTo(1L);
		assertThat(savedProduct.getName()).isEqualTo("New Product");
	}
}

