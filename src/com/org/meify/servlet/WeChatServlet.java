package com.org.meify.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.meify.service.WeChatProcess;
import com.org.meify.util.SignUtil;

/**
 * @ClassName: WeChatServlet
 * @Description: 微信后台入口类
 * @author meify
 * @date 2017年7月28日 上午9:27:55
 *
 */
public class WeChatServlet extends HttpServlet {

	private static final long serialVersionUID = 7172675041551970727L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("doGet()...");
			String signature = request.getParameter("signature");  
			String timestamp = request.getParameter("timestamp");  
			String nonce = request.getParameter("nonce");  
			String echostr = request.getParameter("echostr");  
			System.out.println(signature);
			System.out.println(timestamp);
			System.out.println(nonce);
			System.out.println(echostr);
  
			PrintWriter out = response.getWriter();  
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
				System.out.println("确认为来自微信服务器的消息");
			    out.print(echostr);  
			}  
			out.close();  
			out = null;
		} catch (IOException e) {
			System.out.println("doGet()发生异常");
			e.printStackTrace();
		}  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
    		request.setCharacterEncoding("utf-8");
            response.setContentType("text/xml;charset=utf-8");
            String res = new WeChatProcess().processXml(request);
            
            PrintWriter out = response.getWriter();
            out.print(res);  
            out.flush();
            out.close();
        } catch (Exception e) {
        	System.out.println("doPost()出现异常");
            e.printStackTrace();
        }
	}
}
