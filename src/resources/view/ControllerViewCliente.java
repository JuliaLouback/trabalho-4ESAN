package resources.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.entity.Cliente;
import main.util.MaskFieldUtil;
import main.util.ShowAlert;

public class ControllerViewCliente implements Initializable{

    @FXML
    private TableView<Cliente> Tabela;

    @FXML
    private TableColumn<Cliente, Integer> Id;

    @FXML
    private TableColumn<Cliente, String> Nome;

    @FXML
    private TableColumn<Cliente, String> Cpf;

    @FXML
    private TableColumn<Cliente, String> Email;

    @FXML
    private Label LabelChange;
    
    @FXML
    private Label LabelId;
    
    @FXML
    private Label LabelCPF;

    @FXML
    private Label LabelEmail;
    
    @FXML
    private Label LabelNome;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtCPF;

    @FXML
    private Button btnAdd;

    @FXML
    private Pane Painel;
    
    private List<Cliente> listaClientes = new ArrayList<Cliente>();		
        
    @FXML
    void findById(ActionEvent event) {
        ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);
       
        for(Cliente cliente: lista) {
        	if(cliente.getId().equals(Integer.parseInt("1"))) {
        		TxtNome.setText(cliente.getNome());
        		TxtCPF.setText(cliente.getCpf());
        		TxtEmail.setText(cliente.getEmail());
        	}
        }
    }
    
    @FXML
    void selecionarAcao(ActionEvent event) {
    	String[] acoes = {"Incluir","Alterar","Excluir"};
    	
    	ChoiceDialog choiceDialog = new ChoiceDialog(acoes[0],acoes);
    	choiceDialog.setHeaderText("Selecione uma Ação");
    	choiceDialog.setContentText("Por favor selecione uma ação:");
    	
    	Optional<String> result = choiceDialog.showAndWait();
    	
    	if(result.isPresent()) {
    		Painel.setVisible(true);
    	}
    }

    @FXML
    void sair(ActionEvent event) {
    	new ShowAlert().mensagemAlert();
    	
        Stage stage = (Stage) btnAdd.getScene().getWindow(); 
	    stage.close();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Nome"));
		Cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Cpf"));
		Email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Email"));
		Id.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("Id"));
		
	    Tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
		Painel.setVisible(false);
		
		MaskFieldUtil.cpfField(this.TxtCPF);
	}
	
	public void incluirCliente() {
		if(validacaoCampos()) {
			listaClientes.add(new Cliente(listaClientes.size() + 1, TxtCPF.getText(), TxtEmail.getText(), TxtNome.getText()));
			new ShowAlert().sucessoAlert("Cliente adicionado com sucesso!");
			limparCampos();
			listar();
		} else {
			new ShowAlert().validacaoAlert();
		}
	}
	
	public void alterarCliente() {
		if(validacaoCampos()) {
	        ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);
	
	        for(Cliente cliente: lista) {
	        	if(cliente.getId().equals("")) {
	        		listaClientes.remove(cliente);
	        		listaClientes.add(new Cliente(Integer.parseInt("1"), TxtCPF.getText(), TxtEmail.getText(), TxtNome.getText()));
	        	}
	        }
	      
	    	limparCampos();
	    	
	        listar();
	        
		} else {
			new ShowAlert().validacaoAlert();
		}
	}
	
	public void excluirCliente() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);

        if (new ShowAlert().confirmationAlert()) {
			for(Cliente cliente: lista) {
				if(cliente.getId().equals(Integer.parseInt("1"))) {
					listaClientes.remove(cliente);
		        }
		    }		
		}
        
    	limparCampos();
    	
        listar();
        
	}
	
	
	public void listar() {
	    ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);

	    Tabela.setItems(lista);
	}
	
	public void limparCampos() {
		TxtNome.setText("");
		TxtCPF.setText("");
		TxtEmail.setText("");
	}
	
	
	public boolean validacaoCampos() {
		
		if(TxtEmail.getText().isEmpty() | TxtNome.getText().isEmpty() | TxtCPF.getText().isEmpty()) {
			return false;
		}	
		return true;
	}

}
