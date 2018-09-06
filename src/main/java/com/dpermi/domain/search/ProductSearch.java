package com.dpermi.domain.search;

import lombok.Data;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Data
public class ProductSearch extends BaseSearch {
    private String productName;
    private String brandId;
    private String[] permiBrands;
}
