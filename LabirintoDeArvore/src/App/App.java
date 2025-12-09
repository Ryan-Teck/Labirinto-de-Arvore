package App;

import controller.ControladorJogo;
import javafx.application.Application;
import javafx.stage.Stage;

// Iniciar aplicação
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControladorJogo controladorJogo = new controller.ControladorJogo(stage);
        controladorJogo.carregarTelaInicial(); // Carrega a tela inicial
    }
    public static void main(String[] args) {
        launch(args);// Inicia a aplicação JavaFX
    }
}
