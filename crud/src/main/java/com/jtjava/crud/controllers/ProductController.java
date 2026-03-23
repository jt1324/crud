package com.jtjava.crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtjava.crud.dtos.ProductsDto;
import com.jtjava.crud.model.Product;
import com.jtjava.crud.repositories.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    // get all
    @GetMapping
    public ResponseEntity getAll(){
        List<Product> listProducts = repository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Integer id){

        Optional product = repository.findById(id);

        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");

        }

        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
        
    }

    // save
    @PostMapping
    public ResponseEntity save(@RequestBody ProductsDto dto) {

        var product = new Product();
        BeanUtils.copyProperties(dto, product);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        Optional <Product> product = repository.findById(id);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        repository.delete(product.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value="id") Integer id, @RequestBody ProductsDto dto) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(dto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));
    }



}
