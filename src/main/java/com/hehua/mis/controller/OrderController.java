package com.hehua.mis.controller;


import com.hehua.commons.model.CommonMetaCode;
import com.hehua.commons.time.DateUtils;
import com.hehua.item.dao.ItemDAO;
import com.hehua.mis.item.dto.OrderDTO;
import com.hehua.mis.service.unit.OrderManageAdapterService;
import com.hehua.mis.service.unit.OrderManageService;
import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.Pagination;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.mis.utils.excel.DownLoadUtil;
import com.hehua.order.dao.DeliveryCompanyDAO;
import com.hehua.order.dao.OrdersDAO;
import com.hehua.order.enums.OrdersErrorEnum;
import com.hehua.order.info.DeliveryCompanyInfo;
import com.hehua.order.model.*;
import com.hehua.order.pay.BasePayProvider;
import com.hehua.order.service.DeliveryService;
import com.hehua.order.service.OrderService;
import com.hehua.order.service.OrdersService;
import com.hehua.order.service.RefundService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuweiwei on 14-8-27.
 */
@Controller
public class OrderController {

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private OrdersService ordersService;

    @Inject
    private OrderService orderService;

    @Inject
    private DeliveryCompanyDAO deliveryCompanyDAO;

    @Inject
    private DeliveryService deliveryService;

    @Inject
    private OrderManageService orderManageService;

    @Inject
    private RefundService refundService;

    @Inject
    private ItemDAO itemDAO;

    @Inject
    private OrderManageAdapterService orderManageAdapterService;

    private final int PAGE_SIZE = 40;

    public static HashMap<String, Integer> slug_status_map = new HashMap<String, Integer>() {
        {
            put("unpay", OrdersModel.STATUS_NEW);
            put("payed", OrdersModel.STATUS_PAYED);
            put("deliveried", OrdersModel.STATUS_DELIVERIED);
            put("signed", OrdersModel.STATUS_SIGNED);
            put("refunding", OrdersModel.STATUS_REFUNDING);
            put("refunded", OrdersModel.STATUS_REFUND);
            put("closed", OrdersModel.STATUS_CLOSED);
        }
    };
    @RequestMapping(value = "/orders/list/{slug}")
    public ModelAndView list(HttpServletRequest request, @PathVariable("slug") String slug) {
        ModelAndView mav = new ModelAndView("orders/list");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(PAGE_SIZE, pageNo);
        Integer status = this.slug_status_map.get(slug);
        List<OrdersModel> orders = null;
        if (status == null) {
            slug = "all";
            pagination.setCount(ordersDAO.getAllCount());
            orders = ordersDAO.getAll(pagination.getStartIndex(), pagination.getSize());
        } else {
            pagination.setCount(ordersDAO.getCountByOrderStatus(status));
            orders = ordersDAO.getByStatus(status, pagination.getStartIndex(), pagination.getSize());
        }
        mav.addObject("slug", slug);
        mav.addObject("orders", orders);
        mav.addObject("statusMap", OrdersModel.statusDesc);
        mav.addObject("payMap", BasePayProvider.payIdCNameMap);
        mav.addObject("pageNo", pagination.getNo());

        mav.addObject("pagination", pagination);
        return mav;
    }

    @RequestMapping(value = "/order/search")
    public ModelAndView orderSearchManager(HttpServletRequest request,
                                           @RequestParam(value = "itemName", required = false) String itemName,
                                           @RequestParam(value =  "minPrice", required = false) String minPrice,
                                           @RequestParam(value = "maxPrice", required = false) String maxPrice,
                                           @RequestParam(value = "startTime", required = false) String startTime,
                                           @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mav = new ModelAndView("orders/searchList");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(PAGE_SIZE, pageNo);
        List<OrdersModel> orders = null;
        startTime = startTime.trim();
        endTime = endTime.trim();
        minPrice = minPrice.trim();
        maxPrice = maxPrice.trim();
        itemName = itemName.trim();
        if (StringUtils.isEmpty(minPrice)) {
            minPrice = "0";
        }
        if (StringUtils.isEmpty(maxPrice)) {
            maxPrice = "999999";
        }
        int startUnix = 0, endUinx = 0;
        if (StringUtils.isNotEmpty(startTime)) {
            startUnix = DateUtils.getUnitxTimestamp(DateTimeUtil.formatStrToDate1(startTime));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            endUinx = DateUtils.getUnitxTimestamp(DateTimeUtil.formatStrToDate1(endTime));
        }

        itemName = StringUtils.trim(itemName);
        if (StringUtils.isEmpty(itemName)) {
            pagination.setCount(ordersDAO.getCountParmsBy(startUnix, endUinx, Integer.valueOf(minPrice), Integer.valueOf(maxPrice)));
            orders = ordersDAO.getByParamsBy(startUnix, endUinx, Integer.valueOf(minPrice), Integer.valueOf(maxPrice),
                    pagination.getStartIndex(), pagination.getSize());
        } else {
            pagination.setCount(1);
        }

        orders = orderManageAdapterService.filterModel(orders, itemName);

        mav.addObject("slug", "all");
        mav.addObject("orders", orders);
        mav.addObject("statusMap", OrdersModel.statusDesc);
        mav.addObject("payMap", BasePayProvider.payIdCNameMap);
        mav.addObject("pageNo", pagination.getNo());
        mav.addObject("itemName", itemName);
        mav.addObject("minPrice", minPrice);
        mav.addObject("maxPrice", maxPrice);
        mav.addObject("startTime", startTime);
        mav.addObject("endTime", endTime);
        mav.addObject("searchOrder", "true");

        mav.addObject("pagination", pagination);
        return mav;
    }

