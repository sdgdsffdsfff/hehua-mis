/**
 * 
 */
package com.hehua.mis.dao;

import java.util.List;

import javax.inject.Named;

import com.hehua.mis.domain.Acluser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zhihua
 *
 */
@Named("misUserDAO")
public interface AcluserDAO {

    @Insert("insert into `users` (`username`, `password`, `nickname`, `email`, `enabled`, `created_time`, `salt`, `role`) values (#{username}, #{password}, #{nickname}, #{email}, #{enabled}, #{createdTime}, #{salt}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int addUser(Acluser acluser);

    @Select("select `id`, `username`, `password`, `nickname`, `email`, `enabled`, `created_time`, `salt`, `role` from `users`")
    public List<Acluser> getUsers();

    @Select("select `id`, `username`, `password`, `nickname`, `email`, `enabled`, `created_time`, `salt`, `role` from `users` where `id` = #{id}")
    public Acluser getById(int id);

    @Select("select `id`, `username`, `password`, `nickname`, `email`, `enabled`, `created_time`, `salt`, `role` from `users` where `username` = #{username}")
    public Acluser getByUsername(String username);

    @Update("update `users` set `enabled` = #{enabled} where `id` = #{id}")
    public void updateUser(Acluser acluser);

    @Update("update `users` set `password` = #{password} where `id` = #{id}")
    public void changePassword(Acluser acluser);

    @Update("update `users` set `role` = #{role} where `id` = #{id}")
    public void changeRole(Acluser acluser);
}
