package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import tasks.Subtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SubtaskHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if ("GET".equals(method)) {
            String queryString = httpExchange.getRequestURI().getQuery();
            String response;

            if (queryString != null && queryString.contains("id=")) {
                String idValue = queryString.substring(queryString.indexOf("id="));

                try {
                    int id = Integer.parseInt(idValue);
                    response = taskManager.findSubtask(id).toString();
                    sendText(httpExchange, response, 200);
                } catch (NumberFormatException e) {
                    response = "Invalid Subtask ID format";
                    sendText(httpExchange, response, 400);
                } catch (NullPointerException e) {
                    sendNotFound(httpExchange);
                }
            } else {
                response = taskManager.getAllSubtasks().toString();
                sendText(httpExchange, response, 200);
            }
        } else if ("POST".equals(method)) {
            String queryString = httpExchange.getRequestURI().getQuery();

            if (queryString != null && queryString.contains("id=")) {
                String idValue = queryString.substring(queryString.indexOf("id="));

                try {
                    int id = Integer.parseInt(idValue);
                    taskManager.updateSubtask(taskManager.findSubtask(id));
                    sendText(httpExchange, "Subtask updated", 201);
                } catch (NumberFormatException e) {
                    String response = "Invalid Subtask ID format";
                    sendText(httpExchange, response, 400);
                } catch (NullPointerException e) {
                    sendNotFound(httpExchange);
                }
            } else {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
                try (BufferedReader br = new BufferedReader(isr)) {
                    String body = br.lines().collect(Collectors.joining("n"));
                    Gson gson = new Gson();
                    JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
                    String name = jsonBody.get("name").getAsString();
                    String description = jsonBody.get("description").getAsString();
                    String epicId = jsonBody.get("epicId").getAsString();
                    taskManager.addTask(new Subtask(name, description, Integer.parseInt(epicId)));
                    sendText(httpExchange, "Subtask created", 201);
                }
            }
        } else if ("DELETE".equals((method))) {
            String queryString = httpExchange.getRequestURI().getQuery();

            if (queryString != null && queryString.contains("id=")) {
                String idValue = queryString.substring(queryString.indexOf("id="));

                try {
                    int id = Integer.parseInt(idValue);
                    taskManager.removeSubtask(id);
                    sendText(httpExchange, "Subtask removed", 200);
                } catch (NumberFormatException e) {
                    String response = "Invalid Subtask ID format";
                    sendText(httpExchange, response, 400);
                } catch (NullPointerException e) {
                    sendNotFound(httpExchange);
                }
            }
        }
    }
}