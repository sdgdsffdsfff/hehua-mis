package com.hehua.mis.utils.excel;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * 类DownloadContentHelper.java的实现描述：TODO 类实现描述
 * 
 * @author root 2011-8-7 下午03:05:18
 */
@SuppressWarnings("unchecked")
public class DownloadContentHelper {

	private static Map              map         = new HashMap();
    private static Map             downloadMap = new HashMap();
    private static Map<String, Map> codeMaps    = new HashMap();

    private static void initial() throws Exception {
        Document document = null;
        InputStream input = null;
        SAXReader saxReader = new SAXReader();

        String type = "";

        input = Thread.currentThread().getContextClassLoader().getResourceAsStream("common/DownloadContent.xml");
        document = saxReader.read(input);

        codeMaps = new HashMap();

        List list = document.selectNodes("//description-content/desc");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DownloadBean downloadBean = new DownloadBean();
            String content = "";
            String value = "";
            List valueList = new ArrayList();

            Element element = (Element) it.next();

            type = element.attribute("id").getValue();
            content = element.attribute("content").getValue();

            if (element.attribute("name") != null) {
                initDownloadBean(element, downloadBean);
            }
            valueList.add(content);

            Iterator iterator = element.elementIterator("desc-rule");
            while (iterator.hasNext()) {
                Element e = (Element) iterator.next();
                value = e.attribute("value").getValue();
                valueList.add(value);

                Iterator attributeIt = e.elementIterator("attribute");
                if (attributeIt != null) {
                    while (attributeIt.hasNext()) {
                        Element attributeElement = (Element) attributeIt.next();
                        String attribute = attributeElement.getText();
                        valueList.add(attribute);
                    }
                }
            }
            map.put(type, valueList);
        }
    }

    private static void initDownloadBean(Element element, DownloadBean bean) throws Exception {
        bean.setId(element.attribute("id").getValue());
        bean.setContent(element.attribute("content").getValue());

        Iterator iterator = element.elementIterator("desc-rule");
        while (iterator.hasNext()) {
            DownloadAttribute attributeBean = new DownloadAttribute();
            Element e = (Element) iterator.next();
            String value = e.attribute("value").getValue();
            attributeBean.setRowName(value);

            List list = e.elements("attribute");
            if ((list != null) && (list.size() == 2)) {
                Element attributeElement = (Element) list.get(0);

                attributeBean.setExcelName(attributeElement.getText());
                attributeElement = (Element) list.get(1);

                attributeBean.setExcelType(attributeElement.getText());
            }

            Element formatElement = e.element("format");
            if (formatElement != null) {
                String format = formatElement.getTextTrim();
                if ("Code".equals(attributeBean.getExcelType())) attributeBean.setCodeMap((Map) codeMaps.get(format));
                else {
                    attributeBean.setFormat(format);
                }
            }

            bean.getAttributes().add(attributeBean);
        }
        downloadMap.put(bean.getId(), bean);
    }

    public static DownloadBean getDownloadBean(String id) throws Exception {
        DownloadBean downloadBean = (DownloadBean) downloadMap.get(id);
        if (downloadBean == null) {
            initial();
            downloadBean = (DownloadBean) downloadMap.get(id);
        }
        return downloadBean;
    }

    public static String getContent(String idKey) throws Exception {
        String content = "";
        if (map != null) {
            List list = (List) map.get(idKey);
            if ((list == null) || (list.size() < 0)) {
                list = dynamicLoad(list, idKey);
            }

            content = (String) list.get(0);
        }
        return content;
    }

    public static String getDescription(String idKey, Map rultValueMap) throws Exception {
        String rult = "";
        if (map != null) {
            List list = (List) map.get(idKey);
            if ((list == null) || (list.size() < 0)) {
                list = dynamicLoad(list, idKey);
            }
            if (list.size() >= 2) {
                rult = (String) list.get(1);
            }
        }
        String desc = getDescByRult(rult, rultValueMap);

        return desc;
    }

    public static List getAttributes(String idKey) throws Exception {
        List list = (List) map.get(idKey);

        if ((list != null) && (list.size() < 0)) {
            list = dynamicLoad(list, idKey);
        }

        if ((list != null) && (list.size() >= 2)) {
            list = list.subList(2, list.size());
        }
        return list;
    }

    public static List getAllConfigValues(String idKey, Map rultValueMap) throws Exception {
        List list = (List) map.get(idKey);
        List attributesList = new ArrayList();

        if ((list != null) && (list.size() < 0)) {
            list = dynamicLoad(list, idKey);
        }
        if ((list != null) && (list.size() >= 2)) {
            String rult = (String) list.get(1);
            String desc = getDescByRult(rult, rultValueMap);
            for (int i = 0; i < list.size(); i++) {
                if (i == 1) attributesList.add(desc);
                else {
                    attributesList.add(list.get(i));
                }
            }
        }
        return attributesList;
    }

    private static String getDescByRult(String rult, Map param) {
        String desc = rult;
        Iterator<Entry<String, String>> entryKeyIterator = param.entrySet().iterator();
        while (entryKeyIterator.hasNext()) {
            Entry<String, String> it = entryKeyIterator.next();
            String key = it.getKey();
            String value = it.getValue();

            desc = StringUtils.replace(desc, "{" + key + "}", "\"" + value + "\"");
        }
        return desc;
    }

    private static List dynamicLoad(List list, String idKey) throws Exception {
        if ((list == null) || (list.size() < 0)) {
            initial();
            list = (List) map.get(idKey);
        }
        return list;
    }

    static {
        try {
            initial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) throws Exception{
//    	initial();
//    }
}
