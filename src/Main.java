import java.io.IOException;
import java.net.ServerSocket;

import org.rodolfo.bancodedadosmysql.BancoDeDados;

import br.chatapp.dao.Mensagem;

public class Main {
	public static void main(String[] args) {
		if(BancoDeDados.pegarInstancia().conectar()){
			Mensagem.buscarTodas();
			try (ServerSocket serverSocket = new ServerSocket(5000)) {
				while (true) {
					new ThreadServidor(serverSocket.accept()).start();
				}
			} catch (IOException e) {
				System.out.println("Erro Servidor Main: " + e.getMessage());
			}
		}
		BancoDeDados.pegarInstancia().fecharConexao();
	}
}