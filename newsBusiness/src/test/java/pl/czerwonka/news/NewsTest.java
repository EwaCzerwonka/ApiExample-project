package pl.czerwonka.news;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsTest {

  @Test
  void testIfFileIsWritten() throws IOException {
    String str = "any text";
    String fileName = "anyFile.txt";

    Path path = Paths.get(fileName);
    byte[] strToBytes = str.getBytes();

    Files.write(path, strToBytes);

    String read = Files.readAllLines(path).get(0);
    assertEquals(str, read);
  }

  @Test
  void testReadArticles() throws IOException {
    String expected = "Business Article:Desc:Ewa";

    NewsApiClient newsApiClientMock = Mockito.mock(NewsApiClient.class);
    TopHeadlinesRequest reqMock = mock(TopHeadlinesRequest.class);
    NewsApiClient.ArticlesResponseCallback collbackMock = mock(NewsApiClient.ArticlesResponseCallback.class);
    doNothing().when(newsApiClientMock).getTopHeadlines(reqMock, collbackMock);

    Article article = new Article();
    article.setAuthor("Ewa");
    article.setDescription("Desc");
    article.setTitle("Business Article");
    List<Article> articles = Arrays.asList(article);

    News news = new News(newsApiClientMock);
    news.setArticles(articles);

    String fileName = "testFile.txt";
    news.readArticles("business", "pl", fileName);

    Path path = Paths.get(fileName);
    String read = Files.readAllLines(path).get(0);

    assertEquals(expected, read);
  }

}