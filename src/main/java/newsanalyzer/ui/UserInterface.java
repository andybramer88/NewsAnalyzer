package newsanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import newsanalyzer.ctrl.Controller;
import newsanalyzer.ctrl.NewsAnalyzerException;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.beans.Article;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

public class UserInterface 
{
	private static final String APIKEY = "dfb6219a48b745668b0d4577a5057be8";
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("Business");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.business)
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL ist nicht korrekt");
		} catch (NewsAnalyzerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NoSuchElementException e) {
			System.out.println("Kein Element gefunden.");
		}

	}


	public void getDataFromCtrl2(){
		System.out.println("Entertainment");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.entertainment)
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL ist nicht korrekt");
		} catch (NewsAnalyzerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl3(){
		System.out.println("Health");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.health)
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL ist nicht korrekt");
		} catch (NewsAnalyzerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void getDataForCustomInput() {
		
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Business", this::getDataFromCtrl1);
		menu.insert("b", "Entertainment", this::getDataFromCtrl2);
		menu.insert("c", "Health", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Imput:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}

}
