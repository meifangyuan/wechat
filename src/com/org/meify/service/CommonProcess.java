/**  
* @Title: CreateMenu.java
* @Package com.org.meify.service
* @Description: TODO(用一句话描述该文件做什么)
* @author meify  
* @date 2017年7月30日 下午12:00:17
* @version V1.0  
*/
package com.org.meify.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.org.meify.common.Constant;
import com.org.meify.util.HttpUtil;
/**
 * @ClassName: CreateMenu
 * @Description: 创建公众号菜单
 * @author meify
 * @date 2017年7月30日 下午12:00:17
 *
 */
public class CommonProcess {

	private static final String MENU_STR = "{\"button\":["
			+ "{\"name\":\"关于我们\",\"sub_button\":["
			+ "{\"name\":\"导师介绍\", \"type\":\"click\", \"key\":\"teacher_introduction\"},"
			+ "{\"name\":\"团队介绍\", \"type\":\"click\", \"key\":\"team_introduction\"}"
			+ "]}"
			+ "]}";


//	private static final String MENU_STR1 = 
//			"{
//		      \"button\":[
//		      {
//		           \"name\":\"关于我们\",
//		           \"sub_button\":[
//		            {
//		               \"type\":\"click\",
//		               \"name\":\"导师介绍\",
//		               \"key\":\"teacher_introduction\"
//		            },
//		            {
//		               "\type\":\"click\",
//		               "\name\":\"团队介绍\",
//		               "\key\":\"\team_introduction\"
//		            },
//		            {
//		               "\type\":\"click\",
//		               "\name\":\"联系我们\",
//		               "\key\":\"call_us\"
//		            }]
//		       },
//		       {
//		           "\name\":\"学术研究\",
//		           "\sub_button\":[
//		            {
//		               \"type\":\"click\",
//		               \"name\":\"研究方向\",
//		               \"key\":\"research_area\"
//		            },
//		            {
//		               \"type\":\"click\",
//		               \"name\":\"发表论文\",
//		               \"key\":\"published_papers\"
//		            }]
//		       },
//		       {
//		           \"name\":\"横向项目\",
//		           \"sub_button\":[
//		            {
//		               \"type\":\"click\",
//		               \"name\":\"项目介绍\",
//		               \"key\":\"project_introduction\"
//		            },
//		            {
//		               \"type\":\"click\",
//		               \"name\":\"技术领域\",
//		               \"key\":\"project_technology\"
//		            }]
//		       }]
//		   }";
	/**
	* @Title: getAccessToken
	* @Description: 获取AccessToken
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String getAccessToken() {

		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String params = "grant_type=client_credential&appid=" + Constant.APP_ID + "&secret=" + Constant.APP_SECRET;
		String accessToken = null;
		try {
			String res = HttpUtil.get(url, params);
			System.out.println("res===" + res);
			JSONObject demoJson = new JSONObject(res);
            accessToken = demoJson.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	/**
	* @Title: createMenu
	* @Description: 创建微信公众号菜单
	* @param @param menuStr
	* @param @param accessToken    设定文件
	* @return void    返回类型
	* @throws
	*/ 
	public static String createMenu() {

        String accessToken= getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
        
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(MENU_STR);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
	}
	
	/**
	* @Title: getUserInfo
	* @Description: 获取用户基本信息
	* @param @param openId
	* @param @return    设定文件
	* @return Map<String,String>    返回类型
	* @throws
	*/ 
	public static Map<String, String> getUserInfo(String openId) {
		Map<String, String> userInfo = new HashMap<String, String> ();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info";
		String accessToken = getAccessToken();
		String params = "access_token=" + accessToken +"&openid=" + openId;
		String result = null;
		try {
			result = HttpUtil.get(url, params);
			System.out.println("person info===" + result);
			JSONObject json = new JSONObject(result);
			userInfo.put("nickname", json.getString("nickname"));
			userInfo.put("sex", String.valueOf(json.getInt("sex")));
			userInfo.put("language", json.getString("language"));
			userInfo.put("city", json.getString("city"));
			userInfo.put("country", json.getString("country"));
			userInfo.put("province", json.getString("province"));
			userInfo.put("headimgurl", json.getString("headimgurl"));
			userInfo.put("subscribe_time", String.valueOf(json.getLong("subscribe_time")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userInfo;
	}
	
	public static void main(String[] args) {
//		String accessToken = getAccessToken();
//		System.out.println("accessToken====" + accessToken);
		
		String res = createMenu();
		System.out.println(res);
	}
	
	
}
