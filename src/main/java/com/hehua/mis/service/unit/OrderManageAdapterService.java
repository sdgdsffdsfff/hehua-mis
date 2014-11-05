package com.hehua.mis.service.unit;

import com.google.common.base.Predicate;
import com.hehua.commons.Filters;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.item.dao.ItemDAO;
import com.hehua.order.dao.OrderItemsDAO;
import com.hehua.order.dao.OrdersDAO;
import com.hehua.order.model.OrdersModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by hewenjerry on 14-8-23.
 */
@Component
public class OrderManageAdapterService {

    private static final Log logger = LogFactory.getLog(OrderManageAdapterService.class);

    @Autowired
    private OrderItemsDAO orderItemsDAO;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private OrdersDAO ordersDAO;

    public List<OrdersModel> filterModel(List<OrdersModel> ordersModels, String itemName) {
        if (CollectionUtils.isEmpty(ordersModels) && org.apache.commons.lang3.StringUtils.isEmpty(itemName)) {
            return Collections.emptyList();
        }

        if (CollectionUtils.isEmpty(ordersModels) && org.apache.commons.lang3.StringUtils.isNotEmpty(itemName)) {
            return getOrdersModelsByItemName(itemName);
        }

        if (!CollectionUtils.isEmpty(ordersModels)  && org.apache.commons.lang3.StringUtils.isNotEmpty(itemName)) {
            Collection<Long> itemLites = itemDAO.getItemLiteByName(itemName);
            if (itemLites.isEmpty()) {
                return Collections.emptyList();
            } else {
                final List<Long> orderIds = orderItemsDAO.getListByItemIds(itemLites);
                List<OrdersModel> filterOrders = new ArrayList<>();
                filterOrders = Filters.filter(ordersModels, new Predicate<OrdersModel>() {
                    @Override
                    public boolean apply(OrdersModel input) {
                        for (Long orderId : orderIds) {
                            if (orderId == input.getId()) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
                return filterOrders;
            }
        }

        return ordersModels;
    }

    public List<OrdersModel> getOrdersModelsByItemName(String itemName) {
        Collection<Long> itemLites = itemDAO.getItemLiteByName(itemName);
        if (itemLites.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Long> orderIds = orderItemsDAO.getListByItemIds(itemLites);
            return ordersDAO.getsByIds(orderIds);
        }

    }


}
