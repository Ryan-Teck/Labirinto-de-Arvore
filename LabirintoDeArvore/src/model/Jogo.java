package model;

// Classe que representa o jogo em si
public class Jogo {
    private Jogador jogador;
    private Fase faseAtual;
    private int numeroFase; // Varia de 1 a 4
    // Snapshots dos atributos no início de cada fase
    private int vidaInicial;
    private int ataqueInicial;
    private int defesaInicial;

    // Construtor do jogo
    public Jogo() {
        this.jogador = new Jogador();
        this.numeroFase = 1;
        new GeradorArvore();
        iniciarFaseAtual();
    }

    // Inicia a fase atual utilizando o gerador de fases
    private void iniciarFaseAtual() {
        this.faseAtual = GeradorArvore.gerarFase(numeroFase);

        // define no atual do jogador como a raiz da árvore
        if (faseAtual.getArvore() != null && faseAtual.getArvore().getRaiz() != null) {
            faseAtual.setNoAtual(faseAtual.getArvore().getRaiz());
        }

        // armazena os atributos iniciais do jogador para reinício da fase
        this.vidaInicial = jogador.getVida();
        this.ataqueInicial = jogador.getAtaque();
        this.defesaInicial = jogador.getDefesa();
    }

    //Reiniciar a fase atual
    public void reiniciarFase() {
        // restaura os atributos iniciais do jogador
        jogador.setVida(vidaInicial);
        jogador.setAtaque(ataqueInicial);
        jogador.setDefesa(defesaInicial);

        // reseta chaves do jogador
        jogador.resetarChaves();

        // regenera a fase atual
        iniciarFaseAtual();
    }

    // Avança para a próxima fase
    public boolean avancarFase() {
        if (numeroFase >= 4) {
            return false; // Já está na última fase
        }
        numeroFase++;
        iniciarFaseAtual();
        return true;
    }

    // Retormna o jogador
    public Jogador getJogador() {
        return jogador;
    }

    // Retorna a fase atual
    public Fase getFaseAtual() {
        return faseAtual;
    }

    // Retorna o número da fase atual
    public int getNumeroFase() {
        return numeroFase;
    }

}
