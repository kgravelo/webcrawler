import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {
	public Set<String> visitedURLs = new HashSet<String>();

	/* only search in main website and links */
	public void crawlWebsites(List<String> URLs, String keyword) {

		for (String inputURL : URLs) {
			try {
				URL url = new URL(inputURL);

				String host = url.getHost();
				Document doc = Jsoup.connect(inputURL).timeout(15000).get();

				/* search the whole website through links */
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String linkUrl = link.attr("abs:href");

					this.analyzeURL(host, linkUrl, keyword);
				}

			} catch (IOException e) {
				System.out.println(inputURL + " - (page 404) failed to retrieve data");
			}
		}
	}

	/* analyze text inside page */
	public void analyzeURL(String host, String url, String keyword) {
		url = url.strip();

		if (!this.visitedURLs.contains(url) && url.contains(host)) {
			System.out.print("Processing: " + url);

			try {
				if (!this.isURLAvailable(url)) {
					System.out.print(" - url not accessible");
				}

				Document doc = Jsoup.connect(url).timeout(15000).get();

				if (this.isTextFoundInPage(doc, keyword)) {
					System.out.println(" - keyword '" + keyword + "' found ");
				} else System.out.println(" - done");

			} catch (IOException e) {
				System.out.println(" - (page 404) failed to retrieve data");
			}

			this.visitedURLs.add(url);
		}

	}

	/* check the page if text is found inside */
	public boolean isTextFoundInPage(Document doc, String keyword) {
		if (doc.text().contains(keyword)) {
			return true;
		}
		return false;
	}

	public boolean isURLAvailable(String url) {
		try {
			new java.net.URL(url).openStream().close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
