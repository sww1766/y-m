package com.jmyz.service;

import com.jmyz.entity.Accounts;
import com.jmyz.entity.sys.SysRole;
import com.jmyz.utils.ResponseData;

import java.util.List;

public interface AccountsService {

    ResponseData register(Accounts accounts);

    ResponseData login(String mobileOrLoginName, String password);

    Accounts getAccountsByUserName(String userName);

    List<SysRole> getSysRole(String accountId);
}
