package pl.czerwonka.news;

import com.kwabenaberko.newsapilib.NewsApiClient;

import java.io.IOException;

public class NewsApi {
  public static void main(String[] args) {
    NewsApiClient newsApiClient = new NewsApiClient("ee4096f92425483099e9ca7a162d59e2");
    News news = new News(newsApiClient);

    try {
      news.readArticles("business", "pl", "someFile.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
