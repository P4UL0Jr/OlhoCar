package br.com.aprendizagem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModelosVeiculo(List<InfoVeiculo> modelos) {
    
}
