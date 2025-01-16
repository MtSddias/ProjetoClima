import java.util.Scanner;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;



public class ProjetoClima{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade= scanner.nextLine();

        try{
            String dadosClimaticos= getdadosClimaticos(cidade);
            if(dadosClimaticos.contains("\"code\":1006")){
                System.out.println("A localização inserida está incorreta!");
            }else{
                imprimirDadosClimaticos(dadosClimaticos);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }




    }
}