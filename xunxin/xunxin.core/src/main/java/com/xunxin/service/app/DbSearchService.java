package com.xunxin.service.app;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.xunxin.util.app.lucene.JdbcUtil;

/**
 * 基于Lucene5.5.4的数据库搜索demo
 * @author liuxianan
 */
public class DbSearchService {
	public static final String INDEX_PATH = "D:\\lucene\\lucene-db";
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/xunxin_database?useUnicode=true&characterEncoding=utf-8";
	public static final String USER = "root";
	public static final String PWD = "xunxin";
	public static IndexSearcher indexSearcher =null;
	public static void init() throws IOException {
		if(indexSearcher == null) {
			// 1、创建Directory
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
			// 2、创建IndexReader
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			// 3、根据IndexReader创建IndexSearch
			indexSearcher = new IndexSearcher(directoryReader);
		}
	}
	
	/**
	 * 见数据库文件生成为本地索引文件，创建索引
	 */
	public static void creatIndex()
	{
		IndexWriter indexWriter = null;
		try
		{
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
			//Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new IKAnalyzer(true);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			indexWriter.deleteAll();// 清除以前的index
			
			JdbcUtil jdbc = new JdbcUtil(JDBC_URL, USER, PWD);
			ResultSet rs = jdbc.query("select * from blog");
			while(rs.next())
			{
				Document document = new Document();
				document.add(new Field("id", rs.getString("id"), TextField.TYPE_STORED));
				document.add(new Field("title", rs.getString("title"), TextField.TYPE_STORED));
				document.add(new Field("content", rs.getString("content"), TextField.TYPE_STORED));
				document.add(new Field("tag", rs.getString("tags"), TextField.TYPE_STORED));
				document.add(new Field("url", rs.getString("url"), TextField.TYPE_STORED));
				indexWriter.addDocument(document);
			}
			jdbc.closeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(indexWriter != null) indexWriter.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		//TODO:do something
	}
	public void delete() {
		//TODO:do something
	}
	
	/**
	 * 搜索
	 */
	public static List<String> search(String keyWord)
	{
		List<String> result = new ArrayList<String>();

		try
		{
			// 4、创建搜索的Query
			// Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new IKAnalyzer(true); // 使用IK分词
			
			// 简单的查询，创建Query表示搜索域为content包含keyWord的文档
			//Query query = new QueryParser("content", analyzer).parse(keyWord);
			
			String[] fields = {"title", "content", "tag"};
			// MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
			// MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国" 
			Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
			
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs topDocs = indexSearcher.search(multiFieldQuery, 100); // 搜索前100条结果
			System.out.println("共找到匹配处：" + topDocs.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			System.out.println("共找到匹配文档数：" + scoreDocs.length);
			
			QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red\">", "</span>");
			Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
			highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
			for (ScoreDoc scoreDoc : scoreDocs)
			{
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document document = indexSearcher.doc(scoreDoc.doc);
				String content = document.get("content");
				//TokenStream tokenStream = new SimpleAnalyzer().tokenStream("content", new StringReader(content));
				//TokenSources.getTokenStream("content", tvFields, content, analyzer, 100);
				//TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(), scoreDoc.doc, "content", document, analyzer);
				//System.out.println(highlighter.getBestFragment(tokenStream, content));
				System.out.println("-----------------------------------------");
				System.out.println("文章标题："+document.get("title"));
				System.out.println("文章地址：" + document.get("url"));
				System.out.println("文章内容：");
				System.out.println(highlighter.getBestFragment(analyzer, "content", content));
				System.out.println("");
				result.add(new StringBuilder().append("-----------------------------------------<br/>").append("<a href=\"").append(document.get("url")).append("\">")
						.append(document.get("title")).append("</a><br/>")
						.append(highlighter.getBestFragment(analyzer, "content", content)).append("<br/>").toString());
				// 8、根据Document对象获取需要的值
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String args[]) throws IOException
	{
		DbSearchService demo = new DbSearchService();
//		demo.creatIndex();

		demo.init();
		demo.search("android");
	}
}