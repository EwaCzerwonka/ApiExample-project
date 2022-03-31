package pl.czerwonka.news;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class News {
  List<Article> articles;
  NewsApiClient newsApiClient;

  public News(NewsApiClient newsApiClient) {
    this.newsApiClient = newsApiClient;
  }

  public List<Article> getArticles() {
    return articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  public void readArticles(String category, String country, String fileName) throws IOException {
    getArticales(category, country);
    if (CollectionUtils.isNotEmpty(articles)){
      writeToFile(fileName, articles);
    }
  }

  private List<Article> getArticales(String category, String country){

    newsApiClient.getTopHeadlines(
        new TopHeadlinesRequest.Builder()
            .category(category)
            .country(country)
            .build(),
        new NewsApiClient.ArticlesResponseCallback() {
          @Override
          public void onSuccess(ArticleResponse response) {
            articles = response.getArticles();
          }

          @Override
          public void onFailure(Throwable throwable) {
            System.out.println(throwable.getMessage());
          }
        }
    );
    return articles;
  }

  private void writeToFile(String fileName, List<Article> articles) throws IOException {
    Path path = Paths.get(fileName);
    if(Files.notExists(path)){
      Files.createFile(path);
    }

    for (Article article : articles) {
      String title = StringUtils.defaultString(article.getTitle());
      String description = StringUtils.defaultString(article.getDescription());
      String author = StringUtils.defaultString(article.getAuthor());

      String dataToWrite = title + ":" + description + ":" + author + "\n";

      byte[] strToWrite = dataToWrite.getBytes();
      Files.write(path, strToWrite, StandardOpenOption.APPEND);
    }
  }
}
