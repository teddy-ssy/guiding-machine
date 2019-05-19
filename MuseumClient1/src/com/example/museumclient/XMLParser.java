package com.example.museumclient;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;
  
public class XMLParser { 
  
	// 构造方法 
    public XMLParser() { 
  
    } 
  
    /**  
    * 从URL获取XML使HTTP请求 
    * @param url string  
    * */  

    public String getXmlFromUrl(String url) { 
        String xml = null; 
  
        try { 
            // defaultHttpClient 
            DefaultHttpClient httpClient = new DefaultHttpClient(); 
            HttpPost httpPost = new HttpPost(url); 
  
            HttpResponse httpResponse = httpClient.execute(httpPost); 
            HttpEntity httpEntity = httpResponse.getEntity(); 
            xml = EntityUtils.toString(httpEntity); 
            
         // 鐢熸垚涓�涓姹傚璞★紝鍙傛暟灏辨槸鍦板潃
			HttpGet httpGet = new HttpGet(url);
			// 鐢熸垚Http瀹㈡埛绔�
			HttpClient httpClientGet = new DefaultHttpClient();  
			// 鍙戦�佽姹傜殑鍝嶅簲
			httpResponse = httpClientGet.execute(httpGet);
			// 浠ｈ〃鎺ユ敹鐨刪ttp娑堟伅锛屾湇鍔″櫒杩斿洖鐨勬秷鎭兘鍦╤ttpEntity
			httpEntity = httpResponse.getEntity();
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				 xml = EntityUtils.toString(httpEntity); 
			}
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return xml; 
    } 
  
    /** 
     * 鑾峰彇XML DOM鍏冪礌
     * @param XML string 
     * */
    public Document getDomElement(String xml){ 
        Document doc = null; 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
        try { 
  
            DocumentBuilder db = dbf.newDocumentBuilder(); 
  
            InputSource is = new InputSource(); 
                is.setCharacterStream(new StringReader(xml)); 
                doc = db.parse(is);  
  
            } catch (ParserConfigurationException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } catch (SAXException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } catch (IOException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } 
  
            return doc; 
    } 
  
    /** 鑾峰彇鑺傜偣鍊�
      * @param elem element 
      */
     public final String getElementValue( Node elem ) { 
         Node child; 
         if( elem != null){ 
             if (elem.hasChildNodes()){ 
                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){ 
                     if( child.getNodeType() == Node.TEXT_NODE  ){ 
                         return child.getNodeValue(); 
                     } 
                 } 
             } 
         } 
         return ""; 
     } 
  
     /** 
      * 鑾峰彇鑺傜偣鍊�
      * @param Element node 
      * @param key string 
      * */
     public String getValue(Element item, String str) { 
            NodeList n = item.getElementsByTagName(str); 
            return this.getElementValue(n.item(0)); 
        } 
}