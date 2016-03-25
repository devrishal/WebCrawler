package com.rishal.webcrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * This Class will found out the links present in the page by firing an HTTP request and will store the result in an list.
 */
public class WebCrawlerTraveller {
	// Declaring User Agent to get treated as normal web browser.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	static int count = 1;

	/**
	 * This performs all the work. It makes an HTTP request, checks the
	 * response, and then gathers up all the links on the page.
	 * 
	 * @param url
	 *            - The URL to visit
	 * @return whether or not the crawl was successful
	 */
	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;

			// 200 is the HTTP OK status code indicating that everything is
			// working fine.
			if (connection.response().statusCode() == 200) {
				System.out
						.println("\n**Visiting** Received web page at " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out
						.println("**Failure** It's not HTML");
				return false;
			}
			Elements linksOnPage = htmlDocument.select("a[href]");

			System.out.println("Links found : (" + linksOnPage.size() + ")");
			for (Element link : linksOnPage) {
				FileDownloader getter = new FileDownloader();
				if (!link.absUrl("href").contains("#")
						|| !this.links.contains(link.absUrl("href"))) {
					getter.storingInFile(link.absUrl("href"), count);
					this.links.add(link.absUrl("href"));
				}
				count++;
			}

			return true;
		} catch (IOException ioe) {
			// We were not successful in our HTTP request
			return false;
		}
	}

	public List<String> getLinks() {
		return this.links;
	}

}
