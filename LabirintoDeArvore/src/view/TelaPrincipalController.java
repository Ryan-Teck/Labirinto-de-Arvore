package view;

import controller.ControladorJogo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// Controlador da tela principal do jogo
public class TelaPrincipalController {
    @FXML
    private Button btnIniciarJogo;
    
    private ControladorJogo controladorJogo;

    @FXML
    private void iniciarJogo() {
        controladorJogo.iniciarJogo();
    }

    public void inicializar(ControladorJogo controladorJogo) {
        this.controladorJogo = controladorJogo;
    }
}
