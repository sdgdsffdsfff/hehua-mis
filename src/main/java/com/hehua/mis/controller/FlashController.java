/**
 * 
 */
package com.hehua.mis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehua.framework.subscribe.ZookeeperPubSubService;
import com.hehua.item.service.FlashSessionLocalCache;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.commons.Extractors;
import com.hehua.item.domain.BrandGroup;
import com.hehua.item.domain.Flash;
import com.hehua.item.model.ItemLite;
import com.hehua.item.model.ItemResult;
import com.hehua.item.service.BrandGroupService;
import com.hehua.item.service.FlashService;
import com.hehua.item.service.ItemService;
import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;

/**
 * @author zhihua
 *
 */
@Controller
public class FlashController {

    @Autowired
    private FlashService flashService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BrandGroupService brandGroupService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/flash/add/{itemId}")
    public ModelAndView addFlash(@PathVariable long itemId) {
        ItemResult item = itemService.getItem(itemId);
        flashService.addFlash(new Date(), item);
        return showFlashList();
    }

    @RequestMapping(value = "/flash/add", method = RequestMethod.POST)
    public ModelAndView addFlashBy(HttpServletRequest request, HttpServletResponse response) {
        int itemId = ServletRequestUtils.getIntParameter(request, "itemId", 0);
        String onlineStr = ServletRequestUtils.getStringParameter(request, "onlineStr", "");
        if (StringUtils.isEmpty(onlineStr)) {
            return ResponseUtils.output(response, new JsonResponse(-1, "添加闪购时期"));
        }
        ItemResult item = itemService.getItem(itemId);

        flashService.addFlash(DateTimeUtil.formatStrToDate1(onlineStr), item);
        return ResponseUtils.output(response, new JsonResponse(0, "添加闪购成功"));
    }

    @RequestMapping("/flash/list")
    public ModelAndView showFlashList() {
        List<Flash> allFlashes = flashService.getAllFlash();

        Set<Integer> itemIds = Extractors.extractAsSet(allFlashes, Flash.itemIdExtractor);
        itemIds.remove(0);
        Map<Integer, ItemLite> itemsMap = itemService.getItemLitesByIds(itemIds);

        Map<Integer, BrandGroup> groupsMap = new HashMap<>();
        for (Flash flash : allFlashes) {
            if (flash.isGroup()) {
                BrandGroup brandGroup = brandGroupService.getBrandGroupById(flash.getGroupid());
                if (brandGroup != null) {
                    groupsMap.put(brandGroup.getId(), brandGroup);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("flash/list");
        modelAndView.addObject("allFlashes", allFlashes);
        modelAndView.addObject("itemsMap", itemsMap);
        modelAndView.addObject("groupsMap", groupsMap);

        return modelAndView;
    }

    @RequestMapping("/flash/search")
    public ModelAndView searchFlash(String onlinedate) {
        List<Flash> allFlashes = flashService.getFlashByonlineDate(DateTimeUtil.formatStrToDate(onlinedate));

        Set<Integer> itemIds = Extractors.extractAsSet(allFlashes, Flash.itemIdExtractor);
        itemIds.remove(0);
        Map<Integer, ItemLite> itemsMap = itemService.getItemLitesByIds(itemIds);

        Map<Integer, BrandGroup> groupsMap = new HashMap<>();
        for (Flash flash : allFlashes) {
            if (flash.isGroup()) {
                BrandGroup brandGroup = brandGroupService.getBrandGroupById(flash.getGroupid());
                if (brandGroup != null) {
                    groupsMap.put(brandGroup.getId(), brandGroup);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("flash/list");
        modelAndView.addObject("allFlashes", allFlashes);
        modelAndView.addObject("itemsMap", itemsMap);
        modelAndView.addObject("groupsMap", groupsMap);
        modelAndView.addObject("onlinedate",onlinedate);

        return modelAndView;
    }

    @RequestMapping(value = "/flash/{flashId}/setPriority", method = RequestMethod.POST)
    public ModelAndView setPriority(@PathVariable int flashId,
                                    @RequestParam(value = "priorityVal", required = true) int priorityVal,
                                    HttpServletResponse response) {
        Flash flash = flashService.getFlashById(flashId);
        if (flash == null) {
            return ResponseUtils.output(response, new JsonResponse(1, "设置优先级失败"));
        }

        flash.setPriority(priorityVal);
        if (flashService.updateFlashById(flash)) {
            try {
                ZookeeperPubSubService.getInstance().post(FlashSessionLocalCache.KEY,
                        "updateflash_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
            } catch (Exception e) {
                logger.error("notify flash event fail by ZookeeperPubSubService", e);
                return ResponseUtils.output(response, new JsonResponse(1, "设置优先级调用通知事件失败"));
            }
        } else {
            return ResponseUtils.output(response, new JsonResponse(1, "设置优先级失败"));
        }
        return ResponseUtils.output(response, new JsonResponse(0, "设置优先级成功"));
    }

    @RequestMapping(value = "/flash/{flashId}/setonline", method = RequestMethod.POST)
    public ModelAndView setOnline(@PathVariable int flashId, HttpServletResponse response) {
        flashService.setOnline(flashId);
        return ResponseUtils.output(response, new JsonResponse(0, "上线成功"));
    }

    @RequestMapping(value = "/flash/{flashId}/setoffline", method = RequestMethod.POST)
    public ModelAndView setOffline(@PathVariable int flashId, HttpServletResponse response) {
        flashService.setOffline(flashId);
        return ResponseUtils.output(response, new JsonResponse(0, "下线成功"));
    }
}
