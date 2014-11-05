/**
 * 
 */
package com.hehua.mis.service.user;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.hehua.mis.dao.AcluserDAO;
import com.hehua.mis.domain.Acluser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 3, 2013 11:48:59 AM
 */
@Component
public class AcluserManager implements UserDetailsService, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AcluserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Acluser acluser = userDAO.getByUsername(username);
        if (acluser == null) {
            throw new UsernameNotFoundException("username not found, username is " + username);
        }
        acluser.setAuthorities(Collections.<GrantedAuthority> singleton(new SimpleGrantedAuthority(
                acluser.getRole())));
        return acluser;
    }

    public void createUser(Acluser acluser) {
        userDAO.addUser(acluser);
    }

    public List<Acluser> getUsers() {
        return userDAO.getUsers();
    }

    public Acluser getUser(int userId) {
        return userDAO.getById(userId);
    }

    public void activeUser(int userId) {
        Acluser acluser = userDAO.getById(userId);
        if (acluser != null && !acluser.isEnabled()) {
            acluser.setEnabled(true);
            userDAO.updateUser(acluser);
        }
    }

    public void unactiveUser(int userId) {
        Acluser acluser = userDAO.getById(userId);
        if (acluser != null && acluser.isEnabled()) {
            acluser.setEnabled(false);
            userDAO.updateUser(acluser);
        }
    }

    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    public void changePassword(int userId, String rawPassword) {
        Acluser acluser = userDAO.getById(userId);
        String encodePassword = md5PasswordEncoder.encodePassword(rawPassword, acluser.getSalt());
        acluser.setPassword(encodePassword);
        userDAO.changePassword(acluser);
    }

    public void changeRole(int userId, String role) {
        Acluser acluser = userDAO.getById(userId);
        acluser.setRole(role);
        userDAO.changeRole(acluser);
    }
}
