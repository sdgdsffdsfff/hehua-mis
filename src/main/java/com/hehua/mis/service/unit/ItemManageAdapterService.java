package com.hehua.mis.service.unit;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Filters;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.item.dao.FlashDAO;
import com.hehua.item.dao.ItemDAO;
import com.hehua.item.domain.*;
import com.hehua.item.enums.AppraiseStatus;
import com.hehua.item.manager.PropertyManager;
import com.hehua.item.model.ItemResult;
import com.hehua.item.service.*;
import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.user.service.UserManager;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hehua.item.service.logic.ItemManageService;
import com.hehua.mis.item.domain.Material;
import com.hehua.mis.item.dto.ItemDTO;
import com.hehua.mis.utils.Pagination;
import com.hehua.user.domain.User;
import com.hehua.user.service.UserService;

/**
 * Created by hewenjerry on 14-8-23.
 */
@Component
public class ItemManageAdapterService {

    private static final Log logger = LogFactory.getLog(ItemManageAdapterService.class);

    @Autowired
    private ItemManageService itemManageService;

    private static final String STR_SPLIT_CHAR = ":";

    private static final String STR_SPLIT_CHAR1 = "@";

    private static final String STR_SPLIT_CHMMA = ",";

    private static final String QUANTITY = "quantity";

    private static final String PIDS = "pids";

    private static final String SKUS = "skus";

    private static final String ORIGIN_PRICE = "originprice";

    private static final String REAL_PRICE = "realprice";

    private static final String BAR_CODE = "barcode";

    @Autowired
    private ItemDetailService itemDetailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private ItemStatusService itemStatusService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemAppraiseService itemAppraiseService;

    @Autowired
    private ItemRecommendService itemRecommendService;

    @Autowired
    private PropertyManager propertyManager;

    @Autowired
    private ItemSkuService itemSkuService;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private FlashDAO flashDAO;


    public List<Item> getAllItemBy(Pagination pagination) {
        if (pagination.getCount() == 0) {
            pagination.setCount(itemService.getItemCount());
        }

        List<Item> itemList = itemService.getItemByPage(pagination.getStartIndex(), pagination.getSize());
        List<Integer> itemIds = Transformers.transformList(itemList, new Function<Item, Integer>() {
            @Override
            public Integer apply(Item input) {
                return Integer.valueOf(String.valueOf(input.getId()));
            }
        });
        ListMultimap<Integer, ItemSku> skuListMap = itemSkuService.getItemSkusByItemIds(itemIds);
        for (int itemId : skuListMap.keySet()) {
            for (Item item : itemList) {
                if (item.getId() == itemId) {
                    List<ItemSku> itemSkuList = skuListMap.get(itemId);
                    int totalQuantity = 0;
                    for (ItemSku itemSku : itemSkuList) {
                        totalQuantity += itemSku.getQuantity();
                    }
                    item.setQuantity(totalQuantity);
                    break;
                }
            }
        }
        return itemList;
    }

