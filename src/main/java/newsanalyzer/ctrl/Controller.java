package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class Controller {

	public static final String APIKEY = "dfb6219a48b745668b0d4577a5057be8";

	public void process(NewsApi news) throws IOException, NewsAnalyzerException {
		System.out.println("Start process");

		NewsReponse newsResponse = news.getNews();
		if(newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();

			articles.stream().forEach(article -> System.out.println(article.toString()));
		}

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis

		System.out.println("End process");
	}

	public Object getData() {

		return null;
	}
}
