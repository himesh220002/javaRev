package banking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BankServer {

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Serve static files
            server.createContext("/", new StaticFileHandler());
            
            // API endpoints
            server.createContext("/api/createAccount", new ApiHandler("createAccount"));
            server.createContext("/api/login", new ApiHandler("login"));
            server.createContext("/api/balance", new ApiHandler("balance"));
            server.createContext("/api/deposit", new ApiHandler("deposit"));
            server.createContext("/api/withdraw", new ApiHandler("withdraw"));
            server.createContext("/api/transfer", new ApiHandler("transfer"));
            server.createContext("/api/transactions", new ApiHandler("transactions"));
            
            server.setExecutor(null);
            server.start();
            System.out.println("🚀 Web Server started! Open http://localhost:8080 in your browser.");
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            File file = new File("public" + path);
            if (file.exists() && !file.isDirectory()) {
                String contentType = "text/plain";
                if (path.endsWith(".html")) contentType = "text/html";
                else if (path.endsWith(".css")) contentType = "text/css";
                else if (path.endsWith(".js")) contentType = "application/javascript";
                
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } else {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }

    static class ApiHandler implements HttpHandler {
        private final String action;

        public ApiHandler(String action) {
            this.action = action;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
                return;
            }

            // Read request body
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = parseFormData(body);

            try {
                String jsonResponse = processAction(params);
                sendResponse(exchange, 200, jsonResponse);
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println("Error processing request: " + e.getMessage());
                sendResponse(exchange, 500, "{\"error\": \"Invalid input format: " + e.getMessage() + "\"}");
            }
        }

        private String processAction(Map<String, String> params) {
            switch (action) {
                case "createAccount" -> {
                    String name = params.get("name");
                    int pass = Integer.parseInt(params.get("pass"));
                    int acNo = BankManagement.createAccount(name, pass);
                    if (acNo != -1) {
                        return "{\"success\": true, \"acNo\": " + acNo + "}";
                    }
                    return "{\"success\": false, \"message\": \"Failed to create account\"}";
                }
                case "login" -> {
                    int acNo = Integer.parseInt(params.get("acNo"));
                    int pass = Integer.parseInt(params.get("pass"));
                    String name = BankManagement.getUserName(acNo, pass);
                    if (name != null) {
                        int balance = BankManagement.getBalance(acNo, pass);
                        return "{\"success\": true, \"name\": \"" + name + "\", \"balance\": " + balance + "}";
                    }
                    return "{\"success\": false, \"message\": \"Invalid credentials\"}";
                }
                case "balance" -> {
                    int acNo = Integer.parseInt(params.get("acNo"));
                    int pass = Integer.parseInt(params.get("pass"));
                    int balance = BankManagement.getBalance(acNo, pass);
                    if (balance != -1) {
                        return "{\"success\": true, \"balance\": " + balance + "}";
                    }
                    return "{\"success\": false, \"message\": \"Failed to get balance\"}";
                }
                case "deposit" -> {
                    int acNo = Integer.parseInt(params.get("acNo"));
                    int amount = Integer.parseInt(params.get("amount"));
                    int newBal = BankManagement.deposite(acNo, amount);
                    if (newBal != -1) {
                        return "{\"success\": true, \"balance\": " + newBal + "}";
                    }
                    return "{\"success\": false, \"message\": \"Failed to deposit\"}";
                }
                case "withdraw" -> {
                    int acNo = Integer.parseInt(params.get("acNo"));
                    int pass = Integer.parseInt(params.get("pass"));
                    int amount = Integer.parseInt(params.get("amount"));
                    int newBal = BankManagement.withdrawl(acNo, pass, amount);
                    if (newBal != -1) {
                        return "{\"success\": true, \"balance\": " + newBal + "}";
                    }
                    return "{\"success\": false, \"message\": \"Failed to withdraw\"}";
                }
                case "transfer" -> {
                    int senderAcNo = Integer.parseInt(params.get("senderAcNo"));
                    int pass = Integer.parseInt(params.get("pass"));
                    int recepientAcNo = Integer.parseInt(params.get("recepientAcNo"));
                    int amount = Integer.parseInt(params.get("amount"));
                    int newBal = BankManagement.transferMoney(senderAcNo, pass, recepientAcNo, amount);
                    if (newBal >= 0) {
                        return "{\"success\": true, \"balance\": " + newBal + "}";
                    }
                    return "{\"success\": false, \"message\": \"Failed to transfer\"}";
                }
                case "transactions" -> {
                    int acNo = Integer.parseInt(params.get("acNo"));
                    int pass = Integer.parseInt(params.get("pass"));
                    String name = BankManagement.getUserName(acNo, pass);
                    if (name != null) {
                        String transactionsJson = BankManagement.getRecentTransactions(acNo);
                        return "{\"success\": true, \"transactions\": " + transactionsJson + "}";
                    }
                    return "{\"success\": false, \"message\": \"Invalid credentials\"}";
                }
                default -> {
                    return "{\"success\": false, \"message\": \"Unknown action\"}";
                }
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private Map<String, String> parseFormData(String formData) {
            Map<String, String> map = new HashMap<>();
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    map.put(kv[0], java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
                }
            }
            return map;
        }
    }
}
