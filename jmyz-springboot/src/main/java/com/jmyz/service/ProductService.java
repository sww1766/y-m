package com.jmyz.service;

import com.jmyz.entity.Product;
import com.jmyz.entity.ProductBuyGroup;
import com.jmyz.entity.ProductFw;
import com.jmyz.entity.ProductType;
import com.jmyz.utils.ResponseData;

public interface ProductService {
    ResponseData createProduct(Product product);
    ResponseData queryProductList(Product product);
    ResponseData queryProductDetail(Product product);

    ResponseData createProductType(ProductType productType);
    ResponseData manageProductType(ProductType productType);
    ResponseData queryProductTypeList(String productTypeName);
    ResponseData queryProductTypeDetail(ProductType productType);

    ResponseData createProductBuyGroup(ProductBuyGroup productBuyGroup);
    ResponseData queryProductBuyGroupList(Product product);
    ResponseData queryProductBuyGroupDetail(ProductBuyGroup productBuyGroup);

    ResponseData createProductFw(ProductFw productFw);
    ResponseData queryProductFwList(Product product);
    ResponseData queryProductFwDetail(ProductFw productFw);
}
