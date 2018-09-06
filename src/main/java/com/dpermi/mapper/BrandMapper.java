package com.dpermi.mapper;

import com.dpermi.domain.Brand;
import com.dpermi.domain.PermissionObject;
import com.dpermi.domain.search.BaseSearch;
import com.dpermi.domain.search.DataPermissionSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/24.
 */
@Mapper
public interface BrandMapper {
    List<PermissionObject> getBrands(DataPermissionSearch search);
    List<Brand> getAll();
    int getBrandsCount(DataPermissionSearch search);
}
