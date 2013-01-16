package com.ie;

import java.util.*;
import java.util.concurrent.*;

public class Crawler  {


	public static void main(String[] args) throws Exception
	{
		
		long startTime=System.currentTimeMillis();  
		ExecutorService pool = Executors.newCachedThreadPool();
		//Crawler counter = new Crawler(LinkDB.unVisitedUrlDeQueue());
		String url="http://www.yahoo.co.jp/";
		CrawlerTest counter =  new CrawlerTest(url, pool);
		LinkDB.addUnvisitedUrl(url);
		 pool.submit(counter);
		

	      //pool.shutdown();
	      int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
	      System.out.println("largest pool size=" + largestPoolSize);
		long endTime=System.currentTimeMillis(); //鑾峰彇缁撴潫鏃堕棿 
		System.out.println("绋嬪簭杩愯鏃堕棿锛�"+(endTime-startTime)+"ms");
		
		
	}
}
	
	 class CrawlerTest implements Callable<Integer>
{
	/* 浣跨敤绉嶅瓙 url 鍒濆鍖�URL 闃熷垪*/
	public CrawlerTest(String seeds,ExecutorService pool)
	{
		
			
			//LinkDB.addUnvisitedUrl(seeds);
			LinkDB.visitUrl=seeds;
			this.pool=pool;
			//this.url=seeds;
			
		
	
	
		
	}
	
	/* 鐖彇鏂规硶*/
	public Integer call() 
	{
		
		count=0;
		//ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
		LinkFilter filter = new LinkFilter(){
			//鎻愬彇浠�http://www.twt.edu.cn 寮�ご鐨勯摼鎺�
			public boolean accept(String url) {
				/*if(url.startsWith("http://www.yahoo.co.jp/"))
					return true;
				else
					return false;*/
				return true;
			}
		};
		//鍒濆鍖�URL 闃熷垪
		//initCrawlerWithSeeds(seeds);
		//寰幆鏉′欢锛氬緟鎶撳彇鐨勯摼鎺ヤ笉绌轰笖鎶撳彇鐨勭綉椤典笉澶氫簬 1000
		while(!LinkDB.unVisitedUrlsEmpty()&&LinkDB.getVisitedUrlNum()<=2000)
		{
			String visiturl = LinkDB.unVisitedUrlDeQueue();
			if(visiturl==null)
				continue;
			CrawlerTest counter = new CrawlerTest(visiturl,pool);
			//System.out.println(visiturl+"11111");
			//Future<Integer> result = pool.submit(counter);
			 pool.submit(counter);
			 count++;
            //results.add(result);
			//闃熷ご URL 鍑哄
			//String visitUrl=LinkDB.unVisitedUrlDeQueue();
            //String visitUrl=LinkDB.visitUrl;
            
		
			
			//FileDownLoader downLoader=new FileDownLoader();
			//涓嬭浇缃戦〉
			//downLoader.downloadFile(visitUrl);
			//璇�url 鏀惧叆鍒板凡璁块棶鐨�URL 涓�
			LinkDB.addVisitedUrl(visiturl);
			System.out.println(++n+visiturl);
			//鎻愬彇鍑轰笅杞界綉椤典腑鐨�URL
			
			Set<String> links=HtmlParserTool.extracLinks(visiturl,filter);
			//鏂扮殑鏈闂殑 URL 鍏ラ槦
			for(String link:links)
			{
					LinkDB.addUnvisitedUrl(link);
			}
		}
		
		
		
		  return count  ; 
	
	}
	
	

	//main 鏂规硶鍏ュ彛
	/*
	public static void main(String[]args)
	{	long startTime=System.currentTimeMillis();   //鑾峰彇寮�鏃堕棿  
		Crawler crawler = new Crawler(new String[]{"http://www.yahoo.co.jp/"});
		//crawler.crawling(new String[]{"http://www.yahoo.co.jp/"});
		long endTime=System.currentTimeMillis(); //鑾峰彇缁撴潫鏃堕棿 
		System.out.println("绋嬪簭杩愯鏃堕棿锛�"+(endTime-startTime)+"ms");  
		
		
	}
	*/
	
	private  ExecutorService pool;
	private int count;
	public static int n=0;
	//private String url;
}
	

	


