package com.crm.pfe.repository;

import com.crm.pfe.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Integer> {
}
