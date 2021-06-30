package com.appdeveloperblog.estore.ProductService.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productlookup")
public class ProductLookupEntity implements Serializable {

    private static final long serialVersionUID = 4868341255654966928L;

    @Id
    private String productId;

    @Column(unique = true)
    private String title;
}
