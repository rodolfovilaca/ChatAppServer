package br.chatapp.dao;

import java.io.Serializable;

import org.rodolfo.bancodedadosmysql.BancoDeDados;

public class Usuario implements Serializable{
	static final long serialVersionUID = 3632977338254009699L;

    private String nome;
    private final String ADICIONAR_USUARIO = "INSERT INTO Usuario (usuario_nome) VALUES";

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return this.nome;
    }
    
    public int getForeignKeyId(){
    	int keyId = BancoDeDados.buscarId("SELECT usuario_id FROM Usuario WHERE usuario_nome = '"+this.getNome()+"';");
    	return keyId;
    }
    
    public boolean salvarBancoDeDados() {
    	int buscaUsuario = BancoDeDados.buscarId("SELECT * FROM Usuario WHERE usuario_nome = '"+this.getNome()+"';");
    	if(buscaUsuario == 0){
    		BancoDeDados.inserir(ADICIONAR_USUARIO+"('"+this.getNome()+"')");
    	}
        return true;
    }
}
