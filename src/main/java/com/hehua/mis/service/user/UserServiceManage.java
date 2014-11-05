package com.hehua.mis.service.user;

import com.google.common.collect.Lists;
import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.data.domain.JSONRole;
import com.peaceful.auth.data.domain.JSONSystem;
import com.peaceful.auth.data.domain.JSONUser;
import com.peaceful.auth.exception.CreateSessionException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date 14-10-9.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
@Component("userServiceManage")
public class UserServiceManage {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceManage.class);

    static AuthService authService;

    static {
        try {
            authService = AuthServiceImpl.getAuthService();
        } catch (CreateSessionException e) {
            logger.error(e + "");
        }
    }

    public List<JSONUser> findUsers(){
       JSONSystem system =  authService.getSystem();
        Set<JSONUser> users = new HashSet<JSONUser>();
        for (JSONRole role:system.roles){
            users.addAll(role.users);
        }
        return  Lists.newArrayList(users);
    }
}
