package com.jmyz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmyz.entity.Accounts;
import com.jmyz.entity.Address;
import com.jmyz.mapper.AddressMapper;
import com.jmyz.service.AddressService;
import com.jmyz.utils.ResponseData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Log4j2
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public ResponseData createOrUpdate(Address address) {
        Accounts accounts = (Accounts) SecurityUtils.getSubject().getPrincipal();

        //插入
        if(StringUtils.isBlank(address.getAddressId())){
            address.setAddressId(UUID.randomUUID().toString().replaceAll("-", ""));
            address.setRecTime(new Date());
            address.setCreateAccountId(accounts.getAccountId());
            address.setDataStatus(1);
            addressMapper.insert(address);
        }else{
            address.setRecTime(new Date());
            address.setCreateAccountId(accounts.getAccountId());
            addressMapper.updateById(address);
        }

        return new ResponseData<>(0, "创建或修改收货地址成功");
    }

    @Override
    public ResponseData list(String createAmountId) {
        Accounts accounts = (Accounts) SecurityUtils.getSubject().getPrincipal();

        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.lambda().eq(Address::getCreateAccountId, accounts.getAccountId());

        return new ResponseData<>(0, addressMapper.selectList(addressQueryWrapper));
    }

    @Override
    public ResponseData detail(String addressId) {
        Accounts accounts = (Accounts) SecurityUtils.getSubject().getPrincipal();

        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.lambda().eq(Address::getCreateAccountId, accounts.getAccountId()).
                eq(Address::getAddressId,addressId);

        return new ResponseData<>(addressMapper.selectList(addressQueryWrapper).stream().findFirst().
                filter(ad -> ad.getAddressId() != null).get());
    }


}
