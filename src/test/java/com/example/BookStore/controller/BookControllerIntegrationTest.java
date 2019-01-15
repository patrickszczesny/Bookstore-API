package com.example.BookStore.controller;

import com.example.BookStore.model.Book;
import com.example.BookStore.model.BookDto;
import com.example.BookStore.model.BookWithDetails;
import com.example.BookStore.model.BookWithDetailsDto;
import com.example.BookStore.repository.BookRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8199);

//    @Value("${server.port}")
    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        bookRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldReturnExistingBook() throws Exception {
        // given
        Book first = bookRepository.save(new Book("title 1", "author 1"));
        Book second = bookRepository.save(new Book("title 2", "author 2"));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);

        // when
        ResponseEntity<BookDto> book = restTemplate.exchange(
                prepareUrl("/" + second.getId()),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BookDto.class
        );

        // then
        assertThat(book.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(book.getBody()).isEqualTo(new BookDto(second.getId(), "title 2", "author 2"));
    }

    @Test
    public void shouldReturnBookWithDetails() {
        // given
        Book first = bookRepository.save(new Book("title 1", "author 1"));
        Book second = bookRepository.save(new Book("title 2", "author 2"));

        stubFor(get(urlEqualTo("/api/plaintext"))
                .willReturn(aResponse().withBody("super ksiazka")));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);

        // when
        ResponseEntity<BookWithDetailsDto> book = restTemplate.exchange(
                prepareUrl("/" + second.getId() + "/details"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BookWithDetailsDto.class
        );

        // then
        assertThat(book.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(book.getBody().getAuthor()).isEqualTo("author 2");
        assertThat(book.getBody().getTitle()).isEqualTo("title 2");
        assertThat(book.getBody().getDescription()).isEqualTo("super ksiazka");
    }

    @Test
    public void shouldReturn404Status() throws Exception {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);

        // when
        ResponseEntity<Void> book = restTemplate.exchange(
                prepareUrl("/not-existing-id"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Void.class
        );

        // then
        assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnFakeText() {
        // when
        ResponseEntity<String> result = restTemplate.getForEntity(prepareUrl("/fake"), String.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("some text");
    }

    private String prepareUrl(String uri) {
        return "http://localhost:" + port + "/books" + uri;
    }
}