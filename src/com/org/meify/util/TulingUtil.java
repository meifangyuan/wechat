/**  
* @Title: TulingUtil.java
* @Package com.org.meify.util
* @Description: TODO(用一句话描述该文件做什么)
* @author meify  
* @date 2017年7月31日 上午10:54:24
* @version V1.0  
*/
package com.org.meify.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

/**
* @ClassName: TulingUtil
* @Description: 图灵机器人助手
* @author meify
* @date 2017年7月31日 上午10:54:24
*
*/
public class TulingUtil {
	
	/**
	* @Title: getResponse
	* @Description: 获取问题的自动回复答案
	* @param @param question
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public String getResponse(String question) {
		String resp = null;
		try {
			String APIKEY = "e7a1447ed2182d57758ca845e5a0f36e";
			String INFO = URLEncoder.encode(question, "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			
			JSONObject jsonObj = new JSONObject(sb.toString());
			resp = jsonObj.getString("text");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
}
