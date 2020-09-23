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
    private TextField TxtId;
    
    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtCPF;

    @FXML
    private Button btnAdd;

    @FXML
    private Button PesquisarId;

    @FXML
    private ComboBox<String> ChoiceCombo;

    @FXML
    private TextField PesquisaNome;
    
    @FXML
    private Pane Painel;
    
    private List<Cliente> listaClientes = new ArrayList<Cliente>();		
        
    @FXML
    void findById(ActionEvent event) {
    	if(!pesquisaPorId()) {
			new ShowAlert().erroAlert();
		}  
    }
    
    public boolean pesquisaPorId() {
    	 ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);
         
         
        for(Cliente cliente: lista) {
        	if(cliente.getId().equals(Integer.parseInt(TxtId.getText()))) {
        		TxtNome.setText(cliente.getNome());
        		TxtCPF.setText(cliente.getCpf());
        		TxtEmail.setText(cliente.getEmail());
        		TxtId.setEditable(false);
        		
        		TxtNome.setDisable(false);
    			TxtCPF.setDisable(false);
    			TxtEmail.setDisable(false);
    			btnAdd.setDisable(false);
    			
        		return true;
        	}
        }
         
         
         return false;
   }

    @FXML
    void sair(ActionEvent event) {
    	new ShowAlert().mensagemAlert();
    	
        Stage stage = (Stage) btnAdd.getScene().getWindow(); 
	    stage.close();
    }

    @FXML
    void botaoAcao(ActionEvent event) {
    	if(ChoiceCombo.getValue() == "Incluir") {
    		incluirCliente();
    	} else if(ChoiceCombo.getValue() == "Alterar") {
    		TxtId.setEditable(true);
    		alterarCliente();
    	} else if(ChoiceCombo.getValue() == "Excluir") {
    		TxtId.setEditable(true);
    		excluirCliente();
    	}
    }

    @FXML
    void findByName(ActionEvent event) {
    	
    	ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);
        
    	List<Cliente> novaLista = new ArrayList<Cliente>();
    	
	        if(!PesquisaNome.getText().isEmpty() || !PesquisaNome.getText().equals("")) {
		        for(Cliente cliente: lista) {
		        	if(cliente.getNome().contains(PesquisaNome.getText())) {
		        		System.out.println(cliente);
		        		novaLista.add(cliente);
		        	}
		        }
		        
		 	   ObservableList<Cliente> novaListaObs = FXCollections.observableArrayList(novaLista);

			   Tabela.setItems(novaListaObs);
	        } else {
	        	listar();
	        }
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Painel.setVisible(false);
		
		ChoiceCombo.getItems().setAll("Incluir", "Alterar", "Excluir"); 
		
		ChoiceCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
            	limparCampos();
            	if(ChoiceCombo.getValue() == "Incluir") {
            		camposVisiveis("Adicionar Cliente", "Adicionar", 300, false);
            		TxtId.setEditable(true);
            	} else if (ChoiceCombo.getValue() == "Alterar"){
            		camposVisiveis("Editar Cliente", "Editar", 157, true);
            		TxtId.setEditable(true);
            	} else if (ChoiceCombo.getValue() == "Excluir"){
            		camposVisiveis("Excluir Cliente", "Excluir", 157, true);
            		TxtId.setEditable(true);
            	}
            }
        });
		
		listar();
		
		MaskFieldUtil.cpfField(this.TxtCPF);
	}
	
	public void incluirCliente() {
		if(validacaoCampos()) {
			if(listaClientes.size() != 0) {
				Cliente cliente = listaClientes.get(listaClientes.size() -1);
			
				listaClientes.add(new Cliente(cliente.getId() + 1, TxtCPF.getText(), TxtEmail.getText(), TxtNome.getText()));
			} else {
				listaClientes.add(new Cliente(1, TxtCPF.getText(), TxtEmail.getText(), TxtNome.getText()));
			}
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
	        	if(cliente.getId().equals(Integer.parseInt(TxtId.getText()))) {
	        		listaClientes.remove(cliente);
	        		listaClientes.add(new Cliente(Integer.parseInt(TxtId.getText()), TxtCPF.getText(), TxtEmail.getText(), TxtNome.getText()));
	        	}
	        }
	        
			new ShowAlert().sucessoAlert("Cliente editado com sucesso!");

	    	limparCampos();
	    	
	        listar();
	        
			camposVisiveis("Editar Cliente", "Editar", 157, true);
		} else {
			new ShowAlert().validacaoAlert();
		}
	}
	
	public void excluirCliente() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);

        if (new ShowAlert().confirmationAlert()) {
			for(Cliente cliente: lista) {
				if(cliente.getId().equals(Integer.parseInt(TxtId.getText()))) {
					listaClientes.remove(cliente);
		        }
		    }		
		}
        
    	limparCampos();
    	
        listar();
        
		camposVisiveis("Excluir Cliente", "Excluir", 157, true);

	}
	
	
	public void listar() {
		Nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Nome"));
		Cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Cpf"));
		Email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Email"));
		Id.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("Id"));
		
	    Tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    ObservableList<Cliente> lista = FXCollections.observableArrayList(listaClientes);

	    Tabela.setItems(lista);
	}
	
	public void limparCampos() {
		TxtId.setText("");
		TxtNome.setText("");
		TxtCPF.setText("");
		TxtEmail.setText("");
	}
	

	public void camposVisiveis(String titulo,String botao, int tamanho, boolean visivel) {
		LabelChange.setText(titulo);
		btnAdd.setText(botao);
		
		TxtId.setPrefWidth(tamanho);
		PesquisarId.setVisible(visivel);
		Painel.setVisible(true);
		
		TxtNome.setDisable(visivel);
		TxtCPF.setDisable(visivel);
		TxtEmail.setDisable(visivel);
		btnAdd.setDisable(visivel);
		
		if(!visivel) {
			TxtId.setVisible(false);
			LabelId.setVisible(false);
			
			LabelNome.setLayoutY(42);
			TxtNome.setLayoutY(82);
			
			LabelCPF.setLayoutY(142);
			TxtCPF.setLayoutY(182);
			
			LabelEmail.setLayoutY(242);
			TxtEmail.setLayoutY(282);
			
			btnAdd.setLayoutY(352);
		} else {
			TxtId.setVisible(true);
			LabelId.setVisible(true);
			
			LabelNome.setLayoutY(142);
			TxtNome.setLayoutY(182);
			
			LabelCPF.setLayoutY(242);
			TxtCPF.setLayoutY(282);
			
			LabelEmail.setLayoutY(342);
			TxtEmail.setLayoutY(382);
			
			btnAdd.setLayoutY(452);
		}
	}
	
	public boolean validacaoCampos() {
		
		if(TxtEmail.getText().isEmpty() | TxtNome.getText().isEmpty() | TxtCPF.getText().isEmpty()) {
			return false;
		}	
		return true;
	}

}
