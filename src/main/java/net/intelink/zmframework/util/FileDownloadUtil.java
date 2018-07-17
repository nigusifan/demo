package net.intelink.zmframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtil {

	public static void download(HttpServletResponse response,List<Object> fileUrls,String downloadFilename) {
		try {
			System.out.println("开始打包");
			if(StringUtil.isNotEmpty(downloadFilename)){
				downloadFilename += ".zip";// 文件的名称
			}else{
				downloadFilename = "image.zip";// 文件的名称
			}
			downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
			response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
			response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			long start = System.currentTimeMillis();
			System.out.println("开始下载："+start);
			for (int i = 0; i < fileUrls.size(); i++) {
				String fileurl = fileUrls.get(i).toString();
				fileurl = fileurl.replace("183.62.96.187:8871", "192.168.2.210:80");
				
				URL url = new URL(fileurl);
				String fileName = fileurl.substring(fileurl.lastIndexOf("/")+1);
				
				zos.putNextEntry(new ZipEntry(fileName));
				// FileInputStream fis = new FileInputStream(new
				// File(files[i]));
				InputStream fis = url.openConnection().getInputStream();
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
				fis.close();
			}
			zos.flush();
			zos.close();
			long end = System.currentTimeMillis();
			System.out.println("完成打包："+end);
			System.out.println("下载打包耗时："+(end-start));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
