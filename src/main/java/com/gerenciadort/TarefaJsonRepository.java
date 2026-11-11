package com.gerenciadort;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TarefaJsonRepository implements TarefaRepository {
    private static final String ARQUIVO_JSON = "tarefas.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void salvar(ArrayList<Tarefa> tarefas) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_JSON), tarefas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar as tarefas no JSON: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Tarefa> carregar() {
        File arquivo = new File(ARQUIVO_JSON);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(arquivo, new TypeReference<ArrayList<Tarefa>>() {});
        } catch (IOException e) {
            System.err.println("Erro ao carregar as tarefas do JSON. Iniciando lista vazia.");
            return new ArrayList<>();
        }
    }
}