    //TODO: 分页搜索
    public List<Item> getItemBySearch(Pagination pagination, String name, String minPrice, String maxPrice, String startTime, String endTime) {
        minPrice = org.apache.commons.lang3.StringUtils.trim(minPrice);
        maxPrice = org.apache.commons.lang3.StringUtils.trim(maxPrice);
        name = org.apache.commons.lang3.StringUtils.trim(name);
        int min = 0, max = 0;
        if (StringUtils.isNotEmpty(minPrice)) {
            min = Integer.valueOf(minPrice).intValue();
        }
        if (StringUtils.isNotEmpty(maxPrice)) {
            max = Integer.valueOf(maxPrice).intValue();
        }
        if (pagination.getCount() == 0) {
            pagination.setCount(itemDAO.getItemBySearchCount(name, min, max, 0));
        }
        if (StringUtils.isEmpty(name)) {
            name = null;
        }
        List<Item> itemList = null;
        if (StringUtils.isNotEmpty(startTime) || StringUtils.isNotEmpty(endTime)) {
            itemList = itemDAO.getItemBySearch(name, min, max, pagination.getStartIndex(), pagination.getSize(), 0);
        } else {
            itemList = itemDAO.getItemBySearch(name, min, max, pagination.getStartIndex(), 5000, 0);
        }

        if (CollectionUtils.isEmpty(itemList)) {
            return Collections.emptyList();
        }

        List<Integer> itemIds = Transformers.transformList(itemList, new Function<Item, Integer>() {
            @Override
            public Integer apply(Item input) {
                return Integer.valueOf(String.valueOf(input.getId()));
            }
        });

        ListMultimap<Integer, ItemSku> skuListMap = itemSkuService.getItemSkusByItemIds(itemIds);
        for (int itemId : skuListMap.keySet()) {
            for (Item item : itemList) {
                if (item.getId() == itemId) {
                    List<ItemSku> itemSkuList = skuListMap.get(itemId);
                    int totalQuantity = 0;
                    for (ItemSku itemSku : itemSkuList) {
                        totalQuantity += itemSku.getQuantity();
                    }
                    item.setQuantity(totalQuantity);
                    break;
                }
            }
        }

        //TODO 为了简单
        if (StringUtils.isNotEmpty(startTime) || StringUtils.isNotEmpty(endTime)) {

            final List<Flash> flashs = flashDAO.getFlashesByItemIds(itemIds,
                    DateTimeUtil.formatStrToDate(startTime),
                    DateTimeUtil.formatStrToDate(endTime));
            List<Item> filterItemIds = Filters.filter(itemList, new Predicate<Item>() {
                @Override
                public boolean apply(Item input) {
                    for (Flash flash : flashs) {
                        if (flash.getItemid() == input.getId()) {
                            return true;
                        }
                    }
                    return false;
                }
            });
            itemList = filterItemIds;

        }
        if (pagination.getCount() == 0) {
            pagination.setCount(itemList.size());
        }
        return itemList;
    }

    public ItemResult setItemDataEntry(ItemResult itemResult) {
        List<ItemSku> skus = itemResult.getItemSkus();
        if (CollectionUtils.isEmpty(skus)) {
            return itemResult;
        }
        List<ItemProperty> itemProperties = itemResult.getItemProperties();
        List<ItemProperty> itemProWithSkuList = new ArrayList<>(3);
        for (ItemProperty itemProperty : itemProperties) {
            if (itemProperty.getIssku() != 0) {
                itemProWithSkuList.add(itemProperty);
            }
        }

        List<Property> propertyList = new ArrayList<>(3);
        ListMultimap<Integer, PropertyValue> listMap = ArrayListMultimap.create();
        for (ItemProperty itemProperty : itemProWithSkuList) {
            Property property = propertyManager.getProperty(itemProperty.getPid());

            itemProperty.convertPropertyToMap();

            PropertyValue propertyValue = propertyManager.getPropertyValue(itemProperty.getPvid());
            Map<String, String> pvextMap = itemProperty.getPvextMap();
            if (MapUtils.isNotEmpty(pvextMap)) {
                propertyValue.setAlias(pvextMap.get(ItemProperty.PROPERTY_ALIAS));
            }
            int pKey = itemProperty.getPid();
            if (!listMap.containsKey(pKey)) {
                Map<String, String> pextMap = itemProperty.getPextMap();
                String alias = pextMap.get(ItemProperty.PROPERTY_ALIAS);
                if (StringUtils.isNotBlank(alias)) {
                    property.setAlias(alias);
                }
                propertyList.add(property);
            }

            listMap.put(pKey, propertyValue);
        }
        List<PropertyValue> pvList = new ArrayList<>();
        for (Property property : propertyList) {
            if (StringUtils.isNotEmpty(property.getAlias())) {
                property.setName(property.getAlias());
            }
            List<PropertyValue> tempPros = listMap.get(property.getId());
            for (PropertyValue propertyValue : new LinkedHashSet<>(tempPros)) {
                if (StringUtils.isNotEmpty(propertyValue.getAlias())) {
                    propertyValue.setName(propertyValue.getAlias());
                }
            }
            pvList.addAll(tempPros);
        }

        Map<Long, PropertyValue> propertyValueMap = Transformers.transformAsOneToOneMap(pvList, new Function<PropertyValue, Long>() {
            @Override
            public Long apply(PropertyValue input) {
                return Long.valueOf(input.getId());
            }
        });

        itemResult.setPropertyList(propertyList);//Item sku的属性

        for (ItemSku itemSku : skus) {
            String pvidStr = itemSku.getPvids();
            List<PropertyValue> tempProVList = new ArrayList<>(3);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(pvidStr)) {
                String[] pvidArray = StringUtils.split(pvidStr, STR_SPLIT_CHMMA);
                for (String str : pvidArray) {
                    String[] pvArray = StringUtils.split(str, STR_SPLIT_CHAR);
                    tempProVList.add(propertyValueMap.get(Long.valueOf(pvArray[1])));
                }
            }

            itemSku.setPropertyValueList(tempProVList);//itemsku属性值列表
        }

