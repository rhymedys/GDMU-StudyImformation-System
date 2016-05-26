package com.example.learn.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection.Response;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Text;

import com.example.learn.MainActivity;
import com.example.learn.ui.Fragment_Login;

import org.apache.http.NameValuePair;

import android.R.string;
import android.content.Context;
import android.os.IBinder.DeathRecipient;
import android.util.Log;
import android.widget.Toast;




public class HttpUtil {
	// Httpget请求
	
	public static void httpGet(final String param,
			final HttpCallbackListener listener) {
		Log.i("HttpUtil", "url=" + param);
		try {
			HttpGet httpGet = new HttpGet(param);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.i("HttpUtil","httpget"+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();

				String re = EntityUtils.toString(entity, "utf-8");
				Log.i("HttpUtil", re);

				listener.onFinish(re);
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}

	}

	// Httpget请求 带cookie
	public static void httpGet(final String param, final String cookie,
			final HttpCallbackListener listener) {
		Log.i("HttpUtil", "url=" + param);
		try {
			HttpGet httpGet = new HttpGet(param);
			HttpClient httpClient = new DefaultHttpClient();
			httpGet.setHeader("cookie", "JSESSIONID=" + cookie);
			// // 请求超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 20000);
			// // 读取超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 20000);Httpget 带cookie

			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.i("HttpUtil", "Httpget 带cookie"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String re = EntityUtils.toString(entity, "GBK");
				listener.onFinish(re);
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}

	}

	// HttpPost void 带提交param数据
	public String httpPost(final String param, List<NameValuePair> postParams,
			final HttpCallbackListener listener) {
			String a="error";
			try {
				HttpPost httpPost = new HttpPost(param);
//				httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
				HttpClient httpClient = new DefaultHttpClient();
				// // 请求超时
				// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				// 20000);
				// // 读取超时
				// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				// 20000);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams,
						"utf-8");
				httpPost.setEntity(entity);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.i("HttpUtil", "post"+httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					InputStream inputStream = httpResponse.getEntity().getContent();
//					String cookie =httpResponse.getFirstHeader("Set-Cookie").getValue();
					String  JSESSIONID= ((AbstractHttpClient) httpClient).getCookieStore()
					.getCookies().get(0).getValue();
					String SECURE_AUTH_ROOT_COOKIE=((AbstractHttpClient) httpClient).getCookieStore()
							.getCookies().get(1).getValue();
					String SECURITY_AUTHENTICATION_COOKIE=((AbstractHttpClient) httpClient).getCookieStore()
							.getCookies().get(2).getValue();
					InputStreamReader inputRead = new InputStreamReader(
							inputStream, "GBK");
				
					String newcookies="JSESSIONID="+JSESSIONID+";"+"SECURE_AUTH_ROOT_COOKIE="+SECURE_AUTH_ROOT_COOKIE+";"+"SECURITY_AUTHENTICATION_COOKIE="+SECURITY_AUTHENTICATION_COOKIE;
					BufferedReader bufferReader = new BufferedReader(inputRead);
					String data = "";
					StringBuffer stringBuffer = new StringBuffer();
					
					while ((data = bufferReader.readLine()) != null) {
						stringBuffer.append(data);
						
					}
					

					Log.i("HttpUtil", stringBuffer.toString());
					//以下3个log为调试信息
					Log.i("JSESSIONID", newcookies);
					Log.i("security_root_cookie", SECURE_AUTH_ROOT_COOKIE);
					Log.i("security_authentication_cookie", SECURITY_AUTHENTICATION_COOKIE);
					listener.onFinish(stringBuffer.toString());	
					return newcookies;
				}	
				else
				{
					return a;
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("HttpUtil", e.toString());
				listener.onError(e);
				return a;
			}
				
				
			}



	// HttpPost带cookie  带提交param数据
	public void httpPost(final String param, final String cookie,
			List<NameValuePair> postParams, final HttpCallbackListener listener) {
		try {
			HttpPost httpPost = new HttpPost(param);
//			httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
			HttpClient httpClient = new DefaultHttpClient();
			
			httpPost.addHeader("cookie",cookie);
//			Log.i("JSESSIONID", JSESSIONID);
//			Log.i("security_root_cookie", SECURE_AUTH_ROOT_COOKIE);
//			Log.i("security_authentication_cookie", SECURITY_AUTHENTICATION_COOKIE);
			// // 请求超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 20000);
			// // 读取超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 20000);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams,
					"utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("HttpUtil",httpResponse.getStatusLine().getStatusCode()+"");
			Log.i("cookie", cookie);//调试 
			Log.i("HttpUtil", "cookie  ,post"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 302) {
//				String locationUrl=httpResponse.getLastHeader("Location").getValue(); 
				InputStream inputStream = httpResponse.getEntity().getContent();
				InputStreamReader inputRead = new InputStreamReader(
						inputStream, "GBK");
				BufferedReader bufferReader = new BufferedReader(inputRead);
				String data = "";
				StringBuffer stringBuffer = new StringBuffer();
				while ((data = bufferReader.readLine()) != null) {
					stringBuffer.append(data);
				}
				Log.i("HttpUtil", stringBuffer.toString());
				listener.onFinish(stringBuffer.toString());
			}
			else if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStreams = httpResponse.getEntity().getContent();
				InputStreamReader inputReads = new InputStreamReader(
						inputStreams, "GBK");
				BufferedReader bufferReaders = new BufferedReader(inputReads);
				String datas = "";
				StringBuffer stringBuffers = new StringBuffer();
				while ((datas = bufferReaders.readLine()) != null) {
					stringBuffers.append(datas);
				}
				Log.i("HttpUtil", stringBuffers.toString());
				listener.onFinish(stringBuffers.toString());
			}
			
		}
			catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}
	}
	
