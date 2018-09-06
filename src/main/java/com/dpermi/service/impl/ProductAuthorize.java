package com.dpermi.service.impl;

import com.dpermi.core.PageModel;
import com.dpermi.domain.PermissionObject;
import com.dpermi.domain.search.DataPermissionSearch;
import com.dpermi.mapper.BrandMapper;
import com.dpermi.service.IAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by val620@126.com on 2018/7/23.
 */
@Component
public class ProductAuthorize implements IAuthorize {
    @Autowired
    private BrandMapper brandMapper;
    private static BrandMapper staticBrandMapper;

    @PostConstruct
    public void init() {
        staticBrandMapper = this.brandMapper;
    }

    public PageModel getDataObjects(DataPermissionSearch search){
        if(search.getDataType()!=1) return null;

        List<PermissionObject> objects = staticBrandMapper.getBrands(search);
        int count=staticBrandMapper.getBrandsCount(search);
        PageModel model=new PageModel(objects,count);
        return model;
    }
}
