import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Enter websites below (comma separated):");
		Scanner inputURL = new Scanner(System.in);

		List<String> URLs = new ArrayList<String>();
		URLs.addAll(Arrays.asList(inputURL.nextLine().split(",")));

		System.out.println("Enter text to search:");
		Scanner inputKeyword = new Scanner(System.in);

		StringBuilder stringBuilder = new StringBuilder();
		/* exact word match and not case sensitive */
		stringBuilder.append(" ").append(inputKeyword.nextLine().toLowerCase()).append(" ");

		WebCrawler webCrawler = new WebCrawler();
		webCrawler.crawlWebsites(URLs, stringBuilder.toString());
	}
}
