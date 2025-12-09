package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Jogo;
import view.TelaJogoController;
import view.TelaPrincipalController;

// Controlador principal do jogo
public class ControladorJogo {
     private Stage stage;
    private Jogo jogo;
    private ControladorFase controladorFase;
 
    public ControladorJogo(Stage stage) {
        this.stage = stage;
        this.jogo = new Jogo();
    }
    
    // Carregar tela inicial 
    public void carregarTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
            Parent root = loader.load();
 
            // pega o controlador da tela
            TelaPrincipalController ctrlTela = loader.getController();
 
            // envia dependências
            ctrlTela.inicializar(this);
 
            stage.setScene(new Scene(root));
            stage.setTitle("Labirinto de Árvore N-ária");
            stage.show();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // Iniciar o jogo
    public void iniciarJogo() {
        carregarTelaJogo();
    }

    // Carregar a tela da fase atual
    private void carregarTelaJogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaJogo.fxml"));
            Parent root = loader.load();
 
            // cria o controlador de fase (usa o construtor com Jogo e ControladorJogo)
            controladorFase = new ControladorFase(jogo, this);

            // pega o controlador da tela
            TelaJogoController ctrlTela = loader.getController();
 
            // envia dependências
            ctrlTela.inicializar(this, controladorFase);
 
            stage.setScene(new Scene(root));
            stage.setTitle("Fase " + jogo.getNumeroFase());
            stage.show();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // Avançar para a próxima fase
    public void proximaFase() {
        boolean avancou = jogo.avancarFase();

        if (!avancou) {
            mostrarMensagem("Parabéns! Você completou todas as fases do jogo!");
            Platform.exit();
            return;
        }

        mostrarMensagem("Fase " + jogo.getNumeroFase() + " iniciada!");
        carregarTelaJogo();
    }
 
    // Reiniciar a fase atual
    public void reiniciarFase() {
        mostrarMensagem("Você foi derrotado! Reiniciando fase...");
        jogo.reiniciarFase();
        carregarTelaJogo();
    }
 
    // Mostrar mensagem em alerta
    public void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso: ");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Retorna o jogo
    public Jogo getJogo() {
        return jogo;
    }
}
