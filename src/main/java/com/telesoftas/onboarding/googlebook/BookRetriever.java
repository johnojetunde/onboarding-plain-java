package com.telesoftas.onboarding.googlebook;

import com.telesoftas.onboarding.googlebook.exception.GoogleBookAPIException;
import com.telesoftas.onboarding.googlebook.handler.ResponseHandler;
import com.telesoftas.onboarding.googlebook.model.GoogleBook;
import lombok.NonNull;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class BookRetriever {

    private final String baseUrl;

    private final int maxPages;

    public BookRetriever(int maxPages) {
        this.maxPages = maxPages;
        this.baseUrl = "https://www.googleapis.com/books/v1/volumes";
    }

    public BookRetriever(int maxPages, String baseUrl) {
        this.maxPages = maxPages;
        this.baseUrl = baseUrl;
    }

    public final Set<GoogleBook> byTitle(@NonNull final String title, int pageSize) throws GoogleBookAPIException {
        Set<GoogleBook> books = new HashSet<>();


        try (CloseableHttpClient client = HttpClients.createDefault()) {

            int pageIndex = 0;
            while (pageIndex < maxPages) {
                books.addAll(
                    client.execute(
                        buildRequest(title, pageIndex, pageSize),
                        ResponseHandler::responseHandler)
                );

                pageIndex++;
            }

            return books;
        } catch (Exception e) {
            throw new GoogleBookAPIException(e.getMessage());
        }
    }

    private HttpGet buildRequest(String title, int startIndex, int pageSize) throws URISyntaxException {
        return new HttpGet(new URIBuilder(baseUrl)
            .addParameter("q", title)
            .addParameter("startIndex", String.valueOf(startIndex))
            .addParameter("maxResults", String.valueOf(pageSize))
            .build());
    }
}
