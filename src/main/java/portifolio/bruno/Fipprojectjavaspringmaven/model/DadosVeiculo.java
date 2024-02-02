package portifolio.bruno.Fipprojectjavaspringmaven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(Integer codigo, String nome) {
}
