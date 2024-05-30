package com.crm.pfe.model;

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
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