	//httppost 不带postparam数据
	public void httpPost(final String param, final String cookie,
			 final HttpCallbackListener listener) {
		try {
			HttpPost httpPost = new HttpPost(param);
			HttpClient httpClient = new DefaultHttpClient();			
			httpPost.addHeader("cookie",cookie);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("HttpUtil",httpResponse.getStatusLine().getStatusCode()+"");
			Log.i("cookie", cookie);//调试 
			Log.i("HttpUtil", "cookie  ,post"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStreams = httpResponse.getEntity().getContent();
				InputStreamReader inputReads = new InputStreamReader(
						inputStreams, "GBK");
				BufferedReader bufferReaders = new BufferedReader(inputReads);
				String datas = "";
				StringBuffer stringBuffers = new StringBuffer();
				while ((datas = bufferReaders.readLine()) != null) {
					stringBuffers.append(datas);
				}
				Log.i("HttpUtil", stringBuffers.toString());
				listener.onFinish(stringBuffers.toString());
			}
			
		}
			catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}
	}
	

	// HttpPost带cookie    以下 代码不用管
	public void httpLoginPost(final String param, final String cookie,
			String id,String password,String checkCode, final HttpCallbackListener listener) {
		try{  
            //通过openConnection 连接  
            URL url = new java.net.URL(param);  
            HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();  
            //设置输入和输出流   
            urlConn.setDoOutput(true);  
            urlConn.setDoInput(true);  
            urlConn.setRequestMethod("POST");  
            urlConn.setUseCaches(false);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的    
            urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            urlConn.setRequestProperty("JSESSIONID",cookie);
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，  
            // 要注意的是connection.getOutputStream会隐含的进行connect。    
            urlConn.connect();  
            //DataOutputStream流  
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());  
            //要上传的参数  
            String content = "j_password=" + URLEncoder.encode(password, "UTF_8")+
            		"j_username=" +URLEncoder.encode(id, "UTF_8")+
            		"&RANDOMCODE="+ URLEncoder.encode(checkCode, "UTF_8");   
            //将要上传的内容写入流中  
            out.writeBytes(content);     
            //刷新、关闭  
            OutputStream os = urlConn.getOutputStream();  
//            os.write(data.getBytes());  
            os.flush();  
            Log.i("HttpUtil", urlConn.getResponseCode()+"");
            if (urlConn.getResponseCode() == 200) { 
            	listener.onFinish("");
            }   
            out.close();     
              
        }catch(Exception e){   
        	listener.onError(e); 
        } 
	}
}
