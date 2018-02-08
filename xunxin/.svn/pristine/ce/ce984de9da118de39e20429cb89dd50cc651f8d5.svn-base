package com.xunxin.config.lucene;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunxin.config.LuceneEngineutil;

public class SuggesterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String keyword = req.getParameter("keyword");
	    try
	    {
	    	// 设置返回字符编码  
	    	resp.setCharacterEncoding("UTF-8"); 
	    	resp.setContentType("text/html");

	    	LuceneEngineutil.initialize();
	    	List<String> result = LuceneEngineutil.lookup(keyword);
	        PrintWriter writer = resp.getWriter();
	        for(String html : result) {
		        writer.println(html);
	        }
	        writer.close();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}
