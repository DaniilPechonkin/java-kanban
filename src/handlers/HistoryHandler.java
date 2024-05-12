package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if ("GET".equals(httpExchange.getRequestMethod())) {
            Gson gson = new Gson();
            String response = gson.toJson(taskManager.getHistory());
            sendText(httpExchange, response, 200);
        } else {
            sendText(httpExchange, "Method Not Allowed", 405);
        }
    }
}