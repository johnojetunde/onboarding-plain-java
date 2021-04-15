package com.telesoftas.onboarding.googlebook.handler;

import com.telesoftas.onboarding.googlebook.mapper.HttpEntityMapper;
import com.telesoftas.onboarding.googlebook.model.GoogleBook;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.List;

@Log
@UtilityClass
public class ResponseHandler {

    public List<GoogleBook> responseHandler(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            return HttpEntityMapper.toBooks(response.getEntity().getContent());
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }

}
