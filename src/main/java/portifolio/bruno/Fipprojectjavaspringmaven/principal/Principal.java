package portifolio.bruno.Fipprojectjavaspringmaven.principal;

import portifolio.bruno.Fipprojectjavaspringmaven.model.Anos;
import portifolio.bruno.Fipprojectjavaspringmaven.model.DadosVeiculo;
import portifolio.bruno.Fipprojectjavaspringmaven.model.Modelos;
import portifolio.bruno.Fipprojectjavaspringmaven.model.Preco;
import portifolio.bruno.Fipprojectjavaspringmaven.service.ConsumoAPI;
import portifolio.bruno.Fipprojectjavaspringmaven.service.ConverteDados;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

        //detect the first input
        if (opcao.toLowerCase().contains("arr")){
            adress = URL_BASE + "/carros/marcas";
        } else if (opcao.toLowerCase().contains("oto")) {
            adress = URL_BASE + "/motos/marcas";
        } else if (opcao.toLowerCase().contains("minh")) {
            adress = URL_BASE + "/caminhoes/marcas";
        }

        //print the content from the API
        var json = consumo.pegaDados(adress);
        var marcas = converteDados.obterLista(json, DadosVeiculo.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        //Second Input
        System.out.println("Insira o código da marca para consulta:");
        var codigoMarca = scanner.nextLine();

        adress = adress + "/"+ codigoMarca + "/modelos";
        json = consumo.pegaDados(adress);
        var modeloLista = converteDados.obterDados(json, Modelos.class);

        System.out.println("Modelos");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        //Third input
        System.out.println("Digite o codigo do modelo desejado:");
        var modeloDesejado = scanner.nextInt();
        scanner.nextLine();

        adress = adress + "/"+ modeloDesejado + "/anos";
        json = consumo.pegaDados(adress);
        var carrosLista = converteDados.obterLista(json, Anos.class);

        //Get years code to search for price
        List<String> listCodigoAnos = new ArrayList<>();
        carrosLista.forEach(anos -> listCodigoAnos.add(anos.codigo()));
        Collections.reverse(listCodigoAnos);


        String enderecoBackup = adress;
        List<Preco> listaPreco = new ArrayList<>();
        for (int i = 0; i < listCodigoAnos.size(); i++){
            adress = enderecoBackup;
            adress = adress + "/" + listCodigoAnos.get(i);
            json = consumo.pegaDados(adress);

            //filter Valor and Marca
            var pegaPrecoLista = converteDados.obterDados(json, Preco.class);
            listaPreco.add(pegaPrecoLista);
        }

        listaPreco.forEach(System.out::println);

    }
}
