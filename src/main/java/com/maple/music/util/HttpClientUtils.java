package com.maple.music.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class HttpClientUtils {
   private static PoolingHttpClientConnectionManager cm;
   static {
      cm = new PoolingHttpClientConnectionManager();
      cm.setMaxTotal(100); //设置最大连接数
      cm.setDefaultMaxPerRoute(10);  //设置每个主机的最大链接数
   }

   /**
    * 根据请求地址下载页面数据
    * @param url
    * @return 页面数据
    */
   public static String doGetHtml(String url){
      // 获取Httpclient对象
      CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
      // 创建httpGet对象，设置连接地址
      HttpGet httpGet = new HttpGet(url);
      // 设置请求信息
      httpGet.setConfig(getConfig());
      // 使用HttpClient发起请求，获取响应
      try (CloseableHttpResponse response=httpClient.execute(httpGet)) {
         // 解析响应，返回结果
         if(response.getEntity()!=null){
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            return content;
         }
      }catch (Exception e){
         e.printStackTrace();
      }
      return "";
   }

   /**
    * 根据请求地址获取图片
    * @param url
    * @return 图片名称
    */
   public static String doGetImage(String url){
      // 获取Httpclient对象
      CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
      // 创建httpGet对象，设置连接地址
      HttpGet httpGet = new HttpGet(url);
      // 设置请求信息
      httpGet.setConfig(getConfig());
      // 使用HttpClient发起请求，获取响应
      try (CloseableHttpResponse response=httpClient.execute(httpGet)) {
         // 解析响应，返回结果
         if(response.getEntity()!=null){
            // 下载图片
            // 获取文件后缀名
            String extName = url.substring(url.lastIndexOf("."));
            // 生成随机文件名
            String picName = UUID.randomUUID().toString()+extName;
            // 声明outputstream
            OutputStream outputStream = new FileOutputStream(new File("E:\\图片\\test"+picName));
            response.getEntity().writeTo(outputStream);
         }
      }catch (Exception e){
         e.printStackTrace();
      }
      return "";
   }
   /**
    * 设置请求信息
    * @return 请求信息配置对象
    */
   private static RequestConfig getConfig() {
      RequestConfig config = RequestConfig.custom()
              .setConnectTimeout(1000)    //创建连接的最长时间
              .setConnectionRequestTimeout(500)   // 获取连接的最长时间
              .setSocketTimeout(10000)    // 数据传输的最长时间
              .build();
      return config;
   }
}