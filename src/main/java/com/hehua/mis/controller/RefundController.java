package com.hehua.mis.controller;

import com.hehua.commons.model.CommonMetaCode;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.order.dao.RefundDAO;
import com.hehua.order.exceptions.NoSuchRefundActionException;
import com.hehua.order.model.RefundModel;
import com.hehua.order.refund.Approve1Action;
import com.hehua.order.refund.Approve2Action;
import com.hehua.order.refund.RefundActionManager;
import com.hehua.order.refund.RefundBase;
import com.hehua.order.refund.Reject1Action;
import com.hehua.order.refund.Reject2Action;
import com.hehua.order.refund.WithdrawAction;
import com.hehua.order.refund.params.ResultParam;
import com.peaceful.auth.config.AUTH;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by liuweiwei on 14-8-29.
 */
@Controller
public class RefundController {

    @Inject
    private RefundDAO refundDAO;

    @Inject
    private RefundActionManager refundActionManager;

    @AUTH.Function("refund")
    @RequestMapping(value = "/refund/approve1/{id}")
    public void approve(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        JsonResponse jsonResponse = new JsonResponse();
        RefundModel refund = refundDAO.getById(id);
        if (refund == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "退款不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        Approve1Action action = null;
        try {
            action = (Approve1Action)refundActionManager.getAction(RefundBase.ACTION_APPROVE1);
        } catch (NoSuchRefundActionException e) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "内部错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        ResultParam<String> result = new ResultParam<>(CommonMetaCode.Success);
        if (!action.doAction(refund, null, result)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), result.getMetaCode().getMessage());
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        ResponseUtils.output(response, jsonResponse);
    }

    @AUTH.Function("refund")
    @RequestMapping(value = "/refund/approve2/{id}")
    public void approve2(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        JsonResponse jsonResponse = new JsonResponse();
        RefundModel refund = refundDAO.getById(id);
        if (refund == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "退款不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        Approve2Action action = null;
        try {
            action = (Approve2Action)refundActionManager.getAction(RefundBase.ACTION_APPROVE2);
        } catch (NoSuchRefundActionException e) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "内部错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        ResultParam<String> result = new ResultParam<>(CommonMetaCode.Success);
        if (!action.doAction(refund, null, result)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), result.getMetaCode().getMessage());
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        ResponseUtils.output(response, jsonResponse);
    }

    @AUTH.Function("refund")
    @RequestMapping(value = "/refund/reject1/{id}")
    public void refuse(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        JsonResponse jsonResponse = new JsonResponse();
        RefundModel refund = refundDAO.getById(id);
        if (refund == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "退款不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        Reject1Action action = null;
        try {
            action = (Reject1Action)refundActionManager.getAction(RefundBase.ACTION_REJECT1);
        } catch (NoSuchRefundActionException e) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "内部错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        ResultParam<String> result = new ResultParam<>(CommonMetaCode.Success);
        if (!action.doAction(refund, null, result)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), result.getMetaCode().getMessage());
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        ResponseUtils.output(response, jsonResponse);
    }

    @AUTH.Function("refund")
    @RequestMapping(value = "/refund/reject2/{id}")
    public void reject2(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        JsonResponse jsonResponse = new JsonResponse();
        RefundModel refund = refundDAO.getById(id);
        if (refund == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "退款不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        Reject2Action action = null;
        try {
            action = (Reject2Action)refundActionManager.getAction(RefundBase.ACTION_REJECT2);
        } catch (NoSuchRefundActionException e) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "内部错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        ResultParam<String> result = new ResultParam<>(CommonMetaCode.Success);
        if (!action.doAction(refund, null, result)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), result.getMetaCode().getMessage());
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        ResponseUtils.output(response, jsonResponse);
    }

    @AUTH.Function("refund")
    @RequestMapping(value = "/refund/thirdparty")
    public void refund2third(HttpServletRequest request, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        String refundid = request.getParameter("refundid");
        String money = request.getParameter("money");
        if (StringUtils.isBlank(refundid) || StringUtils.isBlank(money)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "参数错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        RefundModel refund = refundDAO.getById(Integer.parseInt(refundid));
        if (refund == null) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "退款不存在");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        WithdrawAction action = null;
        try {
            action = (WithdrawAction)refundActionManager.getAction(RefundBase.ACTION_WAITING);
        } catch (NoSuchRefundActionException e) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), "内部错误");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        ResultParam<String> result = new ResultParam<>(CommonMetaCode.Success);
        if (!action.doAction(refund, new BigDecimal(StringUtils.trim(money)), result)) {
            jsonResponse.setMessage(CommonMetaCode.Error.getCode(), result.getMetaCode().getMessage());
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        jsonResponse.setMessage(CommonMetaCode.Success.getCode(), "操作成功，请刷新页面");
        jsonResponse.data("refundUrl", result.getData());
        ResponseUtils.output(response, jsonResponse);
    }

}
