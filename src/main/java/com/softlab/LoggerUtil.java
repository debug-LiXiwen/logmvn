package com.softlab;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiXiwen on 2019/6/9 18:47.
 **/
public class LoggerUtil {

    public void setLog(String level, String application, String tag, Date timestamp, String content){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        LogVo logVo = new LogVo();
        logVo.setLevel(level);
        logVo.setApplication(application);
        logVo.setContent(content);
        logVo.setTag(tag);
        logVo.setTimestamp(sdf.format(timestamp));
        // 1 创建HttpClinet，相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建发送POST请求
        HttpPost httpPost = new HttpPost("http://222.27.227.103:8080/producer/send");
        ((HttpEntityEnclosingRequest) httpPost).setEntity(
                new StringEntity(JSON.toJSONString(logVo),
                        ContentType.create("application/json", "UTF-8")));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getEntity() != null) {
                System.out.println(""+response.getStatusLine().getStatusCode()+
                        EntityUtils.toString(response.getEntity(), "UTF-8"));
            } else {
                System.out.println(""+response.getStatusLine().getStatusCode()+ "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
