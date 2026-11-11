package com.gerenciadort;

import java.util.ArrayList;

public interface TarefaRepository {
    ArrayList<Tarefa> carregar();
    void salvar(ArrayList<Tarefa> tarefas);
}
