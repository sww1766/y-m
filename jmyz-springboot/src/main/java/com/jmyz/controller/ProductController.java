package com.jmyz.controller;

import com.jmyz.entity.Product;
import com.jmyz.entity.ProductBuyGroup;
import com.jmyz.entity.ProductFw;
import com.jmyz.entity.ProductType;
import com.jmyz.service.ProductService;
import com.jmyz.utils.ResponseData;
import com.jmyz.utils.validator.group.ProductTypeGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "产品相关接口", tags = "产品，产品类别，产品服务，产品组合接口，post操作需要token，get操作不需要")
@Log4j2
@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 创建产品
     */
    @RequiresRoles("admin")
    @ApiOperation(value = "创建产品", notes = "创建产品")
    @PostMapping(value = "createProduct")
    public ResponseData createProduct(Product product) {
        return productService.createProduct(product);
    }


    /**
     * 查询产品列表
     */
    @ApiOperation(value = "查询产品列表", notes = "查询产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "page",
                    value = "当前页码(page)",
                    example = "1",
                    dataType = "int",
                    required = true),
            @ApiImplicitParam(
                    name = "pageSize",
                    value = "每页展示条数(pageSize)",
                    example = "10",
                    dataType = "int",
                    required = true)
    })
    @GetMapping(value = "queryProductList")
    public ResponseData queryProductList(Product product) {
        return productService.queryProductList(product);
    }

    /**
     * 查询产品详情
     */
    @ApiOperation(value = "查询产品详情,入参 id或code", notes = "查询产品详情")
    @GetMapping(value = "queryProductDetail")
    public ResponseData queryProductDetail(Product product) {
        return productService.queryProductDetail(product);
    }


    /**
     * 创建产品类别
     */
    @RequiresRoles("admin")
    @ApiOperation(value = "创建产品类别", notes = "创建产品类别")
    @PostMapping(value = "createProductType")
    public ResponseData createProductType(@Validated(value = ProductTypeGroup.class) ProductType productType) {
        return productService.createProductType(productType);
    }

    /**
     * 上下架、设置热门产品类别
     */
    @RequiresRoles("admin")
    @ApiOperation(value = "上下架、设置热门产品类别", notes = "上下架、设置热门产品类别")
    @PostMapping(value = "manageProductType")
    public ResponseData manageProductType(@Validated(value = ProductTypeGroup.class) ProductType productType) {
        return productService.manageProductType(productType);
    }

    /**
     * 查询产品类别列表
     */
    @ApiOperation(value = "查询产品类别列表", notes = "查询产品类别列表")
    @GetMapping(value = "queryProductTypeList")
    public ResponseData queryProductTypeList(String productTypeName) {
        return productService.queryProductTypeList(productTypeName);
    }

    /**
     * 查询产品类别详情
     */
    @ApiOperation(value = "查询产品类别详情,入参 id", notes = "查询产品类别详情")
    @GetMapping(value = "queryProductTypeDetail")
    public ResponseData queryProductTypeDetail(ProductType productType) {
        return productService.queryProductTypeDetail(productType);
    }

    /**
     * 创建产品购买组合分类
     */
    @RequiresRoles("admin")
    @ApiOperation(value = "创建产品购买组合分类", notes = "创建产品购买组合分类")
    @PostMapping(value = "createProductBuyGroup")
    public ResponseData createProductBuyGroup(ProductBuyGroup productBuyGroup) {
        return productService.createProductBuyGroup(productBuyGroup);
    }

    /**
     * 查询产品购买组合分类列表
     */
    @ApiOperation(value = "查询产品购买组合分类列表", notes = "查询产品购买组合分类列表")
    @GetMapping(value = "queryProductBuyGroupList")
    public ResponseData queryProductBuyGroupList(Product product) {
        return productService.queryProductBuyGroupList(product);
    }

    /**
     * 查询产品购买组合分类详情
     */
    @ApiOperation(value = "查询产品购买组合分类详情,入参 id", notes = "查询产品购买组合分类详情")
    @GetMapping(value = "queryProductBuyGroupDetail")
    public ResponseData queryProductBuyGroupDetail(ProductBuyGroup productBuyGroup) {
        return productService.queryProductBuyGroupDetail(productBuyGroup);
    }

    /**
     * 创建产品服务
     */
    @RequiresRoles("admin")
    @ApiOperation(value = "创建产品服务", notes = "创建产品服务")
    @PostMapping(value = "createProductFw")
    public ResponseData createProductFw(ProductFw productFw) {
        return productService.createProductFw(productFw);
    }

    /**
     * 查询产品服务列表
     */
    @ApiOperation(value = "查询产品服务列表", notes = "查询产品服务列表")
    @GetMapping(value = "queryProductFwList")
    public ResponseData queryProductFwList(Product product) {
        return productService.queryProductFwList(product);
    }

    /**
     * 查询产品服务详情
     */
    @ApiOperation(value = "查询产品服务详情,入参 id", notes = "查询产品服务详情")
    @GetMapping(value = "queryProductFwDetail")
    public ResponseData queryProductFwDetail(ProductFw productFw) {
        return productService.queryProductFwDetail(productFw);
    }


}
