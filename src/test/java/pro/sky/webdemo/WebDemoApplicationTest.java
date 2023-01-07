package pro.sky.webdemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.webdemo.controller.BooksController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebDemoApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BooksController booksController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(booksController).isNotNull();
    }
    @Test
    public void testDefaultMessage() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                        .isEqualTo("WebApp is working!");
    }

    @Test
    public void testGetBooks() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/books", String.class))
                .isNotEmpty()
                .isNotNull();
    }
    // String.class - тип ответа, который хотим получить

    /*
    @Test
    public void testPostBooks() throws Exception {
        Book book = new Book();
        book.setName("ofijrofe");
        book.setAuthor("hhhhhhhh");
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/books", book, String.class))
                .isNotNull();
        //так создается книга, заноситься в бд и остается там если запущено приложение, по хорошему надо после этого удалять (ниже)
        //тут надо бы получить json и вытащить из него id и вставить ниже, но это неправильно, для этого используется Mock
        Assertions
                .assertThat(this.restTemplate.delete("http://localhost:" + port + "/books/" + "id", book, String.class));
    }
*/


}
