/**
 *
 */
package com.hehua.mis.controller;

import com.hehua.framework.subscribe.ZookeeperPubSubService;
import com.hehua.freeorder.action.params.DeliveryParam;
import com.hehua.freeorder.dao.FreeOrderDAO;
import com.hehua.freeorder.model.FreeOrderModel;
import com.hehua.freeorder.service.FreeOrderService;
import com.hehua.freeorder.value.FreeOrderValue;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.domain.Item;
import com.hehua.item.domain.enums.FreeFlashEnums;
import com.hehua.item.service.FreeFlashService;
import com.hehua.item.service.ItemService;
import com.hehua.item.service.VoteFlashSessionLocalCache;
import com.hehua.mis.service.unit.FreeFlashAdapterService;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.Pagination;
import com.hehua.mis.utils.Response;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.order.dao.DeliveryCompanyDAO;
import com.hehua.order.info.DeliveryCompanyInfo;
import com.hehua.order.service.DeliveryService;
import com.hehua.user.domain.User;
import com.hehua.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 */
@Controller
public class VoteController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Inject
    private DeliveryService deliveryService;

    @Autowired
    private FreeFlashService freeFlashService;

    @Autowired
    private FreeFlashAdapterService freeFlashAdapterService;

    @Autowired
    private FreeOrderService freeOrderService;

    @Autowired
    private FreeOrderDAO freeOrderDAO;

    @Autowired
    private ItemService itemService;

    @Inject
    private DeliveryCompanyDAO deliveryCompanyDAO;

    @Autowired
    @Qualifier("userService")
    private UserService userService_;


    @RequestMapping(value = "/vote/list", method = {RequestMethod.GET})
    public ModelAndView showUserListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("vote/list");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(40, pageNo);
        List<FreeFlash> freeFlashList = freeFlashService.getFreeListByPage(pagination.getStartIndex(), 40);
        pagination.setCount(freeFlashService.getFreeListCount());
        modelAndView.addObject("freeFlashList", freeFlashList);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @RequestMapping(value = "/vote/audit/{state}", method = {RequestMethod.GET})
    public ModelAndView showApplyUserListPage(@PathVariable int state, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("vote/applyList");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(40, pageNo);
        List<FreeFlash> freeFlashList = freeFlashService.getFreeListByStateAndPage(state, pagination.getStartIndex(), 40);
        pagination.setCount(freeFlashService.getFreeListCountByState(state));
        modelAndView.addObject("freeFlashList", freeFlashList);
        modelAndView.addObject("pagination", pagination);
        if (state == 3) {
            modelAndView.setViewName("vote/sendList");
        }
        if (state == 4)
            modelAndView.setViewName("vote/appreiseList");
        return modelAndView;
    }


    @RequestMapping(value = "/vote/{itemid}/status", method = {RequestMethod.POST})
    public void updateFreeFlashAuditPassStatus(@PathVariable("itemid") int itemid, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        FreeFlash freeFlash = freeFlashService.getFreeFlashByItemId(itemid);
        int quantity = freeFlash.getFreequantity();
        int passUserNum = freeOrderService.getPassAuitCountByItemId(itemid);
        if (quantity > passUserNum) {
            jsonResponse.setMessage(-1, "免费发放的商品数目大于审核的人数，需要您在审核一些用户");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (freeFlashService.updateFreeFlashApplyStatusById(freeFlash.getId(), FreeFlashEnums.WAIT_SEND_GOOD.getCode())) {
            jsonResponse.setMessage(0, "更新状态成功");
        } else {
            jsonResponse.setMessage(-1, "更新失败");
        }

        ResponseUtils.output(response, jsonResponse);
        return;
    }

    @RequestMapping(value = "/vote/{itemid}/develivery", method = {RequestMethod.POST})
    public void updateFreeFlashDeliveryStatus(@PathVariable("itemid") int itemid, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        FreeFlash freeFlash = freeFlashService.getFreeFlashByItemId(itemid);
        int quantity = freeFlash.getFreequantity();
        int passUserNum = freeOrderService.getSignedCountByItemId(itemid);
        if (quantity > passUserNum) {
            jsonResponse.setMessage(-1, "免费发放的商品数目大于已经发货的人数，需要全部用户将商品签收后才能执行此操作");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (freeFlashService.updateFreeFlashApplyStatusById(freeFlash.getId(), FreeFlashEnums.SEND_GOOD.getCode())) {
            jsonResponse.setMessage(0, "更新状态成功");
        } else {
            jsonResponse.setMessage(-1, "更新失败");
        }

        ResponseUtils.output(response, jsonResponse);
        return;
    }


    @RequestMapping(value = "/vote/status/{freeid}/{status}", method = {RequestMethod.POST})
    public void updateStatus(@PathVariable("freeid") int freeid,
                             @PathVariable("status") int status,
                             HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        FreeFlash freeFlash = freeFlashService.getFreeFlashNotContainStatusById(freeid);
        if (freeFlash == null || freeFlash.getStatus() > 2) {
            jsonResponse.setMessage(-1, "非法请求操作");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (freeFlashService.updateFreeFlashApplyStatusById(freeid, status)) {
            ZookeeperPubSubService.getInstance().post(VoteFlashSessionLocalCache.KEY, "reload");
            jsonResponse.setMessage(0, "更新状态成功");
        } else {
            jsonResponse.setMessage(-1, "更新失败");
        }

        ResponseUtils.output(response, jsonResponse);
        return;
    }

    @RequestMapping(value = "/vote/setting/{itemid}", method = {RequestMethod.POST})
    public void addVote(@PathVariable("itemid") int itemid,
                        @RequestParam(value = "startTime", required = true) String startTime,
                        @RequestParam(value = "endTime", required = true) String endTime,
                        @RequestParam(value = "freeQuantity", required = true) int freeQuantity,
                        HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        if (StringUtils.isEmpty(startTime)) {
            jsonResponse.setMessage(-1, "开始时间不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (StringUtils.isEmpty(endTime)) {
            jsonResponse.setMessage(-1, "结束时间不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (freeQuantity <= 0) {
            jsonResponse.setMessage(-1, "赠送商品数量不能小于0");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (freeFlashAdapterService.createFreeFlash(itemid, freeQuantity, startTime, endTime)) {
            ZookeeperPubSubService.getInstance().post(VoteFlashSessionLocalCache.KEY, "reload");
            jsonResponse.setMessage(0, "成功");
        } else {
            jsonResponse.setMessage(-1, "插入数据失败或者已经添加众测商品");
        }
        ResponseUtils.output(response, jsonResponse);

        return;
    }


    @RequestMapping(value = "/vote/audit/apply/{itemid}", method = {RequestMethod.GET})
    public ModelAndView getAuditApplyList(@PathVariable("itemid") int itemid,
                                          HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("vote/detail");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(40, pageNo);
        List<FreeOrderValue> freeOrderInfoList = freeOrderService.getFreeUserInfoByItemId(itemid, pagination.getStartIndex(), pagination.getSize());
        Integer applyNum = freeOrderService.getCountByItemId(itemid);
        pagination.setCount(applyNum);
        modelAndView.addObject("orderInfoList", freeOrderInfoList);
        modelAndView.addObject("orderAuditInfo", freeOrderService.getAduitPassUserInfoByItemId(itemid));
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("applyNum", applyNum);
        modelAndView.addObject("itemid", itemid);
        Item item = itemService.getItem_(itemid);
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    @RequestMapping("/vote/getUsersOfNewApply")
    public String getApplyPeople(HttpServletRequest request){
        List<User> users = freeOrderService.getNewApplyUser();
        request.setAttribute("users",users);
        return "vote/newApplyUsers";
    }

    @RequestMapping(value = "/vote/freeorder/{freeorderid}/{itemid}/{status}", method = {RequestMethod.POST})
    public void updateFreeOrderStatus(@PathVariable("freeorderid") int freeorderid,
                                      @PathVariable("status") int status,
                                      @PathVariable("itemid") int itemid,
                                      HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        FreeFlash freeFlash = freeFlashService.getFreeFlashByItemId(itemid);
        int quantity = freeFlash.getFreequantity();
        int passUserNum = freeOrderService.getPassAuitCountByItemId(itemid);
        if (passUserNum >= quantity) {
            jsonResponse.setMessage(-1, "审核通过的数目大于免费发放商品数量");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        FreeOrderModel freeOrderModel = freeOrderService.getById(freeorderid);
        if (FreeOrderModel.STATUS_APPRVOE1 == status) {
            if(freeOrderService.setApprove(freeOrderModel)) {
                jsonResponse.setMessage(0, "更新状态成功");
            } else {
                jsonResponse.setMessage(-1, "更新订单失败");
            }
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (FreeOrderModel.STATUS_REJECT1 == status) {
            if(freeOrderService.setReject(freeOrderModel)) {
                jsonResponse.setMessage(0, "更新状态成功");
            } else {
                jsonResponse.setMessage(-1, "更新订单状态失败");
            }
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        jsonResponse.setMessage(-1, "更新失败");
        ResponseUtils.output(response, jsonResponse);
        return;
    }


    @RequestMapping(value = "/vote/audit/appraise/{freeorderid}", method = {RequestMethod.GET})
    public String getAuditApplyList(@PathVariable("freeorderid") int freeorderid, HttpServletRequest request) {
        FreeOrderValue freeOrderValue = freeOrderService.getFreeOrderByFreeOrderId(freeorderid);
        request.setAttribute("freeOrderInfo", freeOrderValue.getFreeOrderInfo());
        request.setAttribute("item", freeOrderValue.getItem());
        request.setAttribute("itemAppraise", freeOrderValue.getItemAppraise());
        return "vote/orderDetail";
    }

    @RequestMapping(value = "/order/updateDelivery/{freeOrderId}", method = RequestMethod.GET)
    public String setDeliveryPre(HttpServletRequest request, @PathVariable("freeOrderId") int freeOrderId) {
        request.setAttribute("freeOrderId", freeOrderId);
        List<DeliveryCompanyInfo> deliveryCompanyInfos = deliveryCompanyDAO.getSupportedList();
        request.setAttribute("deliveryCompanyInfos", deliveryCompanyInfos);
        return "orders/updateDelivery";
    }

    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public void setSign(FreeOrderModel freeOrderModel) {
        freeOrderService.setSign(freeOrderModel);
        Response.jsonResponse(1, "设为已签收成功");
    }

    @RequestMapping(value = "/vote/setdeliveried/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void setDeliveried(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int orderid) {
        String deliveryComPinyin = request.getParameter("deliveryComPinyin");
        String deliveryNum = org.apache.commons.lang.StringUtils.trim(request.getParameter("deliveryNum"));
        FreeOrderModel order = freeOrderDAO.getById(orderid);
        DeliveryParam param = new DeliveryParam();
        param.setDeliveryCompPinyin(deliveryComPinyin);
        param.setDeliveryNum(deliveryNum);
        boolean result = freeOrderService.setDelivery(order, param);
        if (result) {
             Response.jsonResponse(1, "操作成功");
            return;

        } else {
             Response.jsonResponse(0, "失败");
            return;

        }
    }

    @RequestMapping(value = "/vote/setsigned/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public void setSigned(@PathVariable("id") int orderid) {
        FreeOrderModel order = freeOrderDAO.getById(orderid);
        boolean result = freeOrderService.setSign(order);
        if (result) {
             Response.jsonResponse(1, "操作成功");
            return;

        } else {
             Response.jsonResponse(0, "失败");
            return;
        }
    }


}
