package com.clearqa.test.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Http2Json {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JsonNode readJsonFromUrl(String search_url)
            throws IOException, JSONException {
        URL url = new URL(search_url);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
//        HttpTokenTransport.setAuthHeader(auth_token, uc);  # Pass in a authentication token if required
        uc.connect();
        InputStream is = uc.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json_node = mapper.readTree(jsonText);
            return json_node;
        } finally {
            is.close();
        }
    }
}
