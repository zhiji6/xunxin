package com.xunxin.util.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月12日 -- 下午3:14:00
 * @Version 1.0	
 * @Description	 APP端ID生成器
 */
public class IDGeneratorUtil {
	
	private static final Logger log = Logger.getLogger(IDGeneratorUtil.class);
	
	/**
     * 生成1-99999999的数字编码保存到硬盘
     */
    public static void generateAppCode2Disk(){
        int begin = 1;  
        int end = 99999999;  
        int count = begin + end;  
        //生成1到99999999的所有整数  
        int[] codes = new int[count + 1];  
        for (int i = begin; i <= end; i++){  
            codes[i] = i;  
        }
        //随机交换数据  
        int index = 0;
        int tempCode = 0;
        Random random = new Random();
        for (int i = begin; i <= end; i++){  
            index = random.nextInt(count+1);  
            tempCode = codes[index];  
            codes[index] = codes[i];  
            codes[i] = tempCode;  
        }
        //生成1000个文件,每个文件包含100000个appCode
        StringBuilder sb = new StringBuilder();
        int flag = 100000;
        System.out.println("***********开始**********");
        try {
            for(int i = begin; i <= end; i++){
                sb.append(codes[i]).append("\n");
                if(i == end || i%flag == 0){
                    File folder = new File("/data01/IDGenerate");
                    if(!folder.isDirectory()){
                        folder.mkdir();
                    }
                    if(i==end){
                        i = end +1;
                    }
                    File file = new File("/data01/IDGenerate/ID_"+(i/flag)+".txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedWriter bw=new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
                    bw.write(sb.toString());
                    bw.flush();
                    bw.close();
                    sb = new StringBuilder();
                    System.out.println("当前i值："+i+"第"+(i/flag)+"个文件生成成功！");
                }
            }
            System.out.println("***********结束**********");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
  //获取唯一8位ID
    public static String createAppCode(){
        BufferedWriter bw = null;
        BufferedReader br = null;
        FileReader fr = null;
        FileWriter fw = null;
        try {
            String dir = "/data01/IDGenerate";
            if(StringUtils.isBlank(dir)){
                throw new Exception("获取文件路径为空");
            }
            File rootFile = new File(dir);
            String[] fileNames = rootFile.list();
            if(fileNames == null || fileNames.length == 0){
                throw new Exception("路径不正确，或者ID已经分配完毕，请联系管理员");
            }
            //获取第一个文件
            fr=new FileReader(dir+"/"+fileNames[0]);//获取文件流
            br = new BufferedReader(fr); //将流整体读取。
            StringBuilder sb = new StringBuilder();
            String appCode = "";
            String temp;
            int count =1;
            while(!StringUtils.isBlank(temp=br.readLine())){
                if(count == 1){
                    count++;
                    appCode = temp;
                    continue;
                }
                else{
                    sb.append(temp).append("\n");
                }

            }
            br.close();
            fr.close();
            if(!StringUtils.isBlank(appCode)){
                //判断文件内容是否还有下一行
                if(sb.length()<=0){
                    File delFile = new File(dir+"/"+fileNames[0]);
                    if(delFile.exists()){
                        delFile.delete();//删掉
                    }
                }
                else{
                    //将剩余内容重写写回文件
                    fw = new FileWriter(dir+"/"+fileNames[0]);
                    bw=new BufferedWriter(fw);
                    bw.write(sb.toString());
                    bw.flush();
                    bw.close();
                    fw.close();
                }
                String prex = "00000000";
                appCode = prex.substring(0,prex.length()-appCode.length())+appCode;
                return appCode;
            }
            else{
                throw new Exception("文件中内容为空");
            }
        } catch (Exception e) {
            log.error("获取ID error:"+e.getMessage());
            return null;
        } finally{
            try {
                if(bw != null)bw.close();
                if(br != null)bw.close();
                if(fr != null)bw.close();
                if(fw != null)bw.close();
            } catch (IOException e) {
                log.error("关闭文件流文件异常："+e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
//		generateAppCode2Disk();
    	String appCode = createAppCode();
    	System.out.println(appCode);
	}
    
}
