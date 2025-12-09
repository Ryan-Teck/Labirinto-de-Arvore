package model;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

// Nó de uma árvore N-ária
public class NoNA {
    private int id; // Identificador único da sala
    private ConteudoNo tipo; // Tipo de conteúdo da sala
    private Inimigo inimigo; // Inimigo presente na sala, se houver
    private NoNA pai; // Nó pai
    private List<NoNA> filhos; // Lista de filhos do nó

    // Construtor padrão - criar uma No com ID aleatório e tipo VAZIO
    public NoNA() {
        this.id = UUID.randomUUID().hashCode();
        this.tipo = ConteudoNo.VAZIO;
        this.inimigo = null;
        this.pai = null;
        this.filhos = new ArrayList<>();
    }

    // Construtor que define ID, tipo e inimigo
    public NoNA(int id, ConteudoNo tipo, Inimigo inimigo) {
        this.id = id;
        this.tipo = tipo;
        this.inimigo = inimigo;
        this.filhos = new ArrayList<>();
        this.pai = null;
    }

    // Métodos de acesso
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConteudoNo getTipo() {
        return tipo;
    }

    public void setTipo(ConteudoNo tipo) {
        this.tipo = tipo;
    }

    public Inimigo getInimigo() {
        return inimigo;
    }

    public void setInimigo(Inimigo inimigo) {
        this.inimigo = inimigo;
    }

    public NoNA getPai() {
        return pai;
    }

    public void setPai(NoNA pai) {
        this.pai = pai;
    }

    public List<NoNA> getFilhos() {
        return filhos;
    }

    // Método auxiliares
    public void adicionarFilho(NoNA filho) {
        if (filho == null) {
            return;
        }
        filho.setPai(this);
        this.filhos.add(filho);
    }

    public void removerFilho(NoNA filho) {
        if (filho == null) {
            return;
        }
        this.filhos.remove(filho);
        filho.setPai(null);
    }

    // verifica se o nó possui um inimigo
    public boolean possuiInimigo() {
        return this.inimigo != null && this.tipo == ConteudoNo.INIMIGO;
    }

    // verifica se o nó possui um buff
    public boolean possuiBuff() {
        return tipo == ConteudoNo.BUFF_VIDA || tipo == ConteudoNo.BUFF_ATAQUE || tipo == ConteudoNo.BUFF_DEFESA;
    }

    // verifica se é folha
    public boolean isFolha() {
        return this.filhos.isEmpty();
    }
}
