package com.hehua.mis.service.unit;

import com.hehua.commons.collection.CollectionUtils;
import com.hehua.freeorder.info.FreeOrderInfo;
import com.hehua.freeorder.service.FreeOrderService;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.domain.enums.FreeFlashEnums;
import com.hehua.item.service.FreeFlashService;
import com.hehua.item.service.ItemAppraiseService;
import com.hehua.item.service.ItemSkuService;
import com.hehua.mis.utils.DateTimeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by hesheng on 14-9-22.
 */
@Component
public class FreeFlashAdapterService {
    private static final Log logger = LogFactory.getLog(FreeFlashAdapterService.class);

    @Autowired
    private ItemSkuService itemSkuService;

    @Autowired
    private FreeFlashService freeFlashService;

    @Autowired
    private FreeOrderService freeOrderService;

    @Autowired
    private ItemAppraiseService itemAppraiseService;

    public boolean createFreeFlash(int itemId, int freeQuantity, String startTime, String endTime) {
        FreeFlash freeFlash = freeFlashService.getFreeFlashByItemidNotContainStatus(itemId);
        if (freeFlash != null) {
            logger.error("this item is exist freeFlash by itemId=" + itemId);
            return false;
        }

        Date startDate = DateTimeUtil.formatFixHourBy(startTime, 9);
        Date endDate =  DateTimeUtil.formatFixHourBy(endTime, 9);

        freeFlash = new FreeFlash();
        Date date = new Date();
        freeFlash.setItemid(itemId);
        freeFlash.setOnlinedate(date);
        freeFlash.setCreatetime(new Date());
        freeFlash.setStarttime(startDate);
        freeFlash.setEndtime(endDate);
        freeFlash.setStatus(FreeFlashEnums.ONLINE.getCode());
        freeFlash.setPriority(1);
        freeFlash.setFreequantity(freeQuantity);
        freeFlash.setApplynum(20);

        List<ItemSku> itemSkuList = itemSkuService.getItemSkusByItemid(itemId);
        if (CollectionUtils.isEmpty(itemSkuList)) {
            logger.error("this item is no sku by itemid=" + itemId);
            return false;
        }
        freeFlash.setSkuid((int)itemSkuList.get(0).getId());

        return freeFlashService.createFreeFlash(freeFlash);
    }

}
