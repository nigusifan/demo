package net.intelink.zmframework.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;
import java.util.*;

/**
 * xml工具类
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class XmlUtil {
    private Logger log = LoggerFactory.getLogger(XmlUtil.class);

    private String XML_ROOT = "xml";

    // 对所有xml节点的转换都增加CDATA标记
    private boolean cdata = false;

    public XmlUtil() {
    }

    public XmlUtil(boolean cdata) {
        this.cdata = cdata;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private XStream xstream = new XStream(new XppDriver(new NoNameCoder()) {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                public String encodeNode(String name) {
                    return name;
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write(cdataConvert(text));
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


    /**
     * bean to xml
     *
     * @param obj
     * @return
     */
    public String bean2XML(Object obj) {
        xstream.alias(XML_ROOT, obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * xml to bean
     *
     * @param xml
     * @param clz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T xml2bean(String xml, Class<T> clz) throws Exception {
        xstream.alias(XML_ROOT, clz);
        return (T) xstream.fromXML(xml, clz.newInstance());
    }

    /**
     * xml to map
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public Map<String, Object> xml2map(String xml) throws Exception {
        Document doc = DocumentHelper.parseText(xml);
        return (Map<String, Object>) xml2map(doc.getRootElement());
    }

    /**
     * map to xml
     *
     * @param map
     * @return
     */
    public String map2XML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<");
        sb.append(XML_ROOT);
        sb.append(">");
        mapToXML(map, sb);
        sb.append("</");
        sb.append(XML_ROOT);
        sb.append(">");
        try {
            return sb.toString();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private void mapToXML(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXML(hm, sb);
                }
                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXML((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    String val = "";
                    if (cdata) {
                        val = cdataConvert(value.toString());
                    } else {
                        val = value.toString();
                    }
                    sb.append("<" + key + ">" + val + "</" + key + ">");
                }

            }

        }
    }

    private String cdataConvert(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<![CDATA[");
        builder.append(text);
        builder.append("]]>");

        return builder.toString();
    }


    private Object xml2map(Element element) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Element> elements = element.elements();
        if (elements.size() == 0) {
            map.put(element.getName(), element.getText());
            if (!element.isRootElement()) {
                return element.getText();
            }
        } else if (elements.size() == 1) {
            map.put(elements.get(0).getName(), xml2map(elements.get(0)));
        } else if (elements.size() > 1) {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Element> tempMap = new HashMap<String, Element>();
            for (Element ele : elements) {
                tempMap.put(ele.getName(), ele);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = tempMap.get(string).getNamespace();
                List<Element> elements2 = element.elements(new QName(string, namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Object> list = new ArrayList<Object>();
                    for (Element ele : elements2) {
                        list.add(xml2map(ele));
                    }
                    map.put(string, list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    map.put(string, xml2map(elements2.get(0)));
                }
            }
        }

        return map;
    }

}
