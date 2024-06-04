package com.crm.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name="images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductImage extends BaseEntity{

    @Column(name="data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
