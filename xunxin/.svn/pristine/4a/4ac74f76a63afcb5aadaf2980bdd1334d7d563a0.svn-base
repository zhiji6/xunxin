package com.xunxin.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xunxin.config.lucene.Product;
import com.xunxin.config.lucene.ProductIterator;
import com.xunxin.config.lucene.Tools;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月16日 -- 下午1:19:56
 * @Version 1.0
 * @Description     lucenen 搜索引擎工具类
 */
public class LuceneEngineutil {
    

    public static final String INDEX_PATH = "/data01/lucene-db";
//    public static final String INDEX_PATH = "D:\\lucene\\lucene-db";
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    private static ServerAddress serverAddress = null;
    private static MongoCredential credentials  = null;
    private static List<ServerAddress> addressLists =new ArrayList<ServerAddress>();
    private static List<MongoCredential> credentialsLists = new ArrayList<MongoCredential>();
    
    private static final String     IP = "39.104.88.178";
    private static final int        PORT = 27017;
    
    private static final String     USER = "xunxin";
    private static final String     PWD = "xunxin#123";
    private static final String     DBNAME = "xunxin";
    
    
    /** 
     * 测试MongoDB连接 
     */  
    private static void mongoConn() {  
        try {  
            addressLists.add(serverAddress);
            // 用户名 数据库 密码  
            credentials = MongoCredential.createCredential(USER, DBNAME, PWD.toCharArray());  
            credentialsLists.add(credentials);
            //IP port  
            serverAddress = new ServerAddress(IP, PORT);  
            mongoClient = new MongoClient(serverAddress,credentialsLists);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        if(null != mongoClient){
            database = mongoClient.getDatabase("xunxin");
        }
    }  
    
    public static MongoClient getMongoClient() {
        if (null == mongoClient) {
            mongoConn();
        }
        return mongoClient;
    }

    /**
     * 获取database
     * 
     * @return
     */
    public static MongoDatabase getDatabase() {
        if (null == mongoClient) {
            mongoConn();
        }
        return database;
    }

    /**
     * 获取User Collection
     * 
     * @return
     */
    public static MongoCollection<org.bson.Document> getQACollection() {
        if (null == database) {
            database = getDatabase();
        }
        if (null != database) {
            return database.getCollection("questionVO");
        }
        return null;
    }
    
    public static IndexSearcher indexSearcher =null;
    public static AnalyzingInfixSuggester suggester =null;
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
            
            MongoClient mongoClient2 = getMongoClient();
            MongoDatabase database2 = mongoClient2.getDatabase("xunxin");
            database2.getCollection("questionVO");
            MongoCollection<org.bson.Document> collection = getQACollection();
            FindIterable<org.bson.Document> Documents = collection.find();
            for (org.bson.Document cursor : Documents) {
                System.out.println(cursor.toJson());
                Document document = new Document();
                document.add(new Field("id", cursor.getObjectId("_id").toString(), TextField.TYPE_STORED));
                document.add(new Field("name", cursor.getString("name"), TextField.TYPE_STORED));
                indexWriter.addDocument(document);
            }
            mongoClient2.close();    
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
            
            String[] fields = {"name"};
            // MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD};
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
                String id = document.get("id");
                String name = document.get("name");
                JsonObject j = new JsonObject(); 
                j.addProperty("id", id); 
                j.addProperty("name", name); 
                System.out.println(j.toString());
                result.add(j.toString());
            } 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    public static List<String> lookup(String name) throws IOException {
        List<String> list = new ArrayList<String>();
        HashSet<BytesRef> contexts = new HashSet<BytesRef>();
//      contexts.add(new BytesRef(region.getBytes("UTF8")));
        //先以contexts为过滤条件进行过滤，再以name为关键字进行筛选，根据weight值排序返回前2条
        //第3个布尔值即是否每个Term都要匹配，第4个参数表示是否需要关键字高亮
        List<LookupResult> results = suggester.lookup(name, contexts, 10, true, true);
        System.out.println("-- \"" + name +":");
        for (LookupResult result : results) {
            BytesRef bytesRef = result.payload;
            InputStream is = Tools.bytes2InputStream(bytesRef.bytes);
            Product product = (Product)Tools.deSerialize(is);
            list.add(product.getName());
        }
        return list;
    }

    public static void initialize() throws IOException {
        if(suggester==null) {
            RAMDirectory indexDir = new RAMDirectory();
            StandardAnalyzer analyzer = new StandardAnalyzer();
            suggester = new AnalyzingInfixSuggester(indexDir, analyzer);
            //创建Product测试数据 TODO：生产环节从数据库读取数据
            ArrayList<Product> products = new ArrayList<Product>();
            MongoClient mongoClient = getMongoClient();
            MongoDatabase database2 = mongoClient.getDatabase("xunxin");
            database2.getCollection("questionVO");
            MongoCollection<org.bson.Document> collection = getQACollection();
            FindIterable<org.bson.Document> Documents = collection.find();
            for (org.bson.Document cursor : Documents) {
                System.out.println(cursor.toJson());
                products.add(new Product(cursor.getObjectId("_id").toString(), cursor.getString("name")));
            }
            // 创建测试索引
            suggester.build(new ProductIterator(products.iterator()));
        }
        mongoClient.close();
    }
    
    
    
    

    public static void main(String args[]) throws IOException   {
//        creatIndex();
        init();
        search("你");
//        initialize();
//        // 开始搜索
//        System.out.println(lookup( "你"));
    }
}
