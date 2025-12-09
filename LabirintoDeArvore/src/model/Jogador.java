package model;

// Classe que representa o jogador no jogo
public class Jogador {
    private int vida;
    private int ataque;
    private int defesa;
    private int chaves;

    // Construtor padrão
     public Jogador() {
        this.vida = 100; // Vida padrão
        this.ataque = 100; // Ataque padrão
        this.defesa = 100; // Defesa padrão
        this.chaves = 0; // Inicialmente, o jogador não tem chaves
    }

    // Construtor customizado
    public Jogador(int vida, int ataque, int defesa) {
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.chaves = 0; // Inicialmente, o jogador não tem chaves
    }
   

    // Gerenciamento de buffs
    public void aplicarBuffVida() {
        this.vida += 100;
    }

    public void aplicarBuffAtaque() {
        this.ataque += 100;
    }

    public void aplicarBuffDefesa() {
        this.defesa += 100;
    }

    // Métodos para gerenciar chaves
    public void coletarChave() {
        this.chaves++;
    }
    
    public void resetarChaves() {
        this.chaves = 0;
    }

    // Receber dano do inimigo
    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0; // Vida não pode ser negativa
        }
    }

    // Métodos de acesso
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
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
    
    public int getChaves() {
        return chaves;
    }
}
