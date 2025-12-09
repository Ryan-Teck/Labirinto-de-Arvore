package view;

import controller.ControladorFase;
import controller.ControladorJogo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Fase;
import model.Jogador;
import model.NoNA;

// Controlador da tela do jogo
public class TelaJogoController {
 
    @FXML private Label lblVida;
    @FXML private Label lblAtaque;
    @FXML private Label lblDefesa;
    @FXML private Label lblChaves;
    @FXML private Label lblTipoSala;
    @FXML private TextArea txtHistorico;
 
    @FXML private Button btnFilho1;
    @FXML private Button btnFilho2;
    @FXML private Button btnFilho3;
    @FXML private Button btnVoltar;
 
    private ControladorJogo controladorJogo;
    private ControladorFase controladorFase;
 
    public void inicializar(ControladorJogo controladorJogo, ControladorFase controladorFase) {
        this.controladorJogo = controladorJogo;
        this.controladorFase = controladorFase;
        
        // Aguarda um tick para garantir que tudo está inicializado
        System.out.println("[TelaJogo] inicializar chamado: controladorJogo=" + controladorJogo + ", controladorFase=" + controladorFase);
        Platform.runLater(() -> atualizarInterface());
    }
 
    /** Atualiza todos os elementos da interface */
    public void atualizarInterface() {
        System.out.println("[TelaJogo] atualizarInterface chamado");

        // Verificações de null
        if (controladorJogo == null || controladorJogo.getJogo() == null) {
            System.out.println("ERRO: controladorJogo ou jogo é null");
            return;
        }
        
        Fase fase = controladorJogo.getJogo().getFaseAtual();
        if (fase == null) {
            System.out.println("ERRO: fase é null");
            return;
        }
        
        Jogador jogador = controladorJogo.getJogo().getJogador();
        if (jogador == null) {
            System.out.println("ERRO: jogador é null");
            return;
        }
        
        NoNA sala = fase.getNoAtual();
        if (sala == null) {
            System.out.println("ERRO: sala é null");
            return;
        }
 
        // Atualiza labels com debug
        lblVida.setText("Vida: " + jogador.getVida());
        lblAtaque.setText("Ataque: " + jogador.getAtaque());
        lblDefesa.setText("Defesa: " + jogador.getDefesa());
        lblChaves.setText("Chaves: " + fase.getChavesColetadas() + "/" + fase.getChavesNecessarias());
 
        lblTipoSala.setText("Sala Atual: " + sala.getTipo() + " — ID " + sala.getId());
 
        // HISTÓRICO
        txtHistorico.clear();
        fase.getHistorico().forEach(evento -> txtHistorico.appendText(evento + "\n"));
 
        // scroll automático (JavaFX 21 fix)
        Platform.runLater(() -> txtHistorico.setScrollTop(Double.MAX_VALUE));
 
        // BOTÕES
        atualizarBotoesFilhos(sala);
        btnVoltar.setDisable(sala.getPai() == null);
    }
 
    /** Configura botões dos filhos */
    private void atualizarBotoesFilhos(NoNA sala) {
 
        int total = sala.getFilhos() != null ? sala.getFilhos().size() : 0;
 
        configurarBotao(btnFilho1, sala, 0, total);
        configurarBotao(btnFilho2, sala, 1, total);
        configurarBotao(btnFilho3, sala, 2, total);
    }
 
    private void configurarBotao(Button botao, NoNA sala, int index, int total) {
        if (index < total) {
            NoNA filho = sala.getFilhos().get(index);
            botao.setDisable(false);
            botao.setText("Ir para Sala " + filho.getId());
        } else {
            botao.setDisable(true);
            botao.setText("Sem Sala");
        }
    }
 
    // Eventos dos botões
    @FXML
    private void irFilho1() { irParaFilho(0); }
 
    @FXML
    private void irFilho2() { irParaFilho(1); }
 
    @FXML
    private void irFilho3() { irParaFilho(2); }
 
    private void irParaFilho(int index) {
        controladorFase.moverJogadorPara(index);
        controladorFase.analisarSalaAtual(controladorJogo.getJogo().getFaseAtual().getNoAtual());
        atualizarInterface();
    }
 
    @FXML
    private void voltarParaPai() {
        controladorFase.moverJogadorParaPai();
        controladorFase.analisarSalaAtual(controladorJogo.getJogo().getFaseAtual().getNoAtual());
        atualizarInterface();
    }
}
