package com.jmyz.utils.shiro;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jmyz.entity.Accounts;
import com.jmyz.entity.sys.SysRole;
import com.jmyz.service.AccountsService;
import com.jmyz.utils.redis.RedisUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 认证
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AccountsService accountsService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Accounts user = (Accounts) principals.getPrimaryPrincipal();

        //角色列表
        //取admin角色
        Object object = redisUtil.get("ROLELIST-" + user.getAccountId());
        List<SysRole> roles;
        if(Objects.isNull(object)){
            roles = accountsService.getSysRole(user.getAccountId());
        }else {
            roles = JSONObject.parseArray(object.toString()).toJavaList(SysRole.class);
        }

        return new SimpleAuthorizationInfo(roles.stream().map(SysRole::getRoleName).
                collect(Collectors.toSet()));
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token不正确");
        }

        //查询用户信息
        QueryWrapper<Accounts> accountsQueryWrapper = new QueryWrapper<>();
        accountsQueryWrapper.lambda().eq(Accounts::getLoginMobile, username).or().
                eq(Accounts::getLoginName, username);
        Accounts accounts = accountsService.getAccountsByUserName(username);

        //账号不存在
        if (accounts == null) {
            throw new UnknownAccountException("账号不存在");
        }

        if (!JWTUtil.verify(token, username, accounts.getLoginPwd())) {
            throw new AuthenticationException("账号或密码错误");
        }

        //账号锁定
        if (accounts.getDataStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        return new SimpleAuthenticationInfo(accounts, token, getName());
    }

}
