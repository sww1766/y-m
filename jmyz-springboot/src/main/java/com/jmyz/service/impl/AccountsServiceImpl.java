package com.jmyz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmyz.entity.Accounts;
import com.jmyz.entity.Scores;
import com.jmyz.entity.sys.SysRole;
import com.jmyz.mapper.AccountsMapper;
import com.jmyz.mapper.ScoresMapper;
import com.jmyz.mapper.SysRoleMapper;
import com.jmyz.mapper.VipMapper;
import com.jmyz.service.AccountsService;
import com.jmyz.utils.ResponseData;
import com.jmyz.utils.redis.RedisUtil;
import com.jmyz.utils.shiro.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AccountsService {

    @Resource
    private AccountsMapper accountsMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private VipMapper vipMapper;

    @Resource
    private ScoresMapper scoresMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public ResponseData register(Accounts accounts) {
        QueryWrapper<Accounts> accountsQueryWrapper = new QueryWrapper<>();
        accountsQueryWrapper.lambda().eq(Accounts::getLoginMobile, accounts.getLoginMobile()).or().
                eq(Accounts::getLoginName, accounts.getLoginName());

        Accounts accounts1 = accountsMapper.selectList(accountsQueryWrapper).stream().findFirst().orElse(null);

        if (accounts1 == null) {
            String accountId = UUID.randomUUID().toString().replaceAll("-", "");
            String recTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            accounts.setAccountId(accountId);
            accounts.setLoginPwd(new Sha256Hash(accounts.getLoginPwd()).toHex());
            accounts.setDataStatus(1);
            accounts.setRecTime(recTime);

            if (accountsMapper.insert(accounts) == 1) {
                //注册成功，则赠送500积分 score表
                Scores scores = new Scores();
                scores.setScoresId(UUID.randomUUID().toString().replaceAll("-",""));
                scores.setAccountId(accountId);
                scores.setDataStatus(1);
                scores.setRecTime(recTime);
                scores.setScoresAmounts(500d);
                scoresMapper.insert(scores);
                return new ResponseData<>(0, "注册成功");
            }else {
                return new ResponseData<>(100, "error: 注册失败");
            }
        } else {
            return new ResponseData<>(100, "error: 账户已存在，请直接登录");
        }
    }

    @Override
    public ResponseData login(String mobileOrLoginName, String password) {
        try{
            //sha256加密
            password = new Sha256Hash(password).toHex();
            QueryWrapper<Accounts> accountsQueryWrapper = new QueryWrapper<>();
            accountsQueryWrapper.lambda().eq(Accounts::getLoginMobile, mobileOrLoginName).or().
                    eq(Accounts::getLoginName, mobileOrLoginName);
            Accounts accounts1 = accountsMapper.selectList(accountsQueryWrapper).stream().findFirst().orElse(null);
            if(accounts1 == null){
                return new ResponseData<>(100, "登录失败：用户名不存在");
            }

            if(! accounts1.getLoginPwd().equals(password)){
                return new ResponseData<>(100, "登录失败：密码不正确");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseData<>(100, "登录失败：用户名或密码错误");
        }
        return new ResponseData(0, JWTUtil.sign(mobileOrLoginName, password));
    }

    @Override
    public Accounts getAccountsByUserName(String userName){
        QueryWrapper<Accounts> accountsQueryWrapper = new QueryWrapper<>();
        accountsQueryWrapper.lambda().eq(Accounts::getLoginMobile, userName).or().
                eq(Accounts::getLoginName, userName);
        return accountsMapper.selectList(accountsQueryWrapper).stream().findFirst().orElse(null);
    }

    @Override
    public List<SysRole> getSysRole(String accountId) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.lambda().eq(SysRole::getCreateAccountId,accountId);
        List<SysRole> roleList = sysRoleMapper.selectList(sysRoleQueryWrapper);
        redisUtil.set("ROLELIST-" + accountId,roleList,30000);
        return roleList;
    }
}