    @RequestMapping(value = "/orders/download/data")
    public void downLoadOrderData(HttpServletRequest request,
                                      HttpServletResponse response) {
        int startIndex = ServletRequestUtils.getIntParameter(request, "startIndex", 0);
        int endIndex = ServletRequestUtils.getIntParameter(request, "endIndex", 0);

        List<OrderDTO> orders = orderManageService.getOrderDetailBy(OrdersModel.STATUS_PAYED, startIndex, endIndex);
        DownLoadUtil.downLoad(orders, "report-" + DateTimeUtil.getCurrDateStr() + "-detail", response, "DOWNLOAD_ORDER_PAY_MONEY_LIST");
    }

    @RequestMapping(value = "/orders/detail/{id}")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long orderid) {
        OrdersModel order = ordersDAO.getById(orderid);
        if (order == null) {
            try {
                response.sendRedirect("/orders/list/all");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ModelAndView mav = new ModelAndView("orders/detail");
        mav.addObject("order", order);
        mav.addObject("orderStatusMap", OrdersModel.statusDesc);
        mav.addObject("payMap", BasePayProvider.payIdCNameMap);
        mav.addObject("invoiceMap", DeliveryInfoModel.invoiceDesc);
        mav.addObject("orderInfo", ordersService.genOrderInfo(order));
        if (order.hasPayed()) {
            mav.addObject("buyerAccount", orderService.getUserBuyAccount(order.getId()));
        }

        List<DeliveryCompanyInfo> deliveryCompanyInfos = deliveryCompanyDAO.getSupportedList();
        mav.addObject("deliveryCompanyInfos", deliveryCompanyInfos);

        if (order.hasRefund()) {
            mav.addObject("refundStatusMap", RefundModel.descStatus);
            mav.addObject("refundEventType", RefundLogModel.descType);
            mav.addObject("refundItemInfos", refundService.getRefundItemInfos(orderid));
        }
        return mav;
    }


    @RequestMapping(value = "/orders/setdeliveried/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void setDeliveried(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long orderid) {
        JsonResponse jsonResponse = new JsonResponse();
        String deliveryComPinyin = request.getParameter("deliveryComPinyin");
        String deliveryNum = org.apache.commons.lang.StringUtils.trim(request.getParameter("deliveryNum"));
        if (StringUtils.isBlank(deliveryComPinyin) || StringUtils.isBlank(deliveryNum)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "物流单号或物流公司不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (deliveryService.fillDeliveryInfo(orderid, OrderTraceModel.TYPE_DEFAULT, deliveryNum, deliveryComPinyin)) {
            jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
            ResponseUtils.output(response, jsonResponse);
        } else {
            jsonResponse.setMessage(OrdersErrorEnum.PARAM_ERROR.getCode(), OrdersErrorEnum.PARAM_ERROR.getMessage());
            ResponseUtils.output(response, jsonResponse);
        }
    }

    @RequestMapping(value = "/orders/setsigned/{id}", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public void setSigned(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long orderid) {
        JsonResponse jsonResponse = new JsonResponse();
        OrdersModel order = ordersDAO.getById(orderid);
        if (order == null || !order.isStatusDeliveried()) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "只有已发货的订单才能设为已签收");
            ResponseUtils.output(response, jsonResponse);
        }
        if (ordersService.setSigned(order)) {
            jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
            ResponseUtils.output(response, jsonResponse);
        } else {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), CommonMetaCode.Error.getMessage());
            ResponseUtils.output(response, jsonResponse);
        }
    }
}
