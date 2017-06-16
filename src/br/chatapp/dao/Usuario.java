package br.chatapp.dao;

import java.io.Serializable;

import org.rodolfo.bancodedadosmysql.BancoDeDados;


public class Usuario implements Serializable{
	static final long serialVersionUID = 3632977338254009699L;

    private String nome;
    private final String ADICIONAR_USUARIO = "INSERT IGNORE INTO Usuario (usuario_nome) VALUES";

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
    	String comando = "SELECT usuario_id FROM Usuario WHERE usuario_nome LIKE '%"+this.getNome()+"%';";
    	int keyId = BancoDeDados.buscarId(comando);
    	return keyId;
    }
    
    public boolean salvarBancoDeDados() {
    	BancoDeDados.inserir(ADICIONAR_USUARIO+"('"+this.getNome()+"')");
        return true;
    }
}
