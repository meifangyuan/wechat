package com.org.meify.util;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.org.meify.message.Article;
import com.org.meify.message.ImageMessage;
import com.org.meify.message.NewsMessage;
import com.org.meify.message.TextMessage;
import com.org.meify.message.VedioMessage;
import com.org.meify.message.VoiceMessage;

/**
* @ClassName: XmlUtil
* @Description: 将响应的MessageBean转为xml字符串的工具类
* @author meify
* @date 2017年7月28日 上午9:32:22
*
*/ 
public class XmlUtil {
	
	/**
	* @Title: resovleXml
	* @Description: 解析微信服务器转发过来的xml参数内容
	* @param @param request
	* @param @return    设定文件
	* @return Map<String,String>    返回类型
	* @throws
	*/ 
	public static Map<String, String> resovleXml(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		
        try {
			SAXReader reader = new SAXReader();            // 使用dom4j解析xml
			InputStream ins = request.getInputStream(); // 从request中获取输入流
			Document doc = reader.read(ins);
			 
			Element root = doc.getRootElement();         // 获取根元素
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();        // 获取所有节点
			 
			for (Element e : list) {
			    map.put(e.getName(), e.getText()); 
			    System.out.println(e.getName() + "--->" + e.getText());
			}
			ins.close();
		} catch (Exception e) {
			System.err.println("解析xml出现异常");
			e.printStackTrace();
		}
        
        return map;
	}
	
	/**
	* @Title: formatTextMessage
	* @Description: 封装Text消息
	* @param  textMessage
	* @return String    返回类型
	* @throws
	*/ 
	public static String formatTextMessage(TextMessage textMessage) {
		Date date =  new Date();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(textMessage.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(textMessage.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(date.getTime()).append("</CreateTime>");  
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[").append(textMessage.getContent()).append("]]></Content>");
        sb.append("<FuncFlag>0</FuncFlag>");  
        sb.append("</xml>");
        return sb.toString();  
	}
	
	/**
	* @Title: formatImageMessage
	* @Description: 封装Image类型消息
	* @param @param imageMessage
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String formatImageMessage(ImageMessage imageMessage) {
		Date date =  new Date();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(imageMessage.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(imageMessage.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(date.getTime()).append("</CreateTime>");  
        sb.append("<MsgType><![CDATA[image]]></MsgType>");
        sb.append("<Image><MediaId><![CDATA[").append(imageMessage.getMediaId()).append("]]></MediaId></Image>");
        sb.append("<FuncFlag>0</FuncFlag>");  
        sb.append("</xml>");

        return sb.toString();  
	}
	
	/**
	* @Title: formatVoiceMessage
	* @Description: 封装Voice类型消息
	* @param @param voiceMessage
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String formatVoiceMessage(VoiceMessage voiceMessage) {
		Date date =  new Date();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(voiceMessage.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(voiceMessage.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(date.getTime()).append("</CreateTime>");  
        sb.append("<MsgType><![CDATA[voice]]></MsgType>");
        sb.append("<Voice><MediaId><![CDATA[").append(voiceMessage.getMediaId()).append("]]></MediaId></Voice>");
        sb.append("<FuncFlag>0</FuncFlag>");  
        sb.append("</xml>");
        
        return sb.toString();  
	}
	

	/**
	* @Title: formatVedioMessage
	* @Description: 封装Vedio类型消息
	* @param @param vedioMessage
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String formatVedioMessage(VedioMessage vedioMessage) {
		Date date =  new Date();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(vedioMessage.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(vedioMessage.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(date.getTime()).append("</CreateTime>");  
        sb.append("<MsgType><![CDATA[vedio]]></MsgType>");
		sb.append("<Video>");
		sb.append("<MediaId><![CDATA[").append(vedioMessage.getMediaId()).append("]]></MediaId>");
		sb.append("<Title><![CDATA[").append(vedioMessage.getTitle()).append("]]></Title>");
		sb.append("<Description><![CDATA[").append(vedioMessage.getTitle()).append("]]></Description>");
		sb.append("<Video>");
		sb.append("</xml>");
		
        return sb.toString();  
	}
	

	/**
	* @Title: formatNewsMessage
	* @Description: 封装news类型消息
	* @param @param newsMessage
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String formatNewsMessage(NewsMessage newsMessage) {
		Date date =  new Date();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(newsMessage.getToUserName()).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(newsMessage.getFromUserName()).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(date.getTime()).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>").append(newsMessage.getArticleCount()).append("</ArticleCount>");
		sb.append("<Articles>");
		for(Article article : newsMessage.getArticles()) {
			sb.append("<item>");
			sb.append("<Title><![CDATA[").append(article.getTitle()).append("]]></Title>");
			sb.append("<Description><![CDATA[").append(article.getDescription()).append("]]></Description>");
			sb.append("<PicUrl><![CDATA[").append(article.getPicUrl()).append("]]></PicUrl>");
			sb.append("<Url><![CDATA[").append(article.getUrl()).append("]]></Url>");
			sb.append("</item>");
		}
		sb.append("</xml>");
		
        return sb.toString();  
	}
	
}
