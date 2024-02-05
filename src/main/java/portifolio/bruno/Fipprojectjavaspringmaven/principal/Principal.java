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

        //First input (type)
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

        //Second Input (branch)
        System.out.println("Digite código da Marca desejada");
        var codigoMarca = scanner.nextLine();

        adress = adress + "/"+ codigoMarca + "/modelos";
        json = consumo.pegaDados(adress);
        var modeloLista = converteDados.obterDados(json, Modelos.class);

        System.out.println("Modelos");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        //initialize option only
        int option = 1;
        //(1) Process do show the model prices (2) filter the list of models
        while(option != 0) {
            //Third Input and FilterInput (model)
            System.out.println("""
            
            -(1)Insira o código
            -(2)Filtre Modelo por nome
            
            Insira numero relacionado a opção desejada:
            """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    //Third input
                    System.out.println("Insira o código:");
                    var modeloDesejado = scanner.nextInt();
                    scanner.nextLine();

                    adress = adress + "/"+ modeloDesejado + "/anos";
                    json = consumo.pegaDados(adress);
                    var carrosLista = converteDados.obterLista(json, Anos.class);

                    //Get years code to search for price
                    List<String> listCodigoAnos = new ArrayList<>();
                    carrosLista.forEach(anos -> listCodigoAnos.add(anos.codigo()));
                    Collections.reverse(listCodigoAnos);

                    List<Preco> listaPreco = new ArrayList<>();
                    for (int i = 0; i < listCodigoAnos.size(); i++){
                        String adressAnos = adress + "/" + listCodigoAnos.get(i);
                        json = consumo.pegaDados(adressAnos);

                        //filter Valor, Modelo, AnoLancamento
                        var pegaPrecoLista = converteDados.obterDados(json, Preco.class);
                        listaPreco.add(pegaPrecoLista);
                    }
                    listaPreco.forEach(element -> System.out.println("Preço: " + element.Valor()+ "| "+element.Modelo()+"| "+element.Combustivel()+"|" +element.AnoModelo()));

                    option = 0;
                    break;
                case 2:
                    System.out.println("Insira nome de busca:");
                    var buscar = scanner.nextLine();

                    //filter the list using both toLowerCase
                    List<DadosVeiculo> modelosFiltrado = modeloLista.modelos().stream()
                            .filter(e -> e.nome().toLowerCase().contains(buscar.toLowerCase()))
                            .collect(Collectors.toList());

                    modelosFiltrado.forEach(System.out::println);
                    break;
                default:
                    break;
            }
        }
    }
}
