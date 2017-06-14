import java.io.IOException;
import java.net.ServerSocket;

import org.rodolfo.banco.BancoDeDados;

import br.chatapp.dao.Mensagem;

public class Main {
	public static void main(String[] args) {
		if(BancoDeDados.conectar()){
			Mensagem.buscarTodas();
			try (ServerSocket serverSocket = new ServerSocket(5000)) {
				while (true) {
					new ThreadServidor(serverSocket.accept()).start();
				}
			} catch (IOException e) {
				System.out.println("Erro Servidor Main: " + e.getMessage());
			}
		}
		BancoDeDados.fecharConexao();
	}
}