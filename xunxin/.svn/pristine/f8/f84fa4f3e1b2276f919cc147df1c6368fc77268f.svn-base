package com.xunxin.util;
//package com.java.util;
//
//import java.io.*;
//import java.awt.*;
//import java.awt.image.*;
//import javax.imageio.ImageIO;
//
//import org.apache.commons.io.FileUtils;
//
//import com.sun.image.codec.jpeg.*;
///**
// * <p>Title: </p>
// * <p>Description: </p>
// * <p>Copyright: Copyright (c)2007-6-13</p>
// * <p>Company: fuen</p>
// * @author 杨振朋
// * @version 1.0
// */
//public class Thumbnail {
//   private String srcFile;
//   private String destFile;
//   private int width;
//   private int height;
//   private Image img;  
//
//   /**
//    * 构造函数
//    * @param fileName String
//    * @throws IOException
//    */
//   public Thumbnail(String fileName) throws IOException {
//     File _file = new File(fileName); //读入文件
//     this.srcFile = _file.getName();
//     this.destFile = "D:/upload/smail/2_s.jpg";//this.srcFile.substring(0, this.srcFile.lastIndexOf(".")) +"_s.jpg";
//     img = javax.imageio.ImageIO.read(_file); //构造Image对象
//     width = img.getWidth(null); //得到源图宽
//     height = img.getHeight(null); //得到源图长
//   }   /**
//    * 强制压缩/放大图片到固定的大小
//    * @param w int 新宽度
//    * @param h int 新高度
//    * @throws IOException
//    */
//   public void resize(int w, int h) throws IOException {
//     BufferedImage _image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
//     _image.getGraphics().drawImage(img, 0, 0, w, h, null); //绘制缩小后的图
//     FileOutputStream newimageout = new FileOutputStream(destFile); //输出到文件流
//     /*
//      * JPEGImageEncoder 将图像缓冲数据编码为 JPEG 数据流。该接口的用户应在 Raster
//      * 或 BufferedImage 中提供图像数据，在 JPEGEncodeParams 对象中设置必要的参数，
//      * 并成功地打开 OutputStream（编码 JPEG 流的目的流）。JPEGImageEncoder 接口可
//      * 将图像数据编码为互换的缩略 JPEG 数据流，该数据流将写入提供给编码器的 OutputStream 中。
//      注意：com.sun.image.codec.jpeg 包中的类并不属于核心 Java API。它们属于 Sun 发布的
//      JDK 和 JRE 产品的组成部分。虽然其它获得许可方可能选择发布这些类，但开发人员不能寄
//      希望于从非 Sun 实现的软件中得到它们。我们期望相同的功能最终可以在核心 API 或标准扩
//      展中得到。
//     */
//     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimageout);
//     encoder.encode(_image); //近JPEG编码
//     newimageout.close();
//   }   /**
//    * 按照固定的比例缩放图片
//    * @param t double 比例
//    * @throws IOException
//    */
//   public void resize(double t) throws IOException {
//     int w = (int) (width * t);
//     int h = (int) (height * t);
//     resize(w, h);
//   }   /**
//    * 以宽度为基准，等比例放缩图片
//    * @param w int 新宽度
//    * @throws IOException
//    */
//   public void resizeByWidth(int w) throws IOException {
//     int h = (int) (height * w / width);
//     resize(w, h);
//   }   /**
//    * 以高度为基准，等比例缩放图片
//    * @param h int 新高度
//    * @throws IOException
//    */
//   public void resizeByHeight(int h) throws IOException {
//     int w = (int) (width * h / height);
//     resize(w, h);
//   }   /**
//    * 按照最大高度限制，生成最大的等比例缩略图
//    * @param w int 最大宽度
//    * @param h int 最大高度
//    * @throws IOException
//    */
//   public void resizeFix(int w, int h) throws IOException {
//     if (width / height > w / h) {
//       resizeByWidth(w);
//     }
//     else {
//       resizeByHeight(h);
//     }
//   }   /**
//    * 设置目标文件名
//    * setDestFile
//    * @param fileName String 文件名字符串
//    */
//   public void setDestFile(String fileName) throws Exception {
//     if (!fileName.endsWith(".jpg")) {
//       throw new Exception("Dest File Must end with \".jpg\".");
//     }
//     destFile = fileName;
//   }   /**
//    * 获取目标文件名
//    * getDestFile
//    */
//   public String getDestFile() {
//     return destFile;
//   }   /**
//    * 获取图片原始宽度
//    * getSrcWidth
//    */
//   public int getSrcWidth() {
//     return width;
//   }
//   /**
//    * 获取图片原始高度
//    * getSrcHeight
//    */
//   public int getSrcHeight() {
//     return height;
//   }
// /* 
//  * 调用测试
//  */
//   public static void main(String[] args) throws Exception {
//      //Thumbnail ccc = new Thumbnail("D:/upload/2.jpg");
//      //ccc.resizeFix(68, 68);
//      reduceImg("D:\\upload\\2.jpg", "D:\\upload\\smail\\2_ss.jpg", 200, 200);
////      try 
////      { 
////	      String dir = "D:/upload/loading5.gif"; 
////	      File srcImg = new File(dir); 
////	      File destImg = new File("D:/upload/smail/loading5_s123s.gif");
////	      //getGifImage(srcImg,destImg,68,68,false);
////	      GifImage gif = GifDecoder.decode(srcImg); 
////	      GifImage gi = addTextWatermarkToGifImage(gif,"www.sourcesky.com"); 
////	      GifEncoder.encode(gi,destImg); 
////      } 
////      catch(Exception e) 
////      { 
////    	  e.printStackTrace(); 
////      }
//    }
//   public static void reduceImg(String imgsrc, String imgdist, int widthdist,int heightdist) {
//	   int imageWidth = 0;   
//	   int imageHeight = 0;
//	    try {   
//	        File srcfile = new File(imgsrc);   
//	        if (!srcfile.exists()) {
//	        	System.out.println(imgsrc+" 文件不存在！");
//	            return;   
//	        }
//	        String imgdistDir = "";
//	        if(imgdist.lastIndexOf(File.separator)!=-1)
//	        	imgdistDir=imgdist.substring(0, imgdist.lastIndexOf(File.separator));
//	        else
//				imgdistDir=imgdist.substring(0, imgdist.lastIndexOf("/"));
//	        File outputDir = new File(imgdistDir);
//	        if (!outputDir.exists())
//	            outputDir.mkdirs();
//	        Image src = ImageIO.read(srcfile);   
//	        imageWidth = src.getWidth(null);   
//	        imageHeight = src.getHeight(null);   
//	        if ( imageWidth >= imageHeight)   
//	        {   
//	        	heightdist = (int)Math.round((imageHeight * widthdist * 1.0 / imageWidth));   
//	        }   
//	        else    
//	        {   
//	            widthdist = (int)Math.round((imageWidth * heightdist * 1.0 / imageHeight));   
//	        }
//	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist,BufferedImage.TYPE_INT_RGB);   
//	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);//Image.SCALE_SMOOTH 选择一个使图象平滑度比缩放速度具有更高优先级的图像缩放算法。   
//	        FileOutputStream out = new FileOutputStream(imgdist);   
//	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
//	        encoder.encode(tag);   
//	        out.close();   
//	  
//	    } catch (IOException ex) {   
//	        ex.printStackTrace();   
//	    }   
//	}
//   public static void getGifImage(File srcImg, File destImg, int width,int height, boolean smooth) {
//	   try {
//	   GifImage gifImage = GifDecoder.decode(srcImg);// 创建一个GifImage对象.
//	   // 1.缩放重新更改大小.
//	   GifImage resizeIMG = GifTransformer.resize(gifImage, width, height,smooth);
//	   // 2.剪切图片演示.
//	   // Rectangle rect = new Rectangle(0,0,200,200);
//	   // GifImage cropIMG = GifTransformer.crop(gifImage, rect);
//	   // 3.按比例缩放
//	   //GifImage scaleIMG = GifTransformer.scale(gifImage, 20.0, 20.0,true);//参数需要double型
//	   // 4.其他的方法.还有很多,比如水平翻转，垂直翻转 等等.都是GifTransformer类里面的.
//	   GifEncoder.encode(resizeIMG, destImg);
//	   } catch (IOException e) {
//	   e.printStackTrace();
//	   }
//   }
//   public static GifImage addTextWatermarkToGifImage(GifImage gifImage, String watermarkText) { 
//       //create new TextPainter 
//       TextPainter textPainter = new TextPainter(new Font("Verdana", Font.BOLD, 12)); 
//       textPainter.setOutlinePaint(Color.WHITE); 
//       //render the specified text outlined 
//       BufferedImage renderedWatermarkText = textPainter.renderString(watermarkText, true); 
//       //create new Watermark 
//       Watermark watermark = new Watermark(renderedWatermarkText, Watermark.LAYOUT_MIDDLE_CENTER); 
//       //apply watermark to the specified gif image and return the result 
//       return watermark.apply(gifImage, true); 
//   } 
//}
