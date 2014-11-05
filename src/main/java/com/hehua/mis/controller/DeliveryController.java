package com.hehua.mis.controller;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.framework.config.ZookeeperConfigManager;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.order.constants.PaiyouConfig;
import com.hehua.order.dao.DeliveryCompanyDAO;
import com.hehua.order.model.DeliveryCompanyModel;
import com.hehua.order.service.DeliveryService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liuweiwei on 14-9-3.
 */
@Controller
@RequestMapping(value = "/delivery")
public class DeliveryController {

    @Inject
    DeliveryService deliveryService;

    @Inject
    DeliveryCompanyDAO deliveryCompanyDAO;

    @RequestMapping(value = "company/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("delivery/companylist");
        List<DeliveryCompanyModel> deliveryCompanys = deliveryService.getAllDeliveryCompany();

        mav.addObject("deliveryCompanys", deliveryCompanys);
        return mav;
    }

    @RequestMapping(value = "company/setsort", method = RequestMethod.POST)
    public void setSort(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String sort = request.getParameter("sort");
        JsonResponse jsonResponse = new JsonResponse();
        if (!NumberUtils.isNumber(id) || !NumberUtils.isNumber(sort)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "参数不全");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        DeliveryCompanyModel model = deliveryCompanyDAO.getById(Integer.parseInt(id));
        if (model == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "物流公司不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        model.setSort(Integer.parseInt(sort));
        deliveryService.setDeliveryCompanySort(model);
        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        ResponseUtils.output(response, jsonResponse);
    }

    @RequestMapping(value = "paiyou", method = RequestMethod.GET)
    public ModelAndView paiyouGet() {
        ModelAndView mav = new ModelAndView("delivery/paiyou");
        String sw = ZookeeperConfigManager.getInstance().getString(PaiyouConfig.SWITCH_KEY);
        if (sw != null && sw.equalsIgnoreCase("on")) {
            mav.addObject("switch", 1);
        } else {
            mav.addObject("switch", 0);
        }
        return mav;
    }

    @RequestMapping(value = "paiyou/{switch}", method = RequestMethod.POST)
    public void paiyouPost(@PathVariable("switch") String sw, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        if (sw.equalsIgnoreCase("on")) {
            ZookeeperConfigManager.getInstance().setString(PaiyouConfig.SWITCH_KEY, "on");
        } else if (sw.equalsIgnoreCase("off")) {
            ZookeeperConfigManager.getInstance().setString(PaiyouConfig.SWITCH_KEY, "off");
        }
        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功");
        ResponseUtils.output(response, jsonResponse);
    }
}
