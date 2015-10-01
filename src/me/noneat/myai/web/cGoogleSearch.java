package me.noneat.myai.web;

import me.noneat.myai.cAISettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by Noneatme on 19.08.2015.
 */
public class cGoogleSearch
{
	private String sGoogle      = "http://www.google.com/search?q=";
	private String sTerm        = "";
	private String sCharset     = "UTF-8";
	private String sUserAgent   = "myAI " + cAISettings.VERSION + " (+https://github.com/Noneatme/myai)";

	private Elements uLinks;

	public cGoogleSearch(String term)
	{
		this.sTerm = term;
		try
		{
			this.uLinks = Jsoup.connect(this.sGoogle + URLEncoder.encode(this.sTerm, this.sCharset)).userAgent(this.sUserAgent).get().select("li.g>h3>a");
			int iCount = 0;

			for (Element link : this.uLinks)
			{
				iCount++;

				String title        = link.text();
				String url          = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				url                 = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

				if (!url.startsWith("http"))
				{
					continue; // Ads/news/etc.
				}

				System.out.println("Title: " + title);
				System.out.println("URL: " + url);

				if(iCount > 5)
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
