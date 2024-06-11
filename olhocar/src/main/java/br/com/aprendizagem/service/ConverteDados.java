package br.com.aprendizagem.service;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import br.com.aprendizagem.interfaces.IConverteDados;


public class ConverteDados implements IConverteDados {

    
private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return objectMapper.readValue(json,classe);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = objectMapper.getTypeFactory()
        .constructCollectionType(List.class, classe);
            
            try {
                return objectMapper.readValue(json,lista);
            } catch (JsonMappingException e) {

                e.printStackTrace();
            } catch (JsonProcessingException e) {
                
                e.printStackTrace();
            }
        return null;
    }

    
    
}
