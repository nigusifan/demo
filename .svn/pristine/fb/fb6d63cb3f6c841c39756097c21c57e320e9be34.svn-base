package net.intelink.zmframework.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import net.intelink.zmframework.model.ImageModel;
import net.intelink.zmframework.model.ImageResult;
import net.intelink.zmframework.model.ImageUploadModel;
import net.intelink.zmframework.model.PrintUploadModel;

/**
 * 文件工具类
 * @author feng
 *
 */
public class FileUtil {
	
	
	/**
	 * 获取文件url   
	 * @param ids 文件ID  多个以“,”隔开
	 * @param urlType 类型  out
	 * @return 文件url数组JSON字符串
	 * @throws Exception
	 */
	public static String getFileUrl(String ids, String urlType) {
		String getFileUrl = SystemPropertiesUtil.getContextProperty("fastdfs.getFileUrl").trim();
	/*	String getFileUrl = MapUtils.getString(fastdfsParams, "fastdfs.getFileUrl", "").trim();// 服务器地址*/
		String url = getFileUrl + "?ids=" + ids + "&urlType=" + urlType;
//		String url = "http://113.98.248.185:8093/FileService/file/getFileUrl/1" + "?ids=" + ids + "&urlType=" + urlType;
		String res = HttpClientUtil.sendGetRequest(url, "utf-8");
		JSONObject json = JSONObject.parseObject(res);
		Boolean success = json.getBoolean("success");
		String data = json.getString("data");
		return success.booleanValue() ? data : null;
	}
	//图片上传
	public static List<ImageResult> fileUpload(ImageUploadModel imgModel) throws Exception {
		List<ImageResult> listImg = new ArrayList<>();
		
		
		String uploadUrl = SystemPropertiesUtil.getContextProperty("fastdfs.uploadFileUrl").trim();
		if(imgModel.getListImg() != null && imgModel.getListImg().size() > 0){
			for(ImageModel im:imgModel.getListImg()){
				
				HashMap<String, String> paramMap = new HashMap<>();
				paramMap.put("uploader", imgModel.getUserId() + "");
				paramMap.put("companyId", imgModel.getCompanyId() + "");
				paramMap.put("fileName", StringUtil.isNotEmpty(im.getFileName()) ? im.getFileName() : UUID.randomUUID().toString() + ".jpg");
				paramMap.put("fileType", "tms-010");
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(uploadUrl);
				MultipartEntity entity = new MultipartEntity();
				if (null != im.getImgs()) {
					entity.addPart("file", new ByteArrayBody(im.getImgs(), ""));
				}
				Set<Entry<String, String>> entrySet = paramMap.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String key = entry.getKey();
					String value = entry.getValue();
					entity.addPart(key, new StringBody(value, Charset.forName("utf-8")));
				}
				post.setEntity(entity);
				HttpResponse response = httpClient.execute(post);
				String res = "";
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = response.getEntity();
					res = EntityUtils.toString(httpEntity, "utf-8");
					JSONObject json = JSONObject.parseObject(res);
					Boolean success = json.getBoolean("success"); 
					String data = json.getString("data");
					if (success) {
						ImageResult ir = new ImageResult();
						ir.setId(Long.valueOf(data));
						String fileutlstr = getFileUrl(data, "out");
						if(StringUtil.isNotEmpty(fileutlstr)){
							List<String> list = JsonUtil.json2List(fileutlstr, String.class);
							String o = (null != list && list.size() > 0) ? list.get(0) : "";
							JSONObject json2 = JSONObject.parseObject(o);
							ir.setImgUrl(json2.getString(data));
						}
						listImg.add(ir);
					} 
				}
			}
		}
		return listImg;
	}
	//打印模版上传
	public static Long fileUpload(PrintUploadModel printModel) throws Exception {
		String uploadUrl = SystemPropertiesUtil.getContextProperty("fastdfs.uploadFileUrl").trim();
		if(printModel.getPrintDate() != null){
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("uploader", printModel.getUserId() + "");
			paramMap.put("companyId", printModel.getCompanyId() + "");
			paramMap.put("fileName", StringUtil.isNotEmpty(printModel.getFileName()) ? printModel.getFileName() : UUID.randomUUID().toString() + ".fr3");
			paramMap.put("fileType", "tms-011");
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(uploadUrl);
			MultipartEntity entity = new MultipartEntity();
			if (null != printModel.getPrintDate()) {
				entity.addPart("file", new ByteArrayBody(printModel.getPrintDate(), ""));
			}
			Set<Entry<String, String>> entrySet = paramMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String key = entry.getKey();
				String value = entry.getValue();
				entity.addPart(key, new StringBody(value, Charset.forName("utf-8")));
			}
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			String res = "";
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				res = EntityUtils.toString(httpEntity, "utf-8");
				JSONObject json = JSONObject.parseObject(res);
				Boolean success = json.getBoolean("success"); 
				String data = json.getString("data");
				if (success) {
					return Long.valueOf(data);
				} 
			}
		}
		return null;
	}
	
}
