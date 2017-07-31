package com.org.meify.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.meify.common.Constant;
import com.org.meify.message.Article;
import com.org.meify.message.ImageMessage;
import com.org.meify.message.NewsMessage;
import com.org.meify.message.TextMessage;
import com.org.meify.message.VedioMessage;
import com.org.meify.message.VoiceMessage;
import com.org.meify.util.TulingUtil;
import com.org.meify.util.XmlUtil;

/**
* @ClassName: WeChatProcess
* @Description: 业务处理类
* @author meify
* @date 2017年7月28日 上午9:29:34
*
*/ 
public class WeChatProcess {

	public  String processXml(HttpServletRequest request) {
		String res = "";
		try {
			// 解析请求中的所有参数
			Map<String, String> requestMap = XmlUtil.resovleXml(request);
			
			String msgType = requestMap.get("MsgType");
			System.out.println("msg type===" + msgType);

			if(msgType.equals("text")) {
				String question = requestMap.get("Content");
				String content = new TulingUtil().getResponse(question);
				res = processTextMessage(requestMap, content);
			} else if(msgType.equals("image")) {
				res = processImageMessage(requestMap);
			} else if(msgType.equals("voice")) {
				res = processVoiceMessage(requestMap);
			} else if(msgType.equals("vedio")) {
				res = processVedioMessage(requestMap);
			} else if(msgType.equals("shortvideo")) {
				res = processTextMessage(requestMap, "接收到短视频消息");
			} else if(msgType.equals("location")) {
				res = processLocationMessage(requestMap);
			} else if(msgType.equals("link")) {
				res = processLinkMessage(requestMap);
			} else if(msgType.equals("event")) {	// 事件消息
				String eventType = requestMap.get("Event");
				if(eventType.equals("subscribe")) {
					res = processSubscribeMessage(requestMap);
				} else if(eventType.equals("unsubscribe")) {
					
				} else if(eventType.equals("LOCATION")) {
					res = processLocationMessage(requestMap);
				} else if(eventType.equals("CLICK")) {	// 菜单点击事件
					String eventKey = requestMap.get("EventKey");
					if(eventKey.equals("teacher_introduction")) {
						res = teacherIntroduction(requestMap);
					} else if(eventKey.equals("team_introduction")) {
						res = teamIntroduction(requestMap);
					}
					//res = processTextMessage(requestMap, "菜单点击事件,key："+eventKey);
				}
			} else {
				res = processTextMessage(requestMap, "未知操作！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("响应信息：");
		System.out.println(res);
		return res;
	}
	
	/**
	* @Title: processTextMessage
	* @Description: 处理文本消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processTextMessage(Map<String, String> requestMap, String content) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		Date date =  new Date();
		
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(date.getTime());
		textMessage.setMsgType("text");
		textMessage.setContent(content);
		return XmlUtil.formatTextMessage(textMessage);
	}
	
	/**
	* @Title: processImageMessage
	* @Description: 处理图片消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processImageMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String mediaId = requestMap.get("MediaID");
		Date date =  new Date();
		
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setCreateTime(date.getTime());
		imageMessage.setMsgType("image");
		imageMessage.setMediaId(mediaId);
		return XmlUtil.formatImageMessage(imageMessage);
	}
	
	/**
	* @Title: processVoiceMessage
	* @Description: 处理语音消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processVoiceMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String mediaId = requestMap.get("MediaID");
		Date date =  new Date();
		
		VoiceMessage voiceMessage = new VoiceMessage();
		voiceMessage.setFromUserName(toUserName);
		voiceMessage.setToUserName(fromUserName);
		voiceMessage.setCreateTime(date.getTime());
		voiceMessage.setMsgType("voice");
		voiceMessage.setMediaId(mediaId);
		return XmlUtil.formatVoiceMessage(voiceMessage);
	}
	
	/**
	* @Title: processVedioMessage
	* @Description: 处理视频消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processVedioMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String mediaId = requestMap.get("MediaID");
		Date date =  new Date();
		
		VedioMessage vedioMessage = new VedioMessage();
		vedioMessage.setFromUserName(toUserName);
		vedioMessage.setToUserName(fromUserName);
		vedioMessage.setCreateTime(date.getTime());
		vedioMessage.setMsgType("vedio");
		vedioMessage.setMediaId(mediaId);
		return XmlUtil.formatVedioMessage(vedioMessage);
	}
	
	/**
	* @Title: processShortVedioMessage
	* @Description: 处理短视频消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processShortVedioMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String mediaId = requestMap.get("MediaID");
		Date date =  new Date();
		
		VedioMessage vedioMessage = new VedioMessage();
		vedioMessage.setFromUserName(toUserName);
		vedioMessage.setToUserName(fromUserName);
		vedioMessage.setCreateTime(date.getTime());
		vedioMessage.setMsgType("shortvideo");
		vedioMessage.setMediaId(mediaId);
		return XmlUtil.formatVedioMessage(vedioMessage);
	}
	
	/**
	* @Title: processLocationMessage
	* @Description: 处理位置消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public   String processLocationMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		Date date =  new Date();
		
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(date.getTime());
		textMessage.setMsgType("text");
		textMessage.setContent("接收到位置消息");
		return XmlUtil.formatTextMessage(textMessage);
	}
	
	/**
	* @Title: processLinkMessage
	* @Description: 处理链接消息
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processLinkMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		Date date =  new Date();
		
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(date.getTime());
		textMessage.setMsgType("text");
		textMessage.setContent("接收到链接消息");
		return XmlUtil.formatTextMessage(textMessage);
	}
	
	/**
	* @Title: processSubscribeMessage
	* @Description: 处理用户关注事件
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String processSubscribeMessage(Map<String, String> requestMap) {
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		Date date =  new Date();
		
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(date.getTime());
		textMessage.setMsgType("text");
		
		Map<String, String> userInfo = CommonProcess.getUserInfo(fromUserName);
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您关注本公众号\n");
		sb.append(userInfo.get("nickname") + "\n");
		sb.append("性别：" + (userInfo.get("sex").equals("1") ? "男":"女") + "\n");
		sb.append("国籍：" + userInfo.get("country") + "\n");
		sb.append("省份：" + userInfo.get("province") + "\n");
		sb.append("城市：" + userInfo.get("city") + "\n");
		sb.append("语言：" + userInfo.get("language") + "\n");

		textMessage.setContent(sb.toString());
		return XmlUtil.formatTextMessage(textMessage);
	}
	
	/**
	* @Title: teaherIntroduction
	* @Description: 导师介绍
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public String teacherIntroduction(Map<String, String> requestMap) {
		NewsMessage teacherIntroduction = new NewsMessage();
		teacherIntroduction.setFromUserName(requestMap.get("ToUserName"));
		teacherIntroduction.setToUserName(requestMap.get("FromUserName"));
		teacherIntroduction.setCreateTime(new Date().getTime());
		teacherIntroduction.setMsgType("news");
		
		Article news = new Article();
		news.setTitle("梅方元");
		news.setDescription("个人简介");
		String picUrl = Constant.APP_URL + "images/meify.jpg";
		String url = Constant.APP_URL + "pages/meify.html";
		news.setPicUrl(picUrl);
		news.setUrl(url);
		
		List<Article> list = new ArrayList<Article>();
		list.add(news);
		teacherIntroduction.setArticleCount(list.size());
		teacherIntroduction.setArticles(list);
		
		return XmlUtil.formatNewsMessage(teacherIntroduction);
	}
	
	
	/**
	* @Title: teamIntroduction
	* @Description: 实验室团队介绍
	* @param @param requestMap
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public  String teamIntroduction(Map<String, String> requestMap) {
		NewsMessage teamIntroduction = new NewsMessage();
		teamIntroduction.setFromUserName(requestMap.get("ToUserName"));
		teamIntroduction.setToUserName(requestMap.get("FromUserName"));
		teamIntroduction.setCreateTime(new Date().getTime());
		teamIntroduction.setMsgType("news");
		
		Article member0 = new Article();
		member0.setTitle("后羿");
		member0.setDescription("射手");
		member0.setPicUrl("");
		member0.setUrl("");
		
		Article member1 = new Article();
		member1.setTitle("妲己");
		member1.setDescription("法师");
		member1.setPicUrl("");
		member1.setUrl("");
		
		Article member2 = new Article();
		member2.setTitle("项羽");
		member2.setDescription("肉");
		member2.setPicUrl("");
		member2.setUrl("");
		
		List<Article> list = new ArrayList<Article>();
		list.add(member0);
		list.add(member1);
		list.add(member2);
		teamIntroduction.setArticleCount(list.size());
		teamIntroduction.setArticles(list);
		
		return XmlUtil.formatNewsMessage(teamIntroduction);
	}
	
	
}
