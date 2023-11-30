package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        ArrayList<Integer> temperatures = new ArrayList<>();
        ArrayList<Integer> humidities = new ArrayList<>();
        HashMap<String, Integer> colors = new HashMap<>();

        //EJEMPLOS DE COMO ACCEDER A LA API

        //Enviar una petición POST
        //postAPI("192.168.7.21", "1880", "color", "pink");

        //Realizar una petición GET y mostrarla por pantalla
       //System.out.println(getAPI("192.168.7.21", "1880", "temperature"));
        //System.out.println(getAPI("192.168.7.21", "1880", "humidity"));


        LocalTime myObj = LocalTime.now();
        System.out.println(myObj);


        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n********************** Welcome to Arduino Menu *****************************");
            System.out.println("\n\t1. Ver Color.\t\t\t\t\t7. Humedad máxima.");
            System.out.println("\n\t2. Ver Humedad.\t\t\t\t\t8. Cambiar color.");
            System.out.println("\n\t3. Ver Temperatura.\t\t\t\t9. Color más repetido.");
            System.out.println("\n\t4. Temperatura mínima.\t\t\t10. Hora Temperatura máxima.");
            System.out.println("\n\t5. Humedad mínima.\t\t\t\t11. Hora Humedad máxima.");
            System.out.println("\n\t6. Temperatura máxima.\t\t\t0. Exit.");
            System.out.println("\n**************************************************************************");


            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    System.out.println(getAPI("192.168.7.21", "1880", "color"));
                    String colores=getAPI("192.168.7.21", "1880", "color");
                    if (colores != null) {
                        colors.put(colores,1);


                    }else{colors.put(colores,colors.get(colores)+1);

                    }

                } else if (choice == 2) {
                    System.out.println(getAPI("192.168.7.21", "1880", "humidity"));
                    int humedad=Integer.parseInt(getAPI("192.168.7.21", "1880", "humidity"));
                    humidities.add(humedad);

                } else if (choice == 3) {
                    System.out.println(getAPI("192.168.7.21", "1880", "temperature"));
                    int temperatura=Integer.parseInt(getAPI("192.168.7.21", "1880", "temperature"));
                    temperatures.add(temperatura);

                } else if (choice == 4) {
                    int valor=temperatures.get(0);

                    for (int temperaturaMinima: temperatures) {


                        if (temperaturaMinima<valor){
                           valor=temperaturaMinima;
                        }

                    }
                    System.out.println(valor);

                } else if (choice == 5) {
                    int valor=humidities.get(0);

                    for (int humedadMinima: humidities) {


                        if (humedadMinima<valor){
                            valor=humedadMinima;
                        }

                    }
                    System.out.println(valor);
                } else if (choice == 6) {
                    int valor=temperatures.get(0);

                    for (int temperaturaMaxima: temperatures) {


                        if (temperaturaMaxima>valor){
                            valor=temperaturaMaxima;
                        myObj=LocalTime.now();
                        }
                    }
                    System.out.println(valor);
                } else if (choice == 7) {
                    int valor=humidities.get(0);

                    for (int humedadMaxima: humidities) {


                        if (humedadMaxima>valor){
                            valor=humedadMaxima;
                            myObj=LocalTime.now();
                        }

                    }
                    System.out.println(valor);
                } else if (choice == 8) {
                    System.out.println("Dime un color");
                    String color=scanner.next();
                    postAPI("192.168.7.21", "1880", "color", color);

                } else if (choice == 9) {

                    for (Map.Entry<String, Integer> set:
                            colors.entrySet()) {


                        System.out.println(set.getKey() + " = "
                                + set.getValue());
                    }


                } else if (choice == 10) {
                    System.out.println(myObj);


                } else if (choice == 11) {
                    System.out.println(myObj);


                }else if(choice==0) {
                    System.out.println("Bye!!");
                    System.exit(0);;
                } else {
                    System.err.println("[ERROR] Your option is incorrect!! Try again!!");
                }

            } catch (
                    InputMismatchException e) {
                System.err.println("[ERROR] You must type a number!!!");
                scanner.next();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (true);


    }

    public static String getAPI(String ip, String port, String path) throws URISyntaxException, IOException, InterruptedException {
        URI targetURI = new URI("http://" + ip + ":" + port + "/" + path);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(targetURI).GET().build();
        //HttpClient httpClient = HttpClient.newHttpClient();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public static String postAPI(String ip, String port, String path, String message) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + ip + ":" + port +"/" + path))
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /*
        Escribir en un archivo:

        FileWriter file = new FileWriter("./parameters.txt");
        PrintWriter pw = new PrintWriter(file);
        pw.println("Hola");
        pw.close();
     */

    /*
        Leer de un archivo:

       BufferedReader br = new BufferedReader(new FileReader ("./parameters.txt"));
       String line = "";
       while(!(line = br.readLine()) != null){
            System.out.println(line);
       }
     */

}