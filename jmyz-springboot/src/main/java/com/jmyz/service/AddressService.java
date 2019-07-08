package com.jmyz.service;

import com.jmyz.entity.Address;
import com.jmyz.utils.ResponseData;

public interface AddressService {
    ResponseData createOrUpdate(Address address);
    ResponseData list(String createAmountId);
    ResponseData detail(String addressId);
}
