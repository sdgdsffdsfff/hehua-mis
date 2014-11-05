package com.hehua.mis.service.unit;

import com.hehua.mis.item.dao.MaterialDAO;
import com.hehua.mis.item.domain.Material;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by hewenjerry on 14-8-21.
 */
@Component
public class MaterialService {
    @Autowired
    private MaterialDAO materialDAO;

    private static final Log logger = LogFactory.getLog(MaterialService.class);

    public int addBy(String name) {
        name = StringUtils.trim(name);
        Material material = materialDAO.getMaterialByName(name);
        if (material != null) {
            return material.getId();
        }
        Material shoppingMan = new Material();
        shoppingMan.setCts(new Date());
        shoppingMan.setUts(new Date());
        shoppingMan.setName(name);
        try {
            return materialDAO.addShoppingMan(shoppingMan);
        } catch (Exception e) {
            logger.error("materialDAO.add is error!",e);
            return -1;
        }
    }

    public Material getBy(int id) {
        try {
            return materialDAO.getById(id);
        } catch (Exception e) {
            logger.error("materialDAO.getBy is error!",e);
            return null;
        }
    }

    public List<Material> getAll() {
        try {
            return materialDAO.getAllMaterials();
        } catch (Exception e) {
            logger.error("materialDAO.getBy is error!",e);
            return null;
        }
    }

    public boolean updateBy(int id, String name) {
        Material shoppingMan = this.getBy(id);
        if (shoppingMan == null) {
            logger.error("materialDAO update not exist,by id=" + id);
            return false;
        }
        shoppingMan.setUts(new Date());
        shoppingMan.setName(name);
        try {
            materialDAO.updateShoppingMan(shoppingMan);
            return true;
        } catch (Exception e) {
            logger.error("materialDAO.update is error!",e);
            return false;
        }
    }

    public boolean deleteBy(int id) {
        try {
            materialDAO.delete(id);
            return true;
        } catch(Exception e) {
            logger.error("shoppingManDAO.delete is error!",e);
            return false;
        }
    }

}
