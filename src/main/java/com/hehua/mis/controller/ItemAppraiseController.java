/**
 *
 */
package com.hehua.mis.controller;

import com.hehua.freeorder.model.FreeOrderModel;
import com.hehua.freeorder.service.FreeOrderService;
import com.hehua.item.domain.ItemAppraise;
import com.hehua.item.enums.AppraiseStatus;
import com.hehua.item.service.ItemAppraiseService;
import com.hehua.mis.utils.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 */
@Controller
@RequestMapping("/item/appraise")
public class ItemAppraiseController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ItemAppraiseController.class);

    @Autowired
    private ItemAppraiseService itemAppraiseService;

    @Autowired
    private FreeOrderService freeOrderService;

    @RequestMapping(value = "updateState", method = {RequestMethod.GET})

    public void update(long id, int state) {
        try {
            itemAppraiseService.updateState(id, state);
            ItemAppraise itemAppraise = itemAppraiseService.getItemAppraiseById(id);
            FreeOrderModel freeOrderModel = freeOrderService.getById((int) itemAppraise.getFreeorderid());
            if (state == AppraiseStatus.AUDIT_PASS.getCode()) {
                if (!freeOrderService.setAppraiseApprove2(freeOrderModel)) {
                    Response.jsonResponse(1, "同步订单状态失败");
                    return;
                }
                Response.jsonResponse(1, "已通过");
                return;
            }
            if (state == AppraiseStatus.AUDIT_NO_PASS.getCode()) {
                if (!freeOrderService.setAppraiseReject2(freeOrderModel)) {
                    Response.jsonResponse(1, "同步订单状态失败");
                    return;
                }
                Response.jsonResponse(1, "被拒绝");
                return;

            }
        } catch (Exception e) {
            logger.error("update:{}", e.toString());
            Response.jsonResponse(0, "更新失败");
            return;

        }

        Response.jsonResponse(2, "未知状态");
        return;


    }
}
