package com.dpermi.dao;

import com.dpermi.core.PageModel;
import com.dpermi.domain.Brand;
import com.dpermi.domain.Product;
import com.dpermi.domain.search.ProductSearch;
import com.dpermi.mapper.BrandMapper;
import com.dpermi.mapper.ProductMapper;
import com.dpermi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Component
public class ProductDao {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;

    public PageModel getProductsByName(ProductSearch search) {
        List<Product> products = productMapper.getProductsByName(search);
        if(products!=null && products.size()>0){
            List<Brand> brands=brandMapper.getAll();
            products.forEach(x->{
                Brand brand=brands.parallelStream().filter(b->b.getBrandId().equals(x.getBrandId())).findFirst().get();
                if(brand!=null) x.setBrand(brand.getBrandName());
            });
        }
        int count=productMapper.getProductsByNameCount(search);
        PageModel model=new PageModel(products,count);
        return model;
    }

    public Product getProduct(String id) {
        return productMapper.getProduct(id);
    }

    public int addProduct(Product product) {
        product.setProductId(UUIDUtil.GetUUID());
        return productMapper.addProduct(product);
    }

    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }
    
}
