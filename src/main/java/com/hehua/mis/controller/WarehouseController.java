/**
 * 
 */
package com.hehua.mis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * hewenjerry
 */
@RequestMapping("/ware")
@Controller()
public class WarehouseController {

    private static final String PAGE_LIST = "ware/list";

    private static final String PAGE_INDEX = "ware";

//    @Autowired
//    private WarehouseService warehouseService;
//
//    @RequestMapping({ "/", "/list" })
//    public ModelAndView showWarehouseListPage(HttpServletRequest request, HttpServletResponse response) {
//        ModelAndView modelAndView = new ModelAndView(PAGE_LIST);
//        List<Warehouse> warehouseList = warehouseService.getAll();
//        modelAndView.addObject("warehouseList", warehouseList);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/modify", method = { RequestMethod.POST })
//    public ModelAndView modifyWarehousePage(HttpServletRequest request, HttpServletResponse response) {
//
//        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
//        String name = ServletRequestUtils.getStringParameter(request, "name", "default");
//        if (StringUtils.isBlank(name)) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "仓库名称不能为空"));
//        }
//        if (!warehouseService.updateBy(id, name)) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "更新数据失败"));
//        }
//
//        return ResponseUtils.output(response, new JsonResponse(1, "操作成功"));
//    }
//
//    private ModelAndView showErrorPage(String errorMsg) {
//        ModelAndView modelAndView = new ModelAndView("/error/error");
//        modelAndView.addObject("errorMsg", errorMsg);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/create", method = { RequestMethod.POST })
//    public ModelAndView createWarehouse(HttpServletRequest request, HttpServletResponse response) {
//        String name = ServletRequestUtils.getStringParameter(request, "name", "");
//
//        if (StringUtils.isBlank(name)) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "仓库名称不能为空"));
//        }
//        if (warehouseService.addBy(name) <= 0) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "创建仓库失败"));
//        }
//        return new ModelAndView(String.format("redirect:/%s/",PAGE_INDEX));
//    }
//
//    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
//    public ModelAndView deleteWarehouse(HttpServletRequest request, HttpServletResponse response) {
//
//        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
//
//        if (id <= 0) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "id不存在"));
//        }
//
//        if(!warehouseService.deleteBy(id)) {
//            return ResponseUtils.output(response, new JsonResponse(-1, "操作失败"));
//        }
//
//        return ResponseUtils.output(response, new JsonResponse(1, "操作成功"));
//    }

}
