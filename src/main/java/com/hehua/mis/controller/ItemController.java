/**
 *
 */
package com.hehua.mis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehua.framework.subscribe.ZookeeperPubSubService;
import com.hehua.item.service.*;
import com.peaceful.auth.config.AUTH;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hehua.item.domain.BrandGroup;
import com.hehua.item.domain.BrandgroupItem;
import com.hehua.item.domain.Item;
import com.hehua.item.domain.ItemAppraise;
import com.hehua.item.enums.AppraiseStatus;
import com.hehua.item.model.ItemResult;
import com.hehua.item.purchase.restriction.PurchaseRestrictionPolicy;
import com.hehua.item.purchase.restriction.PurchaseRestrictionPolicyManager;
import com.hehua.item.purchase.restriction.PurchaseRestrictionPolicyType;
import com.hehua.item.purchase.restriction.PurchaseRestrictionService;
import com.hehua.item.purchase.restriction.policy.QuantityRestrictionPerItemPerUserPolicyHandler;
import com.hehua.item.service.logic.ItemManageService;
import com.hehua.item.utils.BabyUtils;
import com.hehua.mis.item.dto.ItemDTO;
import com.hehua.mis.service.unit.ItemManageAdapterService;
import com.hehua.mis.service.unit.MaterialService;
import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.MisConstants;
import com.hehua.mis.utils.MisWebUtil;
import com.hehua.mis.utils.Pagination;
import com.hehua.mis.utils.Response;
import com.hehua.mis.utils.ResponseUtils;
import com.hehua.order.service.FeedbackService;
import com.hehua.user.service.PhoneVersionService;
import com.hehua.user.service.UserService;

/**
 * hewenjerry
 */
@Controller
public class ItemController {

    @Autowired
    ItemManageAdapterService itemManageAdapterService;

    @Autowired
    ItemManageService itemManageService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemAppraiseService itemAppraiseService;

    @Autowired
    FlashSessionLocalCache flashSessionLocalCache;

    @Autowired
    PhoneVersionService phoneVersionService;

    @Autowired
    BrandGroupService brandGroupService;

    @Autowired
    BrandgroupItemService brandgroupItemService;

    @Autowired
    FlashService flashService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CrowedService crowedService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ItemRecommendService itemRecommendService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private PurchaseAddressService purchaseAddressService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemServiceApiProxy itemServiceApiProxy;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    private PurchaseRestrictionService purchaseRestrictionService;

    @Autowired
    private PurchaseRestrictionPolicyManager purchaseRestrictionPolicyManager;

