package com.alexkasko.rest.handlers.json;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.nio.charset.Charset;

import static org.apache.http.params.CoreProtocolPNames.HTTP_CONTENT_CHARSET;

/**
 * HTTP JSON client example
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class JsonClient {
    private static final Gson gson = new Gson();

    <T> T access(String url, Object input, Class<T> outputClass) {
        // may be streamed if needed
        String json = gson.toJson(input);
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(HTTP_CONTENT_CHARSET, "UTF-8");
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
        String resultJson = execute(client, post);
        System.out.println(resultJson);
        return gson.fromJson(resultJson, outputClass);
    }

    // should be streamed
    private String execute(HttpClient client, HttpPost post) {
        try {
            InputStream is = client.execute(post).getEntity().getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(is, baos);
            return new String(baos.toByteArray(), "UTF-8");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
