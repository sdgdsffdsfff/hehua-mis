package com.hehua.mis.item.dao;

import com.hehua.mis.item.domain.Material;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hewenjerry on 14-8-19.
 */
public interface MaterialDAO {
    @Insert("insert into `material` (`name`, `cts`, `uts`) values (#{name}, #{cts}, #{uts})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int addShoppingMan(Material shoppingMan);

    @Select("select `id`, `name`, `cts`, `uts` from `material`")
    public List<Material> getAllMaterials();

    @Select("select `id`, `name`, `cts`, `uts` from `material` where name=#{name} limit 1")
    public Material getMaterialByName(@Param("name") String name);

    @Select("select `id`, `name`, `cts`, `uts` from `material` where `id` = #{id}")
    public Material getById(int id);

    @Update("update `material` set `name` = #{name} where `id` = #{id}")
    public void updateShoppingMan(Material shoppingMan);

    @Delete("delete from `material` where `id` = #{id}")
    public void delete(int id);
}
