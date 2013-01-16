package com.ie;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {
	// 鑾峰彇涓�釜缃戠珯涓婄殑閾炬帴,filter 鐢ㄦ潵杩囨护閾炬帴
	public static Set<String> extracLinks(String url,LinkFilter filter) {

		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			// 杩囨护 <frame >鏍囩鐨�filter锛岀敤鏉ユ彁鍙�frame 鏍囩閲岀殑 src 灞炴�鎵�〃绀虹殑閾炬帴
			@SuppressWarnings("serial")
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter 鏉ヨ缃繃婊�<a> 鏍囩锛屽拰 <frame> 鏍囩
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);
			// 寰楀埌鎵�湁缁忚繃杩囨护鐨勬爣绛�
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> 鏍囩
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
					if(filter.accept(linkUrl)&&!linkUrl.endsWith("pdf")&&!linkUrl.endsWith("doc")&&!linkUrl.endsWith("swf")&&!linkUrl.endsWith("jpg")&&!linkUrl.endsWith("xls"))
						links.add(linkUrl);
				} else// <frame> 鏍囩
				{
		        // 鎻愬彇 frame 閲�src 灞炴�鐨勯摼鎺ュ <frame src="test.html"/>
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1)
						end = frame.indexOf(">");
					String frameUrl = frame.substring(5, end - 1);
					if(filter.accept(frameUrl))
						links.add(frameUrl);
				}
			}
		} catch (ParserException e) {
			//e.printStackTrace();
		}
		return links;
	}
	//娴嬭瘯鐨�main 鏂规硶
	public static void main(String[]args)
	{
Set<String> links = HtmlParserTool.extracLinks(
"http://www.twt.edu.cn",new LinkFilter()
		{
			//鎻愬彇浠�http://www.twt.edu.cn 寮�ご鐨勯摼鎺�
			public boolean accept(String url) {
				if(url.startsWith("http://www.yahoo.co.jp/"))
					return true;
				else
					return false;
			}
			
		});
		for(String link : links)
			System.out.println(link);
	}
}
