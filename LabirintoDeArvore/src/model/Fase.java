package model;

import java.util.ArrayList;
import java.util.List;

// Classe que representa uma fase do jogo
public class Fase {
    private ArvoreNA arvore; // Árvore N-ária representando o mapa da fase
    private NoNA noAtual; // Nó atual onde o jogador está localizado
    private int chavesNecessarias; // Número de chaves necessárias para completar a fase
    private int chavesColetadas; // Número de chaves coletadas pelo jogador
    private List<String> Historico; // Histórico textual das ações na fase
    private int numeroFase;

    // Construtor
    public Fase(ArvoreNA mapa, NoNA noAtual, int chavesNecessarias, int numeroFase) {
        this.arvore = mapa;
        this.noAtual = noAtual;
        this.chavesNecessarias = chavesNecessarias;
        this.chavesColetadas = 0;
        this.numeroFase = numeroFase;
        this.Historico = new ArrayList<>();
        registrarHistorico("Inicio da fase: jogador entrou na fase. Chaves necessárias: " + chavesNecessarias);
    }

    // Métodos de acesso
    public ArvoreNA getArvore() {
        return arvore;
    }

    public void setNoAtual(NoNA noAtual) {
        this.noAtual = noAtual;
    }

    public NoNA getNoAtual() {
        return noAtual;
    }

    public int getChavesNecessarias() {
        return chavesNecessarias;
    }

    public int getChavesColetadas() {
        return chavesColetadas;
    }

    public int getNumeroFase() {
        return numeroFase;
    }

    public void setNumeroFase(int numeroFase) {
        this.numeroFase = numeroFase;
    }

    public List<String> getHistorico() {
        return Historico;
    }

    // Atualizar no atual do jogador
    public void moverPara(NoNA destino) {
        if (destino == null) return;
        this.noAtual = destino;
        registrarHistorico("Movimento: jogador foi para a sala " + destino.getId());
    }

    // Registrar evento no histórico
    public void registrarHistorico(String evento) {
        Historico.add(evento);
    }

    // Adicionar chave coletada
    public void AdicionarChave() {
        chavesColetadas++;
        registrarHistorico("Chave coletada (total: " + chavesColetadas + "/" + chavesNecessarias + ")");
    }

    // Verificar se o jogador ja cumpriu a condição de chaves da fase.
    public boolean possuiChavesSuficientes() {
        return chavesColetadas >= chavesNecessarias;
    }

    // Retorna true quando o jogador está na sala final e possui chaves suficientes.
    public boolean faseConcluida() {
        if (noAtual.getTipo() != ConteudoNo.SAIDA) {
            return false;
        }
        return possuiChavesSuficientes();
    }
}
