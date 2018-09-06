package com.dpermi.controller;

import com.dpermi.core.JsonResult;
import com.dpermi.dao.BrandDao;
import com.dpermi.domain.Brand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by val620@126.com on 2018/8/9.
 */
@RequestMapping("/brand")
@RestController
public class BrandController {
    @Autowired
    private BrandDao brandDao;

    @ApiOperation(value = "获取所有品牌")
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public JsonResult getAll() throws Exception {
        List<Brand> brands = brandDao.getAll();
        JsonResult result=new JsonResult(200,"ok",brands);
        return result;
    }
}
