package com.jtjava.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jtjava.crud.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>  {

}
