package portifolio.bruno.Fipprojectjavaspringmaven.principal;

import portifolio.bruno.Fipprojectjavaspringmaven.model.DadosVeiculo;
import portifolio.bruno.Fipprojectjavaspringmaven.model.Modelos;
import portifolio.bruno.Fipprojectjavaspringmaven.service.ConsumoAPI;
import portifolio.bruno.Fipprojectjavaspringmaven.service.ConverteDados;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    public void exibeMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
        Para visualizar o FIP primeiro digite qual veiculo deseja.
        
        Carro
        Moto
        Caminhão
        
        """);
        var opcao = scanner.nextLine();
        String adress = "";

        if (opcao.toLowerCase().contains("arr")){
            adress = URL_BASE + "/carros/marcas";
        } else if (opcao.toLowerCase().contains("oto")) {
            adress = URL_BASE + "/motos/marcas";
        } else if (opcao.toLowerCase().contains("minh")) {
            adress = URL_BASE + "/caminhoes/marcas";
        }

        var json = consumo.pegaDados(adress);
        System.out.println(json);
        var marcas = converteDados.obterLista(json, DadosVeiculo.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.println("Insira o código da marca para consulta:");
        var codigoMarca = scanner.nextLine();

        adress = adress + "/"+ codigoMarca + "/modelos";
        json = consumo.pegaDados(adress);
        var modeloLista = converteDados.obterDados(json, Modelos.class);

        System.out.println("Modelos");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);




//        List<DadosVeiculo> veiculo = ConsumoAPI.pegaMarca();
//        System.out.println("Digite o código da Marca de interesse");
//        ConsumoAPI.pegaModelo(veiculo);
//        System.out.println("Preços");



    }
}
