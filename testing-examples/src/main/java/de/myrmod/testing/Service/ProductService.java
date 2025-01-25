package de.myrmod.testing.Service;

import de.myrmod.testing.Model.Product;
import de.myrmod.testing.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public Product updateProduct(Long id, Product updatedProduct) {
		return productRepository.findById(id)
			.map(existingProduct -> {
				existingProduct.setName(updatedProduct.getName());
				return productRepository.save(existingProduct);
			})
			.orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
