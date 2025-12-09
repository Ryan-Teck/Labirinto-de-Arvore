package model;

// Classe que representa um inimigo no jogo
public class Inimigo {
    private String nome;
    private int ataque;
    private int defesa;
    private int vida;

    // Construtor
    public Inimigo(String nome, int vida, int ataque, int defesa) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    // Métodos de acesso

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    // Método auxiliares
    // Retorna true se o inimigo estiver morto(vida <= 0)
    public boolean estaMorto() {
        return this.vida <= 0;
    }
}
