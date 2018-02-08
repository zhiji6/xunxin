package com.xunxin.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Title.用于解压缩包 
 * @Description.
 * @Copyright: Copyright (c) 2012-3-8 上午10:54:51
 * @Company: 北京中艺源创有限公司
 * @Author: wangzhongbao
 * @Version: 1.0
 */
public class UnZipper {
	private static final Logger log = LoggerFactory.getLogger(UnZipper.class); 
	
	/**
	 * 功能：解压缩zip格式的文件
	 * @param zipFileName zip压缩包绝对路径
	 * @param outDir	解压到的目录
	 * @param deleteSrc 解压完成后是否删除压缩包
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void unZipper(String zipFileName,String outDir,boolean deleteSrc) throws IOException{
		File zipSrc = new File(zipFileName);
		if (!zipSrc.exists()) {
			log.debug("压缩包不存在！");
			return;
		}
		/*功能：如果不指定解压目录则解压到当前目录*/
		if (StringUtils.isBlank(outDir)) {
			outDir = zipSrc.getParent()+File.separatorChar;
		}
		log.debug("准备开始解压缩，输出路径：{}",outDir);
		long begin = System.currentTimeMillis();
		ZipFile zipFile = new ZipFile(zipFileName);
		Enumeration e = zipFile.entries();
		while (e.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) e.nextElement();
		 	if (zipEntry.isDirectory()) {
				String name = zipEntry.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outDir + name);
				f.mkdirs();
			}else {
				File f = new File(outDir + zipEntry.getName());
				f.getParentFile().mkdirs();
				f.createNewFile();
				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(f);
				int length = 0;
				byte[] b = new byte[1024];
				while ((length=is.read(b,0,1024)) != -1) {
					fos.write(b, 0, length);
				}
				is.close();
				fos.close();
			}
		}
		if (zipFile != null) {
			zipFile.close();
		}
		if (deleteSrc) {
			zipSrc.delete();
		}
		long end = System.currentTimeMillis();
		log.debug("解压缩成功。耗时：{}ms。",end - begin);
	}
	
}
