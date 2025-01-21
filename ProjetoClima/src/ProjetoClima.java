import java.nio.file.Files;
import java.util.Scanner;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static java.net.http.HttpClient.newHttpClient;


public class ProjetoClima {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            String dadosClimaticos = getdadosClimaticos(cidade);
            if (dadosClimaticos.contains("\"code\":1006")) {
                System.out.println("A localização inserida está incorreta!");
            } else {
                imprimirDadosClimaticos(dadosClimaticos);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static String getdadosClimaticos(String cidade) throws Exception {
        String apiKey = Files.readString(Paths.get("API.txt")).trim();
        System.out.println("api:" + apiKey);

        String formatarNomeCidade = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        String urlApi = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + formatarNomeCidade;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlApi))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("URL chamada: " + urlApi);
        System.out.println("Resposta da API: " + response.body());
        return response.body();


    }


    public static void imprimirDadosClimaticos(String dados) {


        JSONObject dadosJson = new JSONObject(dados);
        JSONObject informacoesMeteorologicas = dadosJson.getJSONObject("current");

        // dados de localização

        String cidade = dadosJson.getJSONObject("location").getString("name");
        String pais = dadosJson.getJSONObject("location").getString("country");

        // dados climáticos:

        String condicaoTempo = informacoesMeteorologicas.getJSONObject("condition").getString("text");
        int umidade = informacoesMeteorologicas.getInt("humidity");
        float velVento = informacoesMeteorologicas.getFloat("wind_kph");
        float pressAtm = informacoesMeteorologicas.getFloat("pressure_mb");
        float sensacaoTerm = informacoesMeteorologicas.getFloat("feelslike_c");
        float temperaturaAtual = informacoesMeteorologicas.getFloat("temp_c");

        String dataHora = informacoesMeteorologicas.getString("last_updated");
        System.out.println("Informações:" + cidade + pais);
        System.out.println("data e hora:" + dataHora);
        System.out.println("Temperatura atual:" + temperaturaAtual + "°C");
        System.out.println("Sensação térmica:" + sensacaoTerm + "°C");
        System.out.println("Condição do tempo:" + condicaoTempo);
        System.out.println("Umidade:" + umidade + "%");
        System.out.println("Velocidade do vento:" + velVento + " km/h");
        System.out.println("Pressão:" + pressAtm + "mb");

    }


}
