package com.ie;

import java.util.HashSet;
import java.util.Set;

/**
 * 鐢ㄦ潵淇濆瓨宸茬粡璁块棶杩�Url 鍜屽緟璁块棶鐨�Url 鐨勭被
 */
public class LinkDB {

	//宸茶闂殑 url 闆嗗悎
	private static Set<String> visitedUrl = new HashSet<String>();
	//寰呰闂殑 url 闆嗗悎
	private static Queue<String> unVisitedUrl = new Queue<String>();

	public static String visitUrl;
	public static Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	public static String unVisitedUrlDeQueue() {
		return unVisitedUrl.deQueue();
	}

	// 淇濊瘉姣忎釜 url 鍙璁块棶涓�
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("")
 && !visitedUrl.contains(url)
				&& !unVisitedUrl.contians(url))
			unVisitedUrl.enQueue(url);
	}

	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}

	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.empty();
	}
}