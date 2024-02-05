package portifolio.bruno.Fipprojectjavaspringmaven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Preco(String Valor, String Modelo, Integer AnoModelo, String Combustivel) {
}
