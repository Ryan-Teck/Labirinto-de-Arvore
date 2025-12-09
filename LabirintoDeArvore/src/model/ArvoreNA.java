package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// árvore N-ária
public class ArvoreNA {
    private NoNA raiz;
    private int grauMaximo;

    // Construtor
    public ArvoreNA(int grauMaximo) {
        this.grauMaximo = grauMaximo;
        this.raiz = null;
    }

    // Métodos de acesso
    public NoNA getRaiz() {
        return raiz;
    }

    public void setRaiz(NoNA raiz) {
        this.raiz = raiz;
    }

    public int getGrauMaximo() {
        return grauMaximo;
    }

    // Inserir nó na árvore
    public boolean inserirFilho(NoNA pai, NoNA novoFilho) {
        if (novoFilho == null) {
            return false;
        }
        if (pai == null) {
            // se não houver pai, define como raiz (opcional)
            if (this.raiz == null) {
                this.raiz = novoFilho;
                novoFilho.setPai(null);
                return true;
            } else {
                return false; // já existe raiz
            }
        }
        if (pai.getFilhos().size() >= grauMaximo) {
            return false; // não pode adicionar mais filhos
        }
        novoFilho.setPai(pai);
        pai.getFilhos().add(novoFilho);
        return true;
    }

    // Remover nó da árvore
    public boolean removerNo(NoNA no) {
        if (no == null) {
            return false; // nó nulo
        }
        if (no.getPai() == null) {
            return false;
        }

        NoNA pai = no.getPai();

        // remover o nó da lista de filhos do pai
        pai.getFilhos().remove(no);

        // remover recursivamente todos os filhos do nó (cópia para evitar ConcurrentModification)
        removerNoRecursivo(no);

        // reorganizar a subárvore a partir do pai (se necessário)
        reorganizarArvore(pai);

        return true;
    }

    private void removerNoRecursivo(NoNA no) {
        if (no == null) {
            return;
        }
        List<NoNA> filhosCopia = new ArrayList<>(no.getFilhos());
        for (NoNA filho : filhosCopia) {
            removerNoRecursivo(filho);
        }
        no.getFilhos().clear();
        no.setPai(null);
        no.setTipo(ConteudoNo.VAZIO);
        no.setInimigo(null);
    }

    // Reorganizar a subárvore para respeitar grauMaximo (reconstrução em largura)
    private void reorganizarArvore(NoNA subRaiz) {
        if (subRaiz == null) {
            return;
        }

        List<NoNA> todos = new ArrayList<>();
        Queue<NoNA> fila = new LinkedList<>();
        fila.add(subRaiz);

        while (!fila.isEmpty()) {
            NoNA atual = fila.poll();
            todos.add(atual);

            // enfileira filhos à fila antes de limpar
            for (NoNA filho : new ArrayList<>(atual.getFilhos())) {
                fila.add(filho);
            }

            // limpa os filhos do nó atual
            atual.getFilhos().clear();
            atual.setPai(null);
        }

        // reconstruir a subárvore em largura mantendo o primeiro como subRaiz
        NoNA novaLocal = todos.get(0);
        Queue<NoNA> pais = new LinkedList<>();
        pais.add(novaLocal);

        for (int i = 1; i < todos.size(); i++) {
            NoNA filho = todos.get(i);
            while (!pais.isEmpty()) {
                NoNA paiAtual = pais.peek();
                if (paiAtual.getFilhos().size() < grauMaximo) {
                    paiAtual.getFilhos().add(filho);
                    filho.setPai(paiAtual);
                    pais.add(filho);
                    break;
                } else {
                    pais.poll();
                }
            }
        }

        // se subRaiz era a raiz da árvore original, manter referência
        if (subRaiz == this.raiz) {
            this.raiz = novaLocal;
            this.raiz.setPai(null);
        }
    }

    // Buscar nó por ID (BFS)
    public NoNA buscarNoPorId(int id) {
        if (raiz == null) {
            return null;
        }

        Queue<NoNA> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            NoNA atual = fila.poll();
            if (atual.getId() == id) {
                return atual;
            }
            fila.addAll(atual.getFilhos());
        }
        return null; // nó não encontrado
    }

    // Buscar nó mais distante da raiz (BFS)
    public NoNA buscarNoMaisLongeDaRaiz() {
        if (raiz == null) {
            return null;
        }

        Queue<NoNA> fila = new LinkedList<>();
        fila.add(raiz);
        NoNA ultimo = null;
        while (!fila.isEmpty()) {
            ultimo = fila.poll();
            fila.addAll(ultimo.getFilhos());
        }
        return ultimo; // Retorna o nó mais distante da raiz
    }

    // Retorna o primeiro nó que possui espaço para adicionar filho (BFS)
    public NoNA buscarPrimeiroNoComEspaco() {
        if (raiz == null) {
            return null;
        }

        Queue<NoNA> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            NoNA atual = fila.poll();
            if (atual.getFilhos().size() < grauMaximo) {
                return atual;
            }
            fila.addAll(atual.getFilhos());
        }
        return null; // Todos os nós estão cheios
    }

    // Percurso em largura (BFS)
    public List<NoNA> percursoEmLargura() {
        List<NoNA> lista = new ArrayList<>();
        if (raiz == null) {
            return lista;
        }

        Queue<NoNA> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            NoNA atual = fila.poll();
            lista.add(atual);
            fila.addAll(atual.getFilhos());
        }
        return lista;
    }

    // Percurso em profundidade (pré-ordem)
    public List<NoNA> percursoEmProfundidade() {
        List<NoNA> lista = new ArrayList<>();
        percursoEmProfundidadeRecursivo(raiz, lista);
        return lista;
    }

    // método auxiliar para percurso em profundidade
    private void percursoEmProfundidadeRecursivo(NoNA no, List<NoNA> lista) {
        if (no == null) {
            return;
        }
        lista.add(no);
        for (NoNA filho : no.getFilhos()) {
            percursoEmProfundidadeRecursivo(filho, lista);
        }
    }
}
