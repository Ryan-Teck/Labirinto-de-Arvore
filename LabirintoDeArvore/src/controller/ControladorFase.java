package controller;

import model.*;
import view.Dialogos;

// Controlador da fase atual do jogo
public class ControladorFase {
    private Jogo jogo;
    private ControladorJogo controladorJogo;

    public ControladorFase(Jogo jogo, ControladorJogo controladorJogo) {
        this.jogo = jogo;
        this.controladorJogo = controladorJogo;
    }

    // Mover o jogador para uma sala(nó) filha escolhida
    public void moverJogadorPara(int idFilho) {
        Fase faseAtual = jogo.getFaseAtual();
        NoNA noAtual = faseAtual.getNoAtual();

        if (idFilho >= 0 && idFilho < noAtual.getFilhos().size()) {
            NoNA destino = noAtual.getFilhos().get(idFilho);
            faseAtual.moverPara(destino);
            analisarSalaAtual(destino);
        }
    }

    // Mover para o nó pai
    public void moverJogadorParaPai() {
        Fase faseAtual = jogo.getFaseAtual();
        NoNA noAtual = faseAtual.getNoAtual();
        if (noAtual.getPai() != null) {
            faseAtual.moverPara(noAtual.getPai());
            analisarSalaAtual(noAtual.getPai());
        }
    }   

    // Analize da Sala Atual
    public void analisarSalaAtual(NoNA salaAtual) {
        Jogador jogador = jogo.getJogador();
        Fase faseAtual = jogo.getFaseAtual();

        switch (salaAtual.getTipo()) {
            case BUFF_VIDA:
                jogador.aplicarBuffVida();
                salaAtual.setTipo(ConteudoNo.VAZIO);
                Dialogos.buffVida();
                break;
            case BUFF_DEFESA:
                jogador.aplicarBuffDefesa();
                salaAtual.setTipo(ConteudoNo.VAZIO);
                Dialogos.buffDefesa();
                break;
            case BUFF_ATAQUE:
                jogador.aplicarBuffAtaque();
                salaAtual.setTipo(ConteudoNo.VAZIO);
                Dialogos.buffAtaque();;
                break;
            case CHAVE:
                jogador.coletarChave();
                faseAtual.registrarHistorico("Chave coletada: jogador agora tem " + jogador.getChaves() + " chaves.");
                salaAtual.setTipo(ConteudoNo.VAZIO);
                Dialogos.chaveColetada();
                break;
            case INIMIGO:
                Dialogos.combateIniciado(salaAtual.getInimigo().getNome());
                iniciarCombate(salaAtual);
                break;
            case SAIDA:
                if (faseAtual.faseConcluida()) {
                    Dialogos.faseConcluida();
                    controladorJogo.proximaFase();
                } else {
                    Dialogos.saidaSemChaves(faseAtual.getChavesNecessarias() - faseAtual.getChavesColetadas());
                }
                break;
            default:
                // Sala vazia
                break;
        }
    }

        // Verificar se o jogador morreu
    private void iniciarCombate(NoNA salaAtual) {
        Jogador jogador = jogo.getJogador();
        Inimigo inimigo = salaAtual.getInimigo();
        Fase faseAtual = jogo.getFaseAtual();

        while (jogador.getVida() > 0 && inimigo.getVida() > 0) {
            
            // Jogador ataca primeiro
            int danoParaInimigo = Math.max(0, jogador.getAtaque() - inimigo.getDefesa());
            inimigo.setVida(danoParaInimigo);
            Dialogos.mostrarDanoInimigo(danoParaInimigo, inimigo.getVida());

            faseAtual.registrarHistorico("Combate: jogador causou " + danoParaInimigo + " ao inimigo " + inimigo.getNome());
            if (inimigo.estaMorto()) {
                salaAtual.setTipo(ConteudoNo.VAZIO);
                salaAtual.setInimigo(null);
                Dialogos.inimigoDerrotado(inimigo.getNome());
                return;
            }
            // Inimigo ataca
            int danoParaJogador = Math.max(0, inimigo.getAtaque() - jogador.getDefesa());
            jogador.receberDano(danoParaJogador);
            Dialogos.mostrarDanoJogador(danoParaJogador, jogador.getVida());

            faseAtual.registrarHistorico("Combate: inimigo " + inimigo.getNome() + " causou " + danoParaJogador + " ao jogador");

            if (jogador.getVida() <= 0) {
                Dialogos.jogadorDerrotado();
                jogo.reiniciarFase();
                return;
            }    
        }
    }
}
