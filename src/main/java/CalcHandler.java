import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CalcHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> parameters = getParameters(query);

        String expr = parameters.get("expression");
        String message = "Result: %s".formatted(getCalc(expr));

        exchange.sendResponseHeaders(200, message.length());
        exchange.getResponseBody().write(message.getBytes());
        exchange.getResponseBody().close();
    }

    public Map<String, String> getParameters(String query) {
        Map<String, String> numbers = new HashMap<>();
        String[] split = query.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            numbers.put(split1[0], split1[1]);
        }
        return numbers;
    }

    public String getCalc(String expr){
        String result;
        String operator = getOperator(expr);
        String[] parts = expr.split(Pattern.quote(String.valueOf(operator)));

        if(Objects.equals(operator, "+"))
            result = String.valueOf(Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]));
        else if(Objects.equals(operator, "-"))
            result = String.valueOf(Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]));
        else if(Objects.equals(operator, "*"))
            result = String.valueOf(Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]));
        else if(Objects.equals(operator, "/")) {
            if(parts[1].equals("0"))
                return "Division by zero";
            result = String.valueOf(Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]));
        }
        else
            result = "Error.Incorrect input...";

        return result;
    }

    public String getOperator(String expr){
        expr = expr.replaceAll("\\s+", "");

            String operator = "";
            if (expr.contains("+")) {
                operator = "+";
            } else if (expr.contains("-")) {
                operator = "-";
            } else if (expr.contains("*")) {
                operator = "*";
            } else if (expr.contains("/")) {
                operator = "/";
            }

        return operator;
    }
}
