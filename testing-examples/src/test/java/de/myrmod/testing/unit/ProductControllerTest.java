package de.myrmod.testing.unit;

import de.myrmod.testing.Controller.ProductController;
import de.myrmod.testing.Model.Product;
import de.myrmod.testing.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// since @MockBean is deprecated and will be removed in Spring Boot 3.6
	@MockitoBean
	private ProductService productService;

	@Test
	public void testGetAllProducts() throws Exception {
		// Arrange
		when(productService.getAllProducts()).thenReturn(Arrays.asList(
			new Product() {{
				setId(1L);
				setName("Product 1");
			}},
			new Product() {{
				setId(2L);
				setName("Product 2");
			}}
		));

		// Act & Assert
		mockMvc.perform(get("/api/products")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$[0].name").value("Product 1"));
	}

	@Test
	public void testCreateProduct() throws Exception {
		// Arrange
		Product newProduct = new Product();
		newProduct.setName("New Product");

		when(productService.saveProduct(Mockito.any(Product.class)))
			.thenReturn(new Product() {{
				setId(1L);
				setName("New Product");
			}});

		// Act & Assert
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"New Product\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("New Product"));
	}
}
