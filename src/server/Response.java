package server;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private static String RESPONSE_SEPARATOR = "\r\n";
    private List<String> headers;
    private String body;

    public String make() {
        String formattedResponse = getFormattedHeaders();
        if (body != null) {
            // Response body should be isolated with one line from headers
            formattedResponse += RESPONSE_SEPARATOR + body;
        }
        return formattedResponse;
    }

    public Response() {
        headers = new ArrayList<>();
    }

    public Response appendHeaderAndReturnSelf(String header) {
        headers.add(header);
        return this;
    }

    public Response appendStatus(String status) {
        return appendHeaderAndReturnSelf("HTTP/1.1 " + status);
    }

    public Response appendConnection(String connection) {
        return appendHeaderAndReturnSelf("Connection: " + connection);
    }

    public Response appendContentType(String contentType) {
        return appendHeaderAndReturnSelf("Content-Type: " + contentType);
    }

    public Response appendCORSHeaders() {
        this.appendHeaderAndReturnSelf("Access-Control-Allow-Origin: *")
                .appendHeaderAndReturnSelf("Access-Control-Allow-Methods: GET, POST, OPTIONS")
                .appendHeaderAndReturnSelf("Access-Control-Allow-Headers: Content-Type, Authorization, Content-Length, X-Requested-With");
        return this;
    }

    public Response appendBodyAndReturnSelf(String body) {
        this.body = body;
        return this;
    }

    private String getFormattedHeaders() {
        String preparedHeaders = "";
        for (String singleHeaderTag : this.headers) {
            preparedHeaders += singleHeaderTag + RESPONSE_SEPARATOR;
        }
        return preparedHeaders;
    }

    @Override
    public String toString() {
        return "Response{" +
                "headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
