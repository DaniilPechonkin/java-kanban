package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if ("GET".equals(httpExchange.getRequestMethod())) {
            Gson gson = new Gson();
            String response = gson.toJson(taskManager.getPrioritizedTasks());
            sendText(httpExchange, response, 200);
        } else {
            sendText(httpExchange, "Method Not Allowed", 405);
        }
    }
}