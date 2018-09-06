package com.dpermi.dao;

import com.dpermi.domain.Brand;
import com.dpermi.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by val620@126.com on 2018/8/9.
 */
@Component
public class BrandDao {
    @Autowired
    private BrandMapper brandMapper;

    public List<Brand> getAll(){
        return brandMapper.getAll();
    }
}
