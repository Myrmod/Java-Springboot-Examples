package de.myrmod.testing.Repository;

import de.myrmod.testing.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