    @RequestMapping(value = "/item/edit", method = {RequestMethod.POST})
    public void editItem(@RequestParam(value = "itemId", required = true) int itemId,
                         @RequestParam(value = "brandName", required = true) String brandName,
                         @RequestParam(value = "itemName", required = true) String itemName,
                         @RequestParam(value = "materialName", required = true) String materialName,
                         @RequestParam(value = "purchaseId", required = true) int purchaseId,
                         @RequestParam(value = "darenId", required = true) int darenId,
                         @RequestParam(value = "reason", required = true) String reason,
                         @RequestParam(value = "warehouseId", required = true) int warehouseId,
                         @RequestParam(value = "postTypeId", required = true) int postTypeId,
                         @RequestParam(value = "itemSku", required = false) String itemSku,
                         @RequestParam(value = "desc", required = true) String desc, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        if (StringUtils.isEmpty(StringUtils.trim(desc))) {
            jsonResponse.setMessage(-1, "商品详情描述不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(reason)) {
            jsonResponse.setMessage(-1, "商品推荐理由不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setBrandName(brandName);
        itemDTO.setItemId(itemId);
        itemDTO.setReason(reason);

        // desc = Cleaner.HTML.clean(desc, WhiteList.Publisher);

        itemDTO.setDesc(desc);
        itemDTO.setWarehouseId(warehouseId);
        itemDTO.setPurchaseId(purchaseId);
        itemDTO.setDarenId(darenId);
        itemDTO.setPostTypeId(postTypeId);
        itemDTO.setMaterialName(materialName);
        itemDTO.setName(itemName);
        itemDTO.setSkuStr(itemSku);

        if (!itemManageAdapterService.updateItemDetail(itemDTO)) {
            jsonResponse.setMessage(-1, "编辑失败");
        }
        jsonResponse.setMessage(0, "成功");

        ResponseUtils.output(response, jsonResponse);
    }

    @RequestMapping(value = "/item/save", method = {RequestMethod.POST})
    public void saveItem(HttpServletRequest request, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        int cateId = ServletRequestUtils.getIntParameter(request, "cateId", 0);
        String[] crowedId = ServletRequestUtils.getStringParameters(request, "crowedId");
        int genderId = ServletRequestUtils.getIntParameter(request, "genderId", 0);
        int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
        int purchaseId = ServletRequestUtils.getIntParameter(request, "purchaseId", 0);
        String[] skus = ServletRequestUtils.getStringParameters(request, "goodsNorms");
        String skuStr = ServletRequestUtils.getStringParameter(request, "skuStr", "");
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        String originPrice = ServletRequestUtils.getStringParameter(request, "originPrice", "");
        String realPrice = ServletRequestUtils.getStringParameter(request, "realPrice", "");
        int quantity = ServletRequestUtils.getIntParameter(request, "quantity", 0);
        String image = ServletRequestUtils.getStringParameter(request, "imags", "");
        String desc = ServletRequestUtils.getStringParameter(request, "desc", "");
        String reason = ServletRequestUtils.getStringParameter(request, "reason", "");

        // TODO三个数据暂时保留
        int brandId = ServletRequestUtils.getIntParameter(request, "brandId", 0);
        int material = ServletRequestUtils.getIntParameter(request, "materialId", 0);
        int darenId = ServletRequestUtils.getIntParameter(request, "darenId", 0);

        String brandName = ServletRequestUtils.getStringParameter(request, "brandName", "");
        String materialName = ServletRequestUtils.getStringParameter(request, "materialName", "");
        String darenName = ServletRequestUtils.getStringParameter(request, "darenName", "");
        String barcode = ServletRequestUtils.getStringParameter(request, "barcode", null);
        int postTypeId = ServletRequestUtils.getIntParameter(request, "postTypeId", 1);

        if (skus != null || skus.length != 0) {
            JSONArray jsonArray = new JSONArray();
            for (String sku : skus) {
                jsonArray.add(JSONObject.parse(sku));
            }
            skuStr = jsonArray.toJSONString();
        }
        // TODO:判断
        //        if (StringUtils.isEmpty(skuStr)) {
        //            jsonResponse.setMessage(-1, "sku json为空");
        //        }
        if (StringUtils.isEmpty(brandName)) {
            jsonResponse.setMessage(-1, "品牌名称不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (quantity <= 0) {
            jsonResponse.setMessage(-1, "商品数量不能等于0");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        //        if (StringUtils.isEmpty(darenName)) {
        //            jsonResponse.setMessage(-1, "专题达人不能为空");
        //            ResponseUtils.output(response, jsonResponse);
        //            return;
        //        }
        if (StringUtils.isEmpty(name)) {
            jsonResponse.setMessage(-1, "商品名称不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(realPrice)) {
            jsonResponse.setMessage(-1, "商品真实价格不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(originPrice)) {
            jsonResponse.setMessage(-1, "商品原始价格不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(image) || StringUtils.indexOf(image, "@") == -1) {
            jsonResponse.setMessage(-1, "商品图像不能空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(desc)) {
            jsonResponse.setMessage(-1, "商品详情描述不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (StringUtils.isEmpty(reason)) {
            jsonResponse.setMessage(-1, "商品推荐理由不能为空");
            ResponseUtils.output(response, jsonResponse);
            return;
        }
        if (purchaseId <= 0) {
            jsonResponse.setMessage(-1, "请选择商品购买地");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (ArrayUtils.isEmpty(crowedId)) {
            jsonResponse.setMessage(-1, "请选择适配人群");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setReason(reason);
        itemDTO.setName(name);
        itemDTO.setBrandName(brandName);
        itemDTO.setMaterialName(materialName);
        itemDTO.setDarenName(darenName);
        itemDTO.setBrandId(brandId);
        itemDTO.setCateId(cateId);
        itemDTO.setDarenId(darenId);
        itemDTO.setDesc(desc);
        itemDTO.setGenderId(genderId);
        itemDTO.setImages(image);
        itemDTO.setMaterial(material);
        itemDTO.setOriginprice(originPrice);
        itemDTO.setRealprice(realPrice);
        itemDTO.setQuantity(quantity);
        itemDTO.setSkuStr(skuStr);
        itemDTO.setWarehouseId(warehouseId);
        itemDTO.setPurchaseId(purchaseId);
        itemDTO.setPostTypeId(postTypeId);
        itemDTO.setCrowedId(org.apache.commons.lang3.StringUtils.join(crowedId, ','));

        if (cateId <= 0) {
            jsonResponse.setMessage(-1, "cateId为空");
        } else {
            if (!itemManageAdapterService.saveItemDetail(itemDTO, barcode)) {
                jsonResponse.setMessage(-1, "保存失败");
            }
            jsonResponse.setMessage(0, "成功");
        }

        ResponseUtils.output(response, jsonResponse);
    }

    @AUTH.Function("goods")
    @RequestMapping(value = "/item/list", method = {RequestMethod.GET})
    public ModelAndView getItemList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("item/goodList");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);

        Pagination pagination = new Pagination(15, pageNo);

        modelAndView.addObject("itemList", itemManageAdapterService.getAllItemBy(pagination));
        modelAndView.addObject("darenList", itemManageService.getAllDaren());
        modelAndView.addObject("pagination", pagination);
        Map<Integer, PurchaseRestrictionPolicy> all = purchaseRestrictionPolicyManager.getAll();
        Map<Long, PurchaseRestrictionPolicy> convert = new HashMap<>();
        for (Map.Entry<Integer, PurchaseRestrictionPolicy> entry : all.entrySet()) {
            convert.put(entry.getKey().longValue(), entry.getValue());
        }
        modelAndView.addObject("restrictions", convert);
        MisWebUtil.writeCookie(response, MisConstants.AUTH_COOKIE_KEY_MIS, 123 + "|aswd",
                MisWebUtil.getDomain(request), MisConstants.AUTH_COOKIE_EXPIRE_TIME);
        //        CookieUtils.saveCookie(response, MisConstants.AUTH_COOKIE_KEY_MIS, 123 + "|aswd",
        //                MisConstants.AUTH_COOKIE_EXPIRE_TIME, MisWebUtil.getDomain(request), "/", true);

        return modelAndView;
    }

    @RequestMapping(value = "/items/search", method = {RequestMethod.GET})
    public ModelAndView searchItemList(HttpServletRequest request,
                                       @RequestParam(value = "itemName", required = false) String itemName,
                                       @RequestParam(value = "minPrice", required = false) String minPrice,
                                       @RequestParam(value = "maxPrice", required = false) String maxPrice,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView modelAndView = new ModelAndView("item/goodList");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);

        Pagination pagination = new Pagination(15, pageNo);

        startTime = startTime.trim();
        endTime = endTime.trim();
        minPrice = minPrice.trim();
        maxPrice = maxPrice.trim();
        itemName = itemName.trim();
        if (org.apache.commons.lang3.StringUtils.isEmpty(minPrice)) {
            minPrice = "0";
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(maxPrice)) {
            maxPrice = "999999";
        }

        modelAndView.addObject("itemList", itemManageAdapterService.getItemBySearch(pagination,
                itemName, minPrice, maxPrice, startTime, endTime));
        modelAndView.addObject("darenList", itemManageService.getAllDaren());
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("itemName", itemName);
        modelAndView.addObject("minPrice", minPrice);
        modelAndView.addObject("maxPrice", maxPrice);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);

        return modelAndView;
    }

    @RequestMapping(value = "/item/detail/{itemId}", method = {RequestMethod.GET})
    public ModelAndView getItemDetail(@PathVariable("itemId") int itemId) {
        ModelAndView modelAndView = new ModelAndView("item/itemDetail");

        modelAndView.addObject("warehouseList", warehouseService.getAllWarehouse());
        modelAndView.addObject("purchaseList", purchaseAddressService.getAllPurchaseAddress());
        modelAndView.addObject("darenList", userService.getDarenList().getData());
        ItemResult itemResult = itemService.getItem(itemId);
        itemResult = itemManageAdapterService.setItemDataEntry(itemResult);
        modelAndView.addObject("itemResult", itemResult);
        modelAndView.addObject("itemAppraise", itemAppraiseService.getItemAppraiseByItem(itemId));
        modelAndView.addObject("imageList",
                itemServiceApiProxy.convertImageIdToImageUrl(itemResult));
        modelAndView.addObject("commentList", feedbackService.getListByItemId(itemId));
        modelAndView.addObject(
                "crows",
                BabyUtils.convertIdToStr(itemResult.getItem().getCrowedid(),
                        crowedService.getAllCrowd()));
        modelAndView.addObject(
                "gender",
                BabyUtils.convertGenderIdToStr(itemResult.getItem().getGenderid(),
                        genderService.getAllGender()));
        modelAndView.addObject("material",
                materialService.getBy(itemResult.getItem().getMaterialid()));
        try {
            modelAndView.addObject("itemRecommend",
                    itemRecommendService.getItemRecommendByItemid(itemResult.getItem().getId())
                            .get(0));
        } catch (Exception e) {
            logger.error("itemRecommend is null", e);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/item/appraise/add/{itemId}")
    public String addAppraisePre(@PathVariable("itemId") long itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        request.setAttribute("itemId", itemId);
        request.setAttribute("darenList", userService.getDarenList().getData());
        return "item/addAppraise";
    }

    @RequestMapping(value = "/item/appraise/add")
    public void addAppraise(ItemAppraise itemAppraise) {
        itemAppraise.setCts(new Date());
        itemAppraise.setUts(new Date());
        itemAppraise.setOffical(true);
        itemAppraise.setName(userService.findUserById(itemAppraise.getUid()).getName());
        itemAppraise.setStatus(AppraiseStatus.AUDIT_PASS.getCode());
        if (itemAppraiseService.createItemAppraise(itemAppraise)) Response.jsonResponse(1, "添加成功");
        else Response.jsonResponse(0, "添加失败");
    }

    @RequestMapping(value = "/item/delete/{id}", method = {RequestMethod.GET})
    public void deleteItem(@PathVariable int id, HttpServletResponse response) {
        if (itemService.flashIsExist(id) || itemService.brandGroupIsExist(id)) {
            Response.jsonResponse(0, "不可以删除，当前商品处于在线");
            return;

        }
        if (itemService.deleteItem(id)) {
            List<BrandgroupItem> brandgroupItems = brandgroupItemService
                    .getFlashItemListByBrandGrandItemId(id);
            brandgroupItemService.batchUpdateBrandGroupItemStatus(brandgroupItems, 2);
            Response.jsonResponse(1, "删除成功");
            return;

        }
        Response.jsonResponse(0, "删除失败");
        return;

    }

    @RequestMapping(value = "/item/brand/addGroupPre", method = {RequestMethod.GET})
    public String addBrandGroupPre() {
        return "item/addBrandGroup";

    }

    @RequestMapping(value = "/item/brand/addGroup", method = {RequestMethod.POST})
    public String addBrandGroup(BrandGroup brandGroup) {
        Date date = new Date();
        brandGroup.setUts(date);
        brandGroup.setCts(date);
        brandGroup.setOnlinetime(date);
        brandGroup.setStatus(0);
        brandGroup.setPriority(1);
        brandGroup.setStarttime(date);
        brandGroup.setEndtime(date);
        brandGroupService.createBrandGroup(brandGroup);
        return "item/addBrandGroup";
    }

    @RequestMapping(value = "/item/brand/brandGroup/list", method = {RequestMethod.GET})
    public String findBrandGroup(HttpServletRequest request) {
        List<BrandGroup> brandGroupList = brandGroupService.getAllBrandGroup();
        request.setAttribute("brandGroupList", brandGroupList);
        return "item/brandGroupList";
    }

    @RequestMapping(value = "/item/brand/brandGroup/{id}/{status}/goods/list", method = {RequestMethod.GET})
    public String findGoodsOfBrandGroup(HttpServletRequest request, @PathVariable Integer id,
                                        @PathVariable Integer status) {
        List<BrandgroupItem> brandgroupItems = brandgroupItemService
                .getFlashItemListByBrandGrandId(id, status);
        List<Item> itemList = Lists.newArrayList();
        List<Long> idList = Lists.newArrayList();
        List<Integer> statusList = Lists.newArrayList();
        if (brandgroupItems != null) {
            for (BrandgroupItem brandgroupItem : brandgroupItems) {
                itemList.add(itemService.getItem_(brandgroupItem.getItemid()));
                idList.add(brandgroupItem.getId());
                statusList.add(brandgroupItem.getStatus());
            }
        }
        request.setAttribute("itemList", itemList);
        request.setAttribute("idList", idList);
        request.setAttribute("statusList", statusList);
        return "item/itemListData";
    }

    @RequestMapping(value = "/item/brand/brandGroupItem/{id}/{status}", method = {RequestMethod.GET})
    @ResponseBody
    public String updateBrandGroupItemStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return brandgroupItemService.updateBrandGroupItemStatus(id, status) + "";
    }

    @RequestMapping(value = "/item/brand/view", method = {RequestMethod.POST})
    public String findBrandGroup(BrandGroup brandGroup) {
        brandGroupService.createBrandGroup(brandGroup);
        return "item/addBrandGroup";
    }

    @RequestMapping(value = "/item/brand/addBrandGroupItemData/{id}", method = {RequestMethod.GET})
    public String addBrandGroupItemPre(@PathVariable int id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "item/addBrandGroupItemData";
    }

    @RequestMapping(value = "/item/brand/addBrandGroupItem", method = {RequestMethod.POST})
    public void addBrandGroupItem(int id, String ids, HttpServletRequest request) {
        String[] itemIDS = ids.split(",");
        List<BrandgroupItem> brandgroupItems = Lists.newArrayList();
        for (String itemId : itemIDS) {
            BrandgroupItem brandgroupItem = new BrandgroupItem();
            brandgroupItem.setCts(new Date());
            brandgroupItem.setGroupid(id);
            brandgroupItem.setItemid(Integer.parseInt(itemId));
            brandgroupItem.setStatus(1);
            brandgroupItems.add(brandgroupItem);
        }

       // ZookeeperPubSubService.getInstance().post(flashSessionLocalCache.key(), "update");
        if (brandgroupItemService.batchInsertBrandgroupItem(brandgroupItems))
            Response.jsonResponse(1, "添加成功");
        else Response.jsonResponse(0, "添加失败");
    }

    @RequestMapping(value = "/item/brand/addBrandGroupFlashData/{id}", method = {RequestMethod.GET})
    public String addBrandGroupFlashPre(@PathVariable int id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "item/addBrandGroupFlashData";
    }

    @RequestMapping(value = "/item/brand/addBrandGroupFlash", method = {RequestMethod.POST})
    public void addBrandGroupFlash(int id, String date) {
        try {
            if (brandGroupService.addFlashBrandGroup(id, DateTimeUtil.formatStrToDate(date)))
                Response.jsonResponse(1, "添加成功");
            else
                Response.jsonResponse(0, "添加失败");
        } catch (Exception e) {
            logger.error("addBrandGroupFlash:{}", e);
            Response.jsonResponse(0, "已经添加过闪购");
        }

    }

    @RequestMapping(value = "/item/meta/add", method = {RequestMethod.POST})
    public void addMeta(HttpServletRequest request, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        int itemId = ServletRequestUtils.getIntParameter(request, "itemId", 0);
        String metaStr = ServletRequestUtils.getStringParameter(request, "metaStr", "");
        if (itemId == 0 || StringUtils.isEmpty(metaStr)) {
            jsonResponse.setMessage(-1, "数据不合法");
            ResponseUtils.output(response, jsonResponse);
            return;
        }

        if (itemManageAdapterService.updateMetaBy(itemId, metaStr)) {
            jsonResponse.setMessage(0, "保存成功");
        } else {
            jsonResponse.setMessage(-1, "保存失败");
        }

        ResponseUtils.output(response, jsonResponse);
    }

    @RequestMapping(value = "/item/appraise/list/{itemid}", method = {RequestMethod.GET})
    public ModelAndView getAppariseList(@PathVariable("itemid") int itemid,
                                        HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("item/itemAppraise");

        List<ItemAppraise> itemAppraiseList = itemAppraiseService.getItemAppraiseByItem(itemid);
        modelAndView.addObject("itemAppraiseList", itemAppraiseList);
        modelAndView.addObject("itemId", itemid);
        return modelAndView;
    }

    @RequestMapping(value = "/item/appraise/{appariseId}", method = {RequestMethod.GET})
    public void delApparise(@PathVariable("appariseId") int appariseId, HttpServletResponse response) {
        JsonResponse jsonResponse = new JsonResponse();
        boolean status = itemAppraiseService.deleteItemAppraiseById(appariseId);
        if (status) {
            jsonResponse.setMessage(1, "删除成功");
        } else {
            jsonResponse.setMessage(0, "删除失败");
        }
        ResponseUtils.output(response, jsonResponse);
    }

    @RequestMapping(value = "/item/{itemId}/restriction", method = {RequestMethod.GET})
    public ModelAndView updatePurchaseRestrictionPolicy(@PathVariable("itemId") int itemId,
                                                        HttpServletResponse response) {
        PurchaseRestrictionPolicy purchaseRestrictionPolicy = purchaseRestrictionPolicyManager
                .getItemPurchaseRestrictionPolicy(itemId);

        if (purchaseRestrictionPolicy == null) {
            purchaseRestrictionPolicy = new PurchaseRestrictionPolicy();
            purchaseRestrictionPolicy.setItemid(itemId);
            purchaseRestrictionPolicy.setTitle("title");
            purchaseRestrictionPolicy.setDesc("desc");
            purchaseRestrictionPolicy
                    .setType(PurchaseRestrictionPolicyType.QuantityRestrictionPerItemPerUser.name());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(QuantityRestrictionPerItemPerUserPolicyHandler.DATA_KEY_LIMIT_QUANTITY, 1);
            purchaseRestrictionPolicy.setData(JSON.toJSONString(dataMap));

            purchaseRestrictionPolicyManager
                    .addPurchaseRestrictionPolicy(purchaseRestrictionPolicy);
        } else {
            purchaseRestrictionPolicyManager.delPurchaseRestrictionPolicy(itemId);
        }

        return new ModelAndView("redirect:/item/list");
    }
}
