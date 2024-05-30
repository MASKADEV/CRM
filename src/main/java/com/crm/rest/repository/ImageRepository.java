package com.crm.rest.repository;

import com.crm.rest.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Integer> {
}
