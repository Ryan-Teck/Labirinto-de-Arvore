package model;

import java.util.Collections;
import java.util.List;

// Classe responsável por gerar as fases do jogo como árvores N-árias
public class GeradorArvore {

    public static Fase gerarFase(int numero) {
        switch (numero) {
            case 1:
                return criarFase1();
            case 2:
                return criarFase2();
            case 3:
                return criarFase3();
            case 4:
                return criarFase4();
            default:
                throw new IllegalArgumentException("Número de fase inválido: " + numero);
        }
    }

    // cria a estrutura da fase 1
    private static Fase criarFase1() {
        ArvoreNA arvore = new ArvoreNA(3);
        NoNA raiz = new NoNA(0, ConteudoNo.VAZIO, null);
        arvore.setRaiz(raiz);

        // inseri nos vazios
        for (int i = 1; i < 7; i++) {
            NoNA pai = arvore.buscarPrimeiroNoComEspaco();
            arvore.inserirFilho(pai, new NoNA(i, ConteudoNo.VAZIO, null));
        }

        //define saida
        NoNA noSaida = arvore.buscarNoMaisLongeDaRaiz();
        if (noSaida != null) {
            noSaida.setTipo(ConteudoNo.SAIDA);
            return new Fase(arvore, raiz, 0, 1);
        }
        return null;
    }

    // cria a estrutura da fase 2
    private static Fase criarFase2() {
        ArvoreNA arvore = new ArvoreNA(3);
        NoNA raiz = new NoNA(0, ConteudoNo.VAZIO, null);
        arvore.setRaiz(raiz);

        // inseri nos vazios
        for (int i = 1; i < 9; i++) {
            NoNA pai = arvore.buscarPrimeiroNoComEspaco();
            arvore.inserirFilho(pai, new NoNA(i, ConteudoNo.VAZIO, null));
        }

        // define saida
        NoNA noSaida = arvore.buscarNoMaisLongeDaRaiz();
        if (noSaida != null) {
            noSaida.setTipo(ConteudoNo.SAIDA);
        }
        // Chave e Saida em locais diferentes
        NoNA nochave = arvore.buscarNoMaisLongeDaRaiz();
        if (nochave == noSaida) {
            nochave = noSaida.getPai();
        }
        if (nochave != null) {
            nochave.setTipo(ConteudoNo.CHAVE);
        }
        
        return new Fase(arvore, raiz, 1, 2);
    }

    // cria a estrutura da fase 3
    private static Fase criarFase3() {
        ArvoreNA arvore = new ArvoreNA(4);
        NoNA raiz = new NoNA(0, ConteudoNo.VAZIO, null);
        arvore.setRaiz(raiz);

        // inseri nos vazios
        for (int i = 1; i < 12; i++) {
            NoNA pai = arvore.buscarPrimeiroNoComEspaco();
            arvore.inserirFilho(pai, new NoNA(i, ConteudoNo.VAZIO, null));
        }

        // define saida
        NoNA noSaida = arvore.buscarNoMaisLongeDaRaiz();
        if (noSaida != null) {
            noSaida.setTipo(ConteudoNo.SAIDA);
        }
        // Chave e Saida em locais diferentes
        NoNA nochave = arvore.buscarNoMaisLongeDaRaiz();
        if (nochave == noSaida) {
            nochave = arvore.buscarPrimeiroNoComEspaco();
        }
        if (nochave != null) {
            nochave.setTipo(ConteudoNo.CHAVE);
        }

        // Inimigo no pai da Saida
        if (noSaida != null) {
            NoNA inimigo = noSaida.getPai();
            if (inimigo != null) {
                inimigo.setTipo(ConteudoNo.INIMIGO);
                inimigo.setInimigo(new Inimigo("Goblin Guerreiro", 60, 12, 8));
            }
        }

        return new Fase(arvore, raiz, 2, 3);
    }

    // cria a estrutura da fase 4
    private static Fase criarFase4() {
        ArvoreNA arvore = new ArvoreNA(3);
        NoNA raiz = new NoNA(0, ConteudoNo.VAZIO, null);
        arvore.setRaiz(raiz);

        // inseri nos vazios
        for (int i = 1; i < 15; i++) {
            NoNA pai = arvore.buscarPrimeiroNoComEspaco();
            arvore.inserirFilho(pai, new NoNA(i, ConteudoNo.VAZIO, null));
        }

        // Adiciona buffs aleatórios
        adicionarBuff(arvore, ConteudoNo.BUFF_ATAQUE);
        adicionarBuff(arvore, ConteudoNo.BUFF_DEFESA);
        adicionarBuff(arvore, ConteudoNo.BUFF_VIDA);

        // define saida
        NoNA noSaida = arvore.buscarNoMaisLongeDaRaiz();
        if (noSaida != null) {
            noSaida.setTipo(ConteudoNo.SAIDA);
        }
        // Chave e Saida em locais diferentes
        NoNA nochave = arvore.buscarNoMaisLongeDaRaiz();
        if (nochave == noSaida) {
            nochave = arvore.buscarPrimeiroNoComEspaco();
        }
        if (nochave != null) {
            nochave.setTipo(ConteudoNo.CHAVE);
        }

        // Inimigo no pai da Saida
        NoNA inimigo = noSaida.getPai();
        if (inimigo != null) {
            inimigo.setTipo(ConteudoNo.INIMIGO);
            inimigo.setInimigo(new Inimigo("Rei Orc", 80, 15, 10));
        }

        // Inimigo em outro nó aleatório
        NoNA outroInimigo = arvore.buscarPrimeiroNoComEspaco();
        while (outroInimigo == nochave) {
            outroInimigo = arvore.buscarPrimeiroNoComEspaco();
        }
        if (outroInimigo != null) {
            outroInimigo.setTipo(ConteudoNo.INIMIGO);
            outroInimigo.setInimigo(new Inimigo("Orc Berserker", 70, 14, 9));
        }

        return new Fase(arvore, raiz, 3, 4);
    }

    // Metodo auxiliar para adicionar buffs
    public static void adicionarBuff(ArvoreNA arvore, ConteudoNo tipoBuff) {
        List<NoNA> nos = arvore.percursoEmLargura();
        Collections.shuffle(nos); // Embaralha a lista para escolher aleatoriamente
        for (NoNA no : nos) {
            if (no.getTipo() == ConteudoNo.VAZIO) {
                no.setTipo(tipoBuff);
                return;
            }
        }
    }
}
