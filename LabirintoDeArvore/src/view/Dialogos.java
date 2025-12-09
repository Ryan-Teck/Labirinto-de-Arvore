package view;

import javafx.scene.control.Alert;

// Classe para exibir diálogos informativos ao jogador
public class Dialogos {
    public static void mostrar(String msg) {
        alerta("Informação", msg);
    }
 
    public static void combateIniciado(String nomeInimigo) {
        alerta("Combate", "Você iniciou um combate contra: " + nomeInimigo);
    }
 
    public static void inimigoDerrotado(String nomeInimigo) {
        alerta("Combate", "Você derrotou: " + nomeInimigo);
    }
 
    public static void buffVida() {
        alerta("Buff", "Você ganhou +100 de Vida!");
    }
 
    public static void buffAtaque() {
        alerta("Buff", "Você ganhou +100 de Ataque!");
    }
 
    public static void buffDefesa() {
        alerta("Buff", "Você ganhou +100 de Defesa!");
    }
 
    public static void chaveColetada() {
        alerta("Item", "Você coletou uma chave!");
    }
 
    public static void jogadorDerrotado() {
        alerta("Derrota", "Você foi derrotado! A fase será reiniciada.");
    }
 
    public static void faseConcluida() {
        alerta("Fase Concluída", "Você encontrou a saída!");
    }

    public static void saidaSemChaves(int chavesNecessarias) {
        alerta("Saída Trancada", "Você precisa de " + chavesNecessarias + " chaves para sair desta fase.");
    }

    public static void mostrarDanoInimigo(int danoParaInimigo, int vida) {
        alerta("Ataque", "Você causou " + danoParaInimigo + " de dano ao inimigo. Vida restante do inimigo: " + vida);
    }

    public static void mostrarDanoJogador(int danoParaJogador, int vida) {
        alerta("Ataque", "O inimigo causou " + danoParaJogador + " de dano a você. Sua vida restante: " + vida);
    }
 
    private static void alerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
