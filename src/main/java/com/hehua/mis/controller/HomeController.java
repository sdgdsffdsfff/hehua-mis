/**
 * 
 */
package com.hehua.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehua.item.model.ItemResult;
import com.hehua.item.service.*;
import com.hehua.item.utils.BabyUtils;
import com.hehua.mis.utils.HttpSession;
import com.hehua.mis.utils.MisConstants;
import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.exception.CreateSessionException;
import com.peaceful.web.util.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.item.domain.Category;
import com.hehua.item.service.logic.ItemManageService;
import com.hehua.mis.service.unit.ItemManageAdapterService;
import com.hehua.mis.service.unit.MaterialService;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.user.service.UserService;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 28, 2012 11:01:51 AM
 */
@Controller
public class HomeController {

    private static final String PAGE_HOME = "home/home";

    @Autowired
    CategoryService categoryService;

    @Autowired
    MaterialService materialService;

    @Autowired
    BrandService brandService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    CrowedService crowedService;

    @Autowired
    GenderService genderService;

    @Autowired
    PurchaseAddressService purchaseAddressService;

    @Autowired
    ItemManageService itemManageService;

    @Autowired
    UserService userService;

    @Autowired
    ItemManageAdapterService itemManageAdapterService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRecommendService itemRecommendService;


    @RequestMapping({ "/home", "/", "" })
    public ModelAndView showHomePage(HttpServletRequest request) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        request.setAttribute("user",authService.getUser(HttpSession.session(MisConstants.CURRENT_USER)));
        ModelAndView modelAndView = new ModelAndView("../../index");
        return modelAndView;
    }

    @RequestMapping("/goods")
    public ModelAndView goodsManage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home/goodsManage");
        modelAndView.addObject("categoryList", categoryService.getAllCategory());

        modelAndView.addObject("materialList", materialService.getAll());
        modelAndView.addObject("brandList", brandService.getAllBrand());
        modelAndView.addObject("warehouseList", warehouseService.getAllWarehouse());
        modelAndView.addObject("crowedList", crowedService.getAllCrowd());
        modelAndView.addObject("genderList", genderService.getAllGender());
        modelAndView.addObject("purchaseList", purchaseAddressService.getAllPurchaseAddress());
        modelAndView.addObject("darenList", userService.getDarenList().getData());

        return modelAndView;
    }

    @RequestMapping("/item/goodEdit/{id}")
    public ModelAndView goodEdit(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("item/goodEdit");

        modelAndView.addObject("warehouseList", warehouseService.getAllWarehouse());
        modelAndView.addObject("purchaseList", purchaseAddressService.getAllPurchaseAddress());
        modelAndView.addObject("darenList", userService.getDarenList().getData());

        ItemResult itemResult = itemService.getItem(id);
        itemResult = itemManageAdapterService.setItemDataEntry(itemResult);
        modelAndView.addObject("itemResult", itemResult);
        modelAndView.addObject("crows", BabyUtils.convertIdToStr(itemResult.getItem().getCrowedid(), crowedService.getAllCrowd()));
        modelAndView.addObject("gender", BabyUtils.convertGenderIdToStr(itemResult.getItem().getGenderid(), genderService.getAllGender()));
        modelAndView.addObject("material", materialService.getBy(itemResult.getItem().getMaterialid()));
        modelAndView.addObject("itemRecommend", itemRecommendService.getItemRecommendByItemid(itemResult.getItem().getId()).get(0));

        return modelAndView;
    }

    @RequestMapping(value = "/goods/category", method = { RequestMethod.POST })
    public void getCategory(HttpServletRequest request, HttpServletResponse response) {
        int cateId = ServletRequestUtils.getIntParameter(request, "cateId", 0);
        JsonResponse jsonResponse = new JsonResponse();
        if (cateId <= 0) {
            jsonResponse.setMessage(-1, "cateId为空");
        } else {
            Category category = categoryService.getCategoryBy(cateId);
            jsonResponse.setMessage(0, "成功");
            jsonResponse.data("skuArray", itemManageAdapterService.getItemWithSKuProperty(Long
                    .valueOf(category.getId()).intValue()));
        }

        ResponseUtils.output(response, jsonResponse);
    }

    @RequestMapping("/flow")
    public ModelAndView flowManage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home/flowManage");
        return modelAndView;
    }

}
