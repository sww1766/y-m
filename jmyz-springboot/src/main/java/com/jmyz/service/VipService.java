package com.jmyz.service;

import com.jmyz.entity.Vip;
import com.jmyz.utils.ResponseData;

public interface VipService {

    ResponseData operaVip(Vip vip);

    ResponseData queryVip();
}
