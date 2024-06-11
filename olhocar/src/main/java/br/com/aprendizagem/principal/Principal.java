package br.com.aprendizagem.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.aprendizagem.model.InfoVeiculo;
import br.com.aprendizagem.model.ModelosVeiculo;
import br.com.aprendizagem.model.Veiculo;
import br.com.aprendizagem.service.ConsumoApi;
import br.com.aprendizagem.service.ConverteDados;

public class Principal {
    Scanner in = new Scanner(System.in);
    private final String  URL = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    String endereco = null;
    String json = null;
    public void menu() {
        boolean loop = true;
        while(loop){
            var menu = """
                            Bem vindo(a)!!!
                            |--------------|
                        Consulte já, o preço de seu automóvel!
                            |--------------|
                        Vamos começar a filtragem
                            |--------------|
                        O que você deseja?
                        [1] - Carro
                        [2] - Moto
                        [3] - Caminhão

                        """;
                        System.out.println(menu);
                        System.out.print("--> ");

            try{
                    int escolha = in.nextInt();
                    in.nextLine();
                    
    ////////etapa1
                    endereco = escolhaDoTipo(endereco, escolha);

                    System.out.println("Qual a marca do veiculo você quer?");
                    System.out.println("Digite o codigo da marca desejada");
                    int marcaEscolhida = in.nextInt();
                    in.nextLine();

    ////////estapa2   
                    endereco = endereco+"/"+marcaEscolhida+"/modelos";
                    ModelosVeiculo listaModelo = escolhaDaMarca(endereco, marcaEscolhida);

                    System.out.println("\nDigite o nome do veiculo que você deseja");
                    String nomeVeiculo = in.nextLine();

    /////////etapa3
                    escolhaDoNome(nomeVeiculo, listaModelo);
                

                    System.out.println("Digite o código do veículo");
                    int codigoVeiculo = in.nextInt();
                    in.nextLine();
    ////////etapa4

                ultimaEtapa(codigoVeiculo);

                System.out.println("'enter' para fazer outra busca");
                System.out.println("'f' para fechar aplicação");

                String sairContinuar = in.nextLine();

                if(!sairContinuar.equals(null)){
                    loop=false;
                }

            }catch(Exception e){
                System.out.println("Tente novamente");
                in.nextLine();
            }
        }
    }


    public String escolhaDoTipo(String endereco, int escolha){
        boolean respValida = false;
        switch (escolha) {
            case 1:
                endereco = URL+"carros/marcas";
                respValida = true;
                break;
            case 2:
                endereco = URL+"motos/marcas";
                respValida=true;
               break;
            case 3:
                endereco = URL+"caminhoes/marcas";
                respValida=true;
                break;
            default:
                System.out.println("Escolha uma opção");
                break;
        }
        if(respValida){
            json = consumoApi.ObterDados(endereco);

            List<InfoVeiculo> listaDeInfoVeiculo = conversor.obterLista(json, InfoVeiculo.class);
            listaDeInfoVeiculo.stream().sorted(Comparator.comparing(InfoVeiculo::codigo)).forEach(System.out::println);
            return endereco;
        }
        return null;
    }

    public ModelosVeiculo escolhaDaMarca(String endereco, int marcaEscolhida){
        
                json = consumoApi.ObterDados(endereco);
                var listaModelo = conversor.obterDados(json, ModelosVeiculo.class);

                System.out.println("Modelos dos carros ");
                listaModelo.modelos().stream().sorted(Comparator.comparing(InfoVeiculo::codigo))
                .forEach(System.out::println);
                return listaModelo;
    }

    public void escolhaDoNome(String nomeVeiculo, ModelosVeiculo listaModelo){
        var listaDeInfoVeiculo = listaModelo.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase())).collect(Collectors.toList());
                
        System.out.println("Modelos Filtrados");
        listaDeInfoVeiculo.stream().forEach(System.out::println);
    }
    
    public void ultimaEtapa(int codigoVeiculo){
        List<Veiculo> listaVeiculo = new ArrayList<>();

        endereco = endereco +"/"+codigoVeiculo+"/anos";
        json = consumoApi.ObterDados(endereco);
        List<InfoVeiculo> listaAnos = conversor.obterLista(json, InfoVeiculo.class);
        



        for (int i = 0; i < listaAnos.size(); i++) {
            var enderecoAnos = endereco + "/" + listaAnos.get(i).codigo();
            json = consumoApi.ObterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados (json, Veiculo.class);
            listaVeiculo.add(veiculo);
            }

        System.out.println("\nVeiculos");
        listaVeiculo.forEach(System.out::println);
    }

}
