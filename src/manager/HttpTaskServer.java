package manager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class HttpTaskServer {
    private static final int PORT = 8080;
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new SubtaskHandler());
        httpServer.createContext("/epics", new EpicHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedTasksHandler());

        httpServer.start();
    }

    public abstract static class BaseHttpHandler implements HttpHandler {
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
    }

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

    public class EpicHandler extends BaseHttpHandler {
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
                        response = taskManager.findEpic(id).toString();
                        sendText(httpExchange, response, 200);
                    } catch (NumberFormatException e) {
                        response = "Invalid Epic ID format";
                        sendText(httpExchange, response, 400);
                    } catch (NullPointerException e) {
                        sendNotFound(httpExchange);
                    }
                } else {
                    response = taskManager.getAllEpics().toString();
                    sendText(httpExchange, response, 200);
                }
            } else if ("POST".equals(method)) {
                String queryString = httpExchange.getRequestURI().getQuery();

                if (queryString != null && queryString.contains("id=")) {
                    String idValue = queryString.substring(queryString.indexOf("id="));

                    try {
                        int id = Integer.parseInt(idValue);
                        taskManager.updateEpic(taskManager.findEpic(id));
                        sendText(httpExchange, "Epic updated", 201);
                    } catch (NumberFormatException e) {
                        String response = "Invalid Epic ID format";
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
                        taskManager.addTask(new Epic(name, description));
                        sendText(httpExchange, "Epic created", 201);
                    }
                }
            } else if ("DELETE".equals((method))) {
                String queryString = httpExchange.getRequestURI().getQuery();

                if (queryString != null && queryString.contains("id=")) {
                    String idValue = queryString.substring(queryString.indexOf("id="));

                    try {
                        int id = Integer.parseInt(idValue);
                        taskManager.removeEpic(id);
                        sendText(httpExchange, "Epic removed", 200);
                    } catch (NumberFormatException e) {
                        String response = "Invalid Epic ID format";
                        sendText(httpExchange, response, 400);
                    } catch (NullPointerException e) {
                        sendNotFound(httpExchange);
                    }
                }
        }
    }

    public class HistoryHandler extends BaseHttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String response = historyManager.getHistory().toString();
                sendText(httpExchange, response, 200);
            } else {
                sendText(httpExchange, "Method Not Allowed", 405);
            }
        }
    }

    public class PrioritizedTasksHandler extends BaseHttpHandler {
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
}