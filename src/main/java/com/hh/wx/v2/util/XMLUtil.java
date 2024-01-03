package com.hh.wx.v2.util;


import cn.hutool.core.util.StrUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  XML 工具
 *
 */
public class XMLUtil {

	static String getRequestResult(HttpServletRequest request) throws IOException {
		InputStream is = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		is.close();
		return outSteam.toString();
	}

	public static Map<String, Object> decodeXml(String content) {

        Map<String, Object> xml = new HashMap<String, Object>();
        try {
            //创建一个SAXBuilder对象
            SAXBuilder saxBuilder = new SAXBuilder();
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            InputStream xmlInput = new ByteArrayInputStream(bytes);
            //读取prop.xml资源
            Document doc = saxBuilder.build(xmlInput);
            //获取根元素(prop)
            Element root = doc.getRootElement();
            //获取根元素下面的所有子元素(mess)
			List<Element> messList = root.getChildren();
            for (Element element : messList) {
                xml.put(element.getName(), element.getValue());
            }
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

		return xml;

    }



}
