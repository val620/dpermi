package com.dpermi.mapper;

import com.dpermi.domain.Product;
import com.dpermi.domain.search.ProductSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Mapper
public interface ProductMapper {

    List<Product> getProductsByName(ProductSearch search);

    int getProductsByNameCount(ProductSearch search);
    
    Product getProduct(String id);

    int addProduct(Product product);

    int updateProduct(Product product);
}
