import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression: ");
        String expression = scanner.next();
        scanner.close();

        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/calculator?expression=%s"
                .formatted(expression))).GET().build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        String result = send.body();
        System.out.println(result);
    }
}