package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TaskHandler extends BaseHttpHandler {
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
                    response = taskManager.findTask(id).toString();
                    sendText(httpExchange, response, 200);
                } catch (NumberFormatException e) {
                    response = "Invalid task ID format";
                    sendText(httpExchange, response, 400);
                } catch (NullPointerException e) {
                    sendNotFound(httpExchange);
                }
            } else {
                response = taskManager.getAllTasks().toString();
                sendText(httpExchange, response, 200);
            }
        } else if ("POST".equals(method)) {
            String queryString = httpExchange.getRequestURI().getQuery();

            if (queryString != null && queryString.contains("id=")) {
                String idValue = queryString.substring(queryString.indexOf("id="));

                try {
                    int id = Integer.parseInt(idValue);
                    taskManager.updateTask(taskManager.findTask(id));
                    sendText(httpExchange, "Task updated", 201);
                } catch (NumberFormatException e) {
                    String response = "Invalid task ID format";
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
                    taskManager.addTask(new Task(name, description));
                    sendText(httpExchange, "Task created", 201);
                }
            }
        } else if ("DELETE".equals((method))) {
            String queryString = httpExchange.getRequestURI().getQuery();

            if (queryString != null && queryString.contains("id=")) {
                String idValue = queryString.substring(queryString.indexOf("id="));

                try {
                    int id = Integer.parseInt(idValue);
                    taskManager.removeTask(id);
                    sendText(httpExchange, "Task removed", 200);
                } catch (NumberFormatException e) {
                    String response = "Invalid Task ID format";
                    sendText(httpExchange, response, 400);
                } catch (NullPointerException e) {
                    sendNotFound(httpExchange);
                }
            }
        }
    }
}