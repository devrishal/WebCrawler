package com.rishal.webcrawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * Starting point for the Crawler's functionality. Internally it creates crawler branches
 * that make an HTTP request and parse the response (the web page).
 * 
 */

public class WebCrawler {
	/**
	 * Declaring variables which will store the values of maximum pages needs to
	 * be explored which will restrict infinite loop, pages_explored will remove
	 * the duplicate pages to be explored,pages_to_visit which will be the guide
	 * for the next page to visit.
	 */
	private static int MAX_PAGES_TO_VISIT=0;
	private Set<String> pages_explored = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	static int count = 1;

	/**
	 * This method will creates crawler branches that make an HTTP request and
	 * parse the response.
	 * 
	 * @param url
	 *            - The starting point of the crawler
	 * 
	 */
	public void process(String url,int webpagesToExplore ) {
			MAX_PAGES_TO_VISIT = webpagesToExplore;
		while (this.pages_explored.size() < MAX_PAGES_TO_VISIT) {
			String currentUrl;
			WebCrawlerTraveller handle = new WebCrawlerTraveller();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pages_explored.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			handle.crawl(currentUrl); // Logic to handle the crawl for more
										// information Look at the crawl method
										// in
										// WebCrawlerTraveller

			this.pagesToVisit.addAll(handle.getLinks());
			count++;
		}
		System.out.println("\n**Done** Visited " + this.pages_explored.size()
				+ " web page(s)");
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We
	 * also do a check to make sure this method doesn't return a URL that has
	 * already been visited.
	 * 
	 * @return url
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pages_explored.contains(nextUrl));
		this.pages_explored.add(nextUrl);
		return nextUrl;
	}

}
