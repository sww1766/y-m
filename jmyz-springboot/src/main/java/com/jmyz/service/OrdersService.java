package com.jmyz.service;

import com.jmyz.entity.Orders;
import com.jmyz.utils.ResponseData;

public interface OrdersService {
    ResponseData create(Orders orders);
    ResponseData list(Orders orders);
    ResponseData detail(Orders orders);
}
