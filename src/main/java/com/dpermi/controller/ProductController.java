package com.dpermi.controller;

import com.dpermi.common.DataPermission;
import com.dpermi.common.UserService;
import com.dpermi.core.JsonResult;
import com.dpermi.core.PageModel;
import com.dpermi.core.Permission;
import com.dpermi.dao.BrandDao;
import com.dpermi.dao.ProductDao;
import com.dpermi.domain.Brand;
import com.dpermi.domain.Product;
import com.dpermi.domain.search.ProductSearch;
import com.dpermi.domain.search.UserSearch;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Permission(name = "产品", parentId = "")
@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;

    @ApiOperation(value = "根据id获取产品")
    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    public JsonResult getProduct(String id) throws Exception {
        Product product = productDao.getProduct(id);
        JsonResult result = new JsonResult(200, "ok", product);
        return result;
    }

    @Permission(name = "搜索", parentId = "product")
    @ApiOperation(value = "获取产品")
    @RequestMapping(value = "/getProducts", method = RequestMethod.POST)
    public JsonResult getProducts(ProductSearch search) throws Exception {
        DataPermission data = new DataPermission();
        String[] brandIds = data.getObjectIds(1);
        search.setPermiBrands(brandIds);
        PageModel model = productDao.getProductsByName(search);
        JsonResult result = new JsonResult(200, "ok", model);
        return result;
    }

    @Permission(name = "添加", parentId = "product")
    @ApiOperation(value = "添加产品")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public JsonResult addProduct(Product product) throws Exception {
        int val = productDao.addProduct(product);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "修改", parentId = "product")
    @ApiOperation(value = "更新产品")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public JsonResult updateProduct(Product product) throws Exception {
        int val = productDao.updateProduct(product);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "品牌下拉框", parentId = "product")
    @ApiOperation(value = "获取产品")
    @RequestMapping(value = "/getBrands", method = RequestMethod.POST)
    public JsonResult getBrands() throws Exception {
        List<Brand> brands = brandDao.getAll();
        boolean admin = UserService.getCurrentUser().isAdmin();
        if (!admin) {
            DataPermission data = new DataPermission();
            String[] brandIds = data.getObjectIds(1);

            if (brandIds != null && brandIds.length > 0) {
                List<String> brandIdList = Arrays.asList(brandIds);
                brands = brands.parallelStream().filter(x -> brandIdList.contains(x.getBrandId())).collect(Collectors.toList());
            } else {
                brands = new ArrayList<>();
            }
        }
        JsonResult result = new JsonResult(200, "ok", brands);
        return result;
    }
}
