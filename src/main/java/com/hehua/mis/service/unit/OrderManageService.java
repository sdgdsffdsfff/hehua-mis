package com.hehua.mis.service.unit;

import com.google.common.base.Function;
import com.hehua.commons.Transformers;
import com.hehua.mis.item.dto.OrderDTO;
import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.order.dao.OrdersDAO;
import com.hehua.order.info.ItemInfo;
import com.hehua.order.info.ItemTypeInfo;
import com.hehua.order.info.OrderInfo;
import com.hehua.order.model.OrdersModel;
import com.hehua.order.service.OrdersService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hesheng on 14-9-22.
 */
@Component
public class OrderManageService {
    private static final Log logger = LogFactory.getLog(OrderManageService.class);

    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private OrdersService ordersService;

    private static final String SPLIT = ";";

    public List<OrderDTO> getOrderDetailBy(int status, int startIndex, int size) {
        List<OrdersModel> orders = ordersDAO.getByStatusAndOrderIdRange(status, startIndex, size);

        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        OrderDTO orderDTO = null;
        List<ItemInfo> itemInfoList = null;
        List<String> itemList = null;
        Map<Long, OrderInfo> orderInfoMap = ordersService.batchGenOrderInfo(orders);

        for (OrdersModel ordersModel : orders) {
            OrderInfo orderInfo = orderInfoMap.get(ordersModel.getId());
            orderDTO = new OrderDTO();
            orderDTO.orderId = String.valueOf(ordersModel.getId());
            orderDTO.paytime = DateTimeUtil.getFormatDate(ordersModel.getPayDate(), "yyyy-MM-dd HH:mm:ss");
            orderDTO.name = orderInfo.getAddress().getName();
            orderDTO.province = orderInfo.getAddress().getProvince();
            orderDTO.city = orderInfo.getAddress().getCity();
            orderDTO.county = orderInfo.getAddress().getCounty();
            orderDTO.detail = orderInfo.getAddress().getDetail();
            orderDTO.postcode = orderInfo.getAddress().getPostCode();
            orderDTO.phone = orderInfo.getAddress().getPhone();
            orderDTO.invoiceComment = orderInfo.getInvoiceComment();
            itemInfoList = orderInfo.getItemInfo();
            itemList = Transformers.transformList(itemInfoList, new Function<ItemInfo, String>() {
                @Override
                public String apply(ItemInfo itemInfo) {
                    return itemInfo.getTitle();
                }
            });

            orderDTO.title = StringUtils.join(itemList, SPLIT);

            itemList = Transformers.transformList(itemInfoList, new Function<ItemInfo, String>() {
                @Override
                public String apply(ItemInfo itemInfo) {
                    return String.valueOf(itemInfo.getPrice());
                }
            });
            orderDTO.price = StringUtils.join(itemList, SPLIT);

            itemList = Transformers.transformList(itemInfoList, new Function<ItemInfo, String>() {
                @Override
                public String apply(ItemInfo itemInfo) {
                    return String.valueOf(itemInfo.getQuantity());
                }
            });
            orderDTO.quantity = StringUtils.join(itemList, SPLIT);
            List<String> skuList = Transformers.transformList(itemInfoList, new Function<ItemInfo, String>() {
                @Override
                public String apply(ItemInfo input) {
                    String tmp = "";
                    for (ItemTypeInfo itemTypeInfo : input.getTypes()) {
                        tmp  += itemTypeInfo.getName() + ":" + itemTypeInfo.getValue() + "&";
                    }
                    return tmp;
                }
            });
            orderDTO.skuName = StringUtils.join(skuList, SPLIT);

            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

}
