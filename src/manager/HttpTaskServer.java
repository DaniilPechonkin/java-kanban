package manager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    private static final int port = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new SubtaskHandler());
        httpServer.createContext("/epics", new EpicHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedTasksHandler());

        httpServer.start();
    }

    public static class BaseHttpHandler implements HttpHandler{
        protected void sendText(HttpExchange exchange, String text, int statusCode) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, text.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(text.getBytes(StandardCharsets.UTF_8));
            }
        }

        protected void sendNotFound(HttpExchange exchange) throws IOException {
            sendText(exchange, "Resource not found", 404);
        }

        protected void sendHasInteractions(HttpExchange exchange) throws IOException {
            sendText(exchange, "Task has collisions with existing tasks", 406);
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
        }
    }

    static class TaskHandler extends BaseHttpHandler {
        String response = "TaskHandler";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                if (httpExchange.getRequestMethod().equals("GET")) {
                    sendNotFound(httpExchange);
                } else if (httpExchange.getRequestMethod().equals("POST")) {
                    sendHasInteractions(httpExchange);
                }
            }
        }
    }

    static class SubtaskHandler extends BaseHttpHandler {
        String response = "SubtaskHandler";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                if (httpExchange.getRequestMethod().equals("GET")) {
                    sendNotFound(httpExchange);
                } else if (httpExchange.getRequestMethod().equals("POST")) {
                    sendHasInteractions(httpExchange);
                }
            }
        }
    }

    static class EpicHandler extends BaseHttpHandler {
        String response = "EpicHandler";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                if (httpExchange.getRequestMethod().equals("GET")) {
                    sendNotFound(httpExchange);
                } else if (httpExchange.getRequestMethod().equals("POST")) {
                    sendHasInteractions(httpExchange);
                }
            }
        }
    }

    static class HistoryHandler extends BaseHttpHandler {
        String response = "HistoryHandler";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class PrioritizedTasksHandler extends BaseHttpHandler {
        String response = "PrioritizedTasksHandler";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}