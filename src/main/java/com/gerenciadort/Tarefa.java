package com.gerenciadort;

public class Tarefa {
    private int id;
    private String nome;
    private boolean concluida;

    public Tarefa(){

    }

    public Tarefa(int id, String desc) {
        this.id = id;
        this.nome = desc;
        this.concluida = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida){
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return "[" + (concluida ? "X" : " ") + "] " + id + " - " + nome;
    }

    

}
