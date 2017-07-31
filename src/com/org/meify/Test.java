/**  
* @Title: Test.java
* @Package com.org.meify
* @Description: TODO(用一句话描述该文件做什么)
* @author meify  
* @date 2017年7月31日 上午10:50:30
* @version V1.0  
*/
package com.org.meify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @ClassName: Test
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author meify
 * @date 2017年7月31日 上午10:50:30
 *
 */
public class Test {
	public static void main(String[] args) {
		try {
			String APIKEY = "e7a1447ed2182d57758ca845e5a0f36e";
			String question = "你叫什么名字？";// 这是上传给云机器人的问题
			// String INFO = URLEncoder.encode("北京今日天气", "utf-8");
			String INFO = URLEncoder.encode(question, "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();

			// 取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			System.out.println(sb);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
