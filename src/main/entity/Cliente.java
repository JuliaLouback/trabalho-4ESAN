package main.entity;

import java.util.List;

public class Cliente {

	private Integer Id;
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	private String Cpf;
	
	private String Email;
	
	private String Nome;

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cpf) {
		Cpf = cpf;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	@Override
	public String toString() {
		return "Cliente [Cpf=" + Cpf + ", Email=" + Email + ", Nome=" + Nome + "]";
	}

	public Cliente(Integer id, String cpf, String email, String nome) {
		super();
		Id = id;
		Cpf = cpf;
		Email = email;
		Nome = nome;
	}
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente encontrarCliente(
        Integer id, List<Cliente> clientes) {
		    for (Cliente cliente : clientes) {
		        if (cliente.getId().equals(Id)) {
		            return cliente;
		        }
		    }
		    return null;
		}
	
}
