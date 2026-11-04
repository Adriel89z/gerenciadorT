package com.gerenciadort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    private static final String ARQUIVO_JSON = "tarefas.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        ArrayList<Tarefa> tarefas = carregarTarefas();
        Scanner scanner = new Scanner(System.in);

        int contadorID = tarefas.stream().mapToInt(Tarefa::getId).max().orElse(0) + 1;
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== GERENCIADOR DE TAREFAS ===");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Listar Tarefas");
            System.out.println("3. Concluir Tarefas");
            System.out.println("4. Remover Tarefas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma Opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {

                    case 1:
                        System.out.println("Digite o Nome da Tarefa: ");
                        String desc = scanner.nextLine();
                        tarefas.add(new Tarefa(contadorID++, desc));
                        salvarTarefas(tarefas);
                        System.out.println("Tarefa Adicionada com Sucesso!");
                        break;

                    case 2:
                        System.out.println("\n--- Suas Tarefas ---");
                        if (tarefas.isEmpty()) {
                            System.out.println("Nenhuma Tarefa Cadastrada.");
                        } else {
                            for (Tarefa tarefa : tarefas) {
                            String status = tarefa.isConcluida() ? "[X]" : "[ ]";
                                System.out.println(status + "ID: " + tarefa.getId() + " | Nome : " + tarefa.getNome());
                            }
                        }
                        break;
                        
                    case 3:
                        System.out.println("Digite o ID da tarefa que deseja Concluir: ");
                        if (scanner.hasNextInt()) {
                            int idConcluir = scanner.nextInt();
                            scanner.nextLine();
                            tarefas.stream().filter(t -> t.getId() == idConcluir).findFirst().ifPresentOrElse(t -> {
                                t.setConcluida(true);
                                salvarTarefas(tarefas);
                                System.out.println("Tarefa marcada como Concluída");
                            }, () -> System.out.println("Tarefa não encontrada."));
                        } else {
                            System.out.println("Erro: ID inválido.");
                            scanner.nextLine();
                        }
                        break;

                    case 4:
                        System.out.println("Digite o ID da tarefa que deseja Remover: ");
                        if (scanner.hasNextInt()){
                            int idRem = scanner.nextInt();
                            scanner.nextLine();

                            boolean removido = tarefas.removeIf(t -> t.getId() == idRem);
                            
                            if (removido) {
                                salvarTarefas(tarefas);
                                System.out.println("Tarefa '"+ removido + "' foi removida do arquivo!");
                            } else {
                                System.out.println("Tarefa não encontrada.");
                            }
                        } else {
                            System.out.println("Erro: ID inválido.");
                            scanner.nextLine();
                        }
                        break;

                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;

                    default:
                        System.out.println("Opção inválida: " + opcao + ". Tente novamente.");;
                }
            } else {
                System.out.println("Por favor, difite um número correspondente às opções do menu.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void salvarTarefas(ArrayList<Tarefa> tarefas) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_JSON), tarefas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a tarefas: " + e.getMessage());
        }
    }
    
    private static ArrayList<Tarefa> carregarTarefas() {
        File arquivo = new File(ARQUIVO_JSON);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(arquivo, new TypeReference<ArrayList<Tarefa>>() {});
        } catch (IOException e) {
            System.out.println("Erro ao carregar as tarefas. Iniciando lista vazia.");
            return new ArrayList<>();
        }
    }
}