        return itemResult;
    }

    public boolean saveAppraiseInfo(int itemId, int darenId, String descStr) {
        User user = userManager.getUserById(darenId);
        if (user == null) {
            return false;
        } else {
            ItemAppraise itemAppraise = new ItemAppraise();
            itemAppraise.setCts(new Date());
            itemAppraise.setUts(new Date());
            itemAppraise.setName(user.getName());
            itemAppraise.setUid(user.getId());
            itemAppraise.setItemid(itemId);
            itemAppraise.setOffical(true);
            itemAppraise.setAppraise(descStr);
            itemAppraise.setStatus(AppraiseStatus.AUDIT_PASS.getCode());
            String summary = itemAppraiseService.abbreviateAppraise(itemAppraise.getAppraise());
            itemAppraise.setSummary(summary);
            return itemAppraiseService.createItemAppraise(itemAppraise);
        }
    }

    public boolean updateMetaBy(int itemId, String meta) {
        return itemDetailService.updateItemByItemId(itemId, meta);
    }

    public boolean updateItemDetail(ItemDTO itemDTO) {
       Item item = itemService.getItemById(itemDTO.getItemId());
       if (item == null) {
           return false;
       }

       item.setPostagetype(itemDTO.getPostTypeId());
       item.setPurchaseid(itemDTO.getPurchaseId());
       item.setUts(new Date());
       item.setDesc(itemDTO.getDesc());
       item.setWarehouseid(itemDTO.getWarehouseId());
       item.setName(itemDTO.getName());


       //更新推荐理由
        ItemRecommend itemRecommend = itemRecommendService.getItemRecommendByItemIdAndDarenIdReason(itemDTO.getItemId(), itemDTO.getDarenId(), itemDTO.getReason());
        itemRecommendService.deleteItemRecommendByItemid(itemDTO.getItemId());
        itemManageService.addItemRecommend(itemDTO.getItemId(), itemDTO.getDarenId(), itemDTO.getReason());

        // 更新材质
        if (StringUtils.isNotEmpty(itemDTO.getMaterialName())) {
            item.setMaterialid(saveMaterialInfo(itemDTO.getMaterialName()));
        }

        // 更新品牌
        Brand brand = brandService.getBrandByName(itemDTO.getBrandName());
        if (brand == null) {
            item.setBrandid(saveBrandInfo(itemDTO.getBrandName()));
        }

        String skuJson = itemDTO.getSkuStr();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(skuJson)) {
            JSONArray jsonArray = JSON.parseArray(skuJson);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tempJson = (JSONObject) jsonArray.get(i);
                int skuId = tempJson.getInteger("skuId");
                int quantity = tempJson.getInteger("quantity");
                ItemSku itemSku = itemSkuService.getItemSkuById(skuId);
                if (itemSku == null) {
                    logger.info("skuId not exist by id=" + skuId);
                }
                itemSku.setQuantity(quantity);
                itemSkuService.updateItemSkuById(itemSku);
            }
        }

       return itemService.updateItemById(item);
    }
    public boolean saveItemDetail(ItemDTO itemDTO,String barcode) {
        //        itemDTO.setDarenId(saveDarenInfo(itemDTO.getDarenName()));
        // TODO fixme
        itemDTO.setDarenId(itemDTO.getDarenId());

        itemDTO.setBrandId(saveBrandInfo(itemDTO.getBrandName()));

        if (StringUtils.isNotEmpty(itemDTO.getMaterialName())) {
            itemDTO.setMaterial(saveMaterialInfo(itemDTO.getMaterialName()));
        }

        // 商品的基本信息保存
        int itemId = itemManageService.addItem(itemDTO.getName(),
                itemDTO.getRealprice(), itemDTO.getOriginprice(), itemDTO.getBrandId(), 0, "件",
                itemDTO.getCrowedId(), itemDTO.getGenderId(), itemDTO.getPurchaseId(),
                itemDTO.getWarehouseId(), itemDTO.getMaterial(), itemDTO.getDesc(), itemDTO.getPostTypeId());
        if (itemId <= 0) {
            return false;
        }

        //TODO: 保存其中有失败是否考虑回滚, 商品详情保存
        itemManageService.addItemDetatil(itemId, itemDTO.getBrandId(), itemDTO.getDesc(),
                itemDTO.getPurchaseId(), itemDTO.getWarehouseId(), itemDTO.getGenderId(),
                itemDTO.getCrowedId(), itemDTO.getMaterial());

        // 商品图片保存
        if (StringUtils.isNotEmpty(itemDTO.getImages())) {
            List<String> imageList = new ArrayList<String>(Arrays.asList(itemDTO.getImages().split(
                    STR_SPLIT_CHAR1)));

//        List<Integer> imageLists = itemManageService.addItemImage(itemId, imageList);
            if (!CollectionUtils.isEmpty(imageList)) {// 商品图片的更新
                List<Integer> imageIdList = Transformers.transformList(imageList, new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return Integer.valueOf(s);
                    }
                });
                itemManageService.updateItemImageInfo(imageIdList, itemId);
            }
        }

        // 商品的推荐理由
        itemManageService.addItemRecommend(itemId, itemDTO.getDarenId(), itemDTO.getReason());

        //保存itemStatus表
        saveItemStatus(itemId);

        // item和category之间关联表
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setCategory(itemDTO.getCateId());
        itemCategory.setItemid(itemId);
        itemCategory.setCts(new Date());
        itemCategory.setUts(new Date());
        itemCategoryService.createItemCategory(itemCategory);

        // 商品sku相关信息保存，主要保存在item_sku,item_property
        saveSkuAndProperty(itemDTO, itemId,barcode);
        return true;
    }

    private void saveSkuAndProperty(ItemDTO itemDTO, int itemId,String barcode) {
        if (StringUtils.isNotEmpty(itemDTO.getSkuStr())) {
            JSONArray jsonArray = JSONArray.parseArray(itemDTO.getSkuStr());
            if (jsonArray != null && jsonArray.size() != 0){

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    int skuId = itemManageService.addItemSku(itemId,
                            Integer.valueOf(jsonObject.getString(QUANTITY)).intValue(),
                            jsonObject.getString(PIDS), jsonObject.getString(BAR_CODE),
                            jsonObject.getString(REAL_PRICE), jsonObject.getString(ORIGIN_PRICE));
                    if (skuId <= 0) {
                        logger.error("json 数据不完善" + jsonObject.toString());
                        continue;
                    } else {
                        String skus = "";
                        try {
                            skus = jsonObject.getString(SKUS);
                            logger.info("information skuid=" + skuId + "  pids=" + skus);
                            JSONArray skuArray = (JSONArray) JSON.parse(skus);
                            for (int j = 0; j < skuArray.size(); j++) {
                                JSONObject proObject = (JSONObject) skuArray.get(j);
                                itemManageService.addItemWithSkuProperty(proObject,itemId,
                                        skuId);

                            }
                        } catch (Exception e) {
                            logger.error("json pids node is error,by" + skus, e);

                        }
                    }
                }
            } else {
                itemManageService.addItemSku(itemId, itemDTO.getQuantity(), "", barcode,
                        itemDTO.getRealprice(), itemDTO.getOriginprice());
            }
        } else {// sku没有的话需要默认创建sku相关信息
            //TODO:条形码如何填写数值
            itemManageService.addItemSku(itemId, itemDTO.getQuantity(), "", barcode,
                    itemDTO.getRealprice(), itemDTO.getOriginprice());
        }
    }

    private int saveMaterialInfo(String materialName) {
        return materialService.addBy(materialName);
    }

    private int saveBrandInfo(String brandName) {
        Brand brand = new Brand();
        brand.setCts(new Date());
        brand.setUts(new Date());
        brand.setName(brandName);
        brand.setDesc("mis input");

        brandService.createBrand(brand);
        return (int) brand.getId();
    }

    private int saveDarenInfo(String darenName) {
        User user = new User();
        user.setCts(new Date());
        user.setUts(new Date());
        user.setName(darenName);
        user.setAccount("mis录入默认");
        user.setDaren(true);
        user.setDesc("mis input");
        user.setAvatar("http://google.cn");
        userManager.createUser(user);
        return (int) user.getId();
    }

    private void saveItemStatus(int itemId) {
        ItemStatus itemStatus = new ItemStatus();
        Date now = new Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 2);
        itemStatus.setCts(now);
        itemStatus.setUts(now);
        itemStatus.setEndtime(calendar.getTime());
        itemStatus.setOnlinetime(now);
        itemStatus.setStarttime(now);
        itemStatus.setItemid(itemId);
        itemStatus.setUnits("件");
        itemStatus.setSaletype(2);
        itemStatus.setSales(22);
        itemStatus.setStatus(1);

        itemStatusService.createItemStatus(itemStatus);
    }

    public List<Property> getItemProperty(int cateId) {
        Category category = categoryService.getCategoryBy(cateId);
        return propertyService.getPropertyByCateId(category.getId());
    }

    //TODO 规格判断不合理
    private static final String SKU_COLOR = "颜色";

    private static final String SKU_1 = "型号一";

    private static final String SKU_3 = "型号三";

    private static final String SKU_4 = "型号四";

    private static final String SKU_5 = "型号五";

    private static final String SKU_6 = "型号六";

    public JSONArray getItemWithSKuProperty(int cateId) {
        List<Property> propertyList = this.getItemProperty(cateId);
        JSONArray retArray = new JSONArray(propertyList.size());
        for (Property property : propertyList) {
            if (StringUtils.contains(property.getName(), SKU_COLOR)
                    || StringUtils.contains(property.getName(), SKU_1)
                    || StringUtils.contains(property.getName(), SKU_3)
                    || StringUtils.contains(property.getName(), SKU_4)
                    || StringUtils.contains(property.getName(), SKU_5)
                    || StringUtils.contains(property.getName(), SKU_6)) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pid", property.getId());
                jsonObject.put("pName", property.getName());

                List<PropertyValue> propertyValues = propertyValueService
                        .getPropertyValuesByPropertyId(property.getId());
                JSONArray tempArray = new JSONArray(propertyValues.size());
                for (PropertyValue propertyValue : propertyValues) {
                    JSONObject tempObject = new JSONObject();
                    tempObject.put("pvid", propertyValue.getId());
                    tempObject.put("pvname", propertyValue.getName());
                    tempArray.add(tempObject);
                }
                jsonObject.put("pvs", tempArray);

                retArray.add(jsonObject);
            }

        }

        return retArray;
    }

}
