package portifolio.bruno.Fipprojectjavaspringmaven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
//modelos is written on json, is the list key
public record Modelos(List<DadosVeiculo> modelos) {
}
