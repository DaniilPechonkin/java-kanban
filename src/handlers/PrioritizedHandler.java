package handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if ("GET".equals(httpExchange.getRequestMethod())) {
            String response = taskManager.getPrioritizedTasks().toString();
            sendText(httpExchange, response, 200);
        } else {
            sendText(httpExchange, "Method Not Allowed", 405);
        }
    }
}