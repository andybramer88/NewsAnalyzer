package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsreader.downloader.SequentialDownloader;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.security.cert.CollectionCertStoreParameters;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "dfb6219a48b745668b0d4577a5057be8";

	public void process(NewsApi news) throws IOException, NewsAnalyzerException {
		System.out.println("Start process");

		NewsReponse newsResponse = news.getNews();
		if(newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));

			System.out.println("Zahl der Artikel");
			System.out.println(getNumbersOfArticles(articles));

			System.out.println("Bester Provider");
			System.out.println(getBestProvider(newsResponse.getArticles()));

			System.out.println("Kürzester Autor:");
			System.out.println(getShortestName(newsResponse.getArticles()));

			System.out.println("Sortierung:");
			List<Article> sortedTitles = getTitlesSortedByLength(articles);

			/*
			System.out.println(getTitlesSortedByLength(newsResponse.getArticles()));
			Collections.reverse(sortedTitles);
			aus einem anderen Versuch
			 */
		}

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis

		System.out.println("End process");
	}

	private long getNumbersOfArticles(List<Article> data) {
		return (long) data.size();
	}

	public String getBestProvider(List<Article> data) {
		return data
				.stream()
				.collect(Collectors.groupingBy(article -> article.getSource().getName(),Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue()).orElseThrow(NoSuchElementException::new).getKey();
	}

	public String getShortestName(List<Article> data) throws NewsAnalyzerException {
		String authorShort;
		try {
			authorShort = data
					.stream()
					.min(Comparator.comparing(Article::getAuthor))
					.toString();
		} catch(NoSuchElementException exception) {
			throw new NewsAnalyzerException(exception.getMessage());
		}
		return authorShort;
	}

	public List<Article> getTitlesSortedByLength(List<Article> data){
	return data
					.stream()
					.sorted(Comparator.comparingInt(Article -> Article.getTitle().length()))
					.collect(Collectors.toList());
		}

	public Object getData() {

		return null;
	}

	public void downloadUrlToList(NewsApi news) throws IOException, NewsAnalyzerException {
		NewsReponse newsReponse = news.getNews();

		if(newsReponse != null){
			List<Article> articles = newsReponse.getArticles();

			var urls = articles
					.stream()
					.map(Article::getUrl)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			SequentialDownloader sequentialDownloader = new SequentialDownloader();
			sequentialDownloader.process(urls);
		}
	}
}
