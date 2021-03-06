
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import br.chatapp.dao.Mensagem;
import br.chatapp.dao.Mensagem.Estado;

public class ThreadServidor extends Thread {
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	private static Map<String, ObjectOutputStream> listaDeUsuariosOnline = new HashMap<String, ObjectOutputStream>();
	private boolean estaConectado = false;
	private boolean usuarioSalvo = false;
	
	public ThreadServidor(Socket socket) {
		this.socket = socket;
		try{
			input = new ObjectInputStream(this.socket.getInputStream());
			output = new ObjectOutputStream(this.socket.getOutputStream());
			output.writeObject(Mensagem.getLista());
		}catch (IOException e) {
			System.out.println("Erro Servidor: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Cliente Conectado");
			while (socket.isConnected()) {
				try{
					Mensagem mensagemRecebida = (Mensagem) input.readObject();
					if (mensagemRecebida != null) {
						Estado estado = mensagemRecebida.getEstado();
						switch (estado) {
						case CONECTANDO:{
							estaConectado = conectar(mensagemRecebida.getUsuario().getNome(), output);
							usuarioSalvo = mensagemRecebida.getUsuario().salvarBancoDeDados();
							}
							break;
						case CONECTADO:{
								if(usuarioSalvo && estaConectado){
									System.out.println("mensagem recebida");
									enviarTodos(mensagemRecebida);
									new Thread(()-> mensagemRecebida.salvarBancoDeDados()).start();
								}
							}
							break;
						case DESCONECTADO:
							desconectar(mensagemRecebida.getUsuario().getNome());
							break;
						default:
							break;
						}
					}
				}catch (ClassNotFoundException e) {
					System.out.println("Class not Found" + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Erro Servidor ThreadServidor: " + e.getMessage());
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (Exception e) {
				System.out.println("Erro fechar streams: " + e.getMessage());
			}
		}
	}
	
	private void desconectar(String usuario) {
		if (usuario != null) {
			listaDeUsuariosOnline.remove(usuario);
		}
	}

	public static boolean conectar(String usuario, ObjectOutputStream stream){
		if (usuario != null && stream != null) {
			listaDeUsuariosOnline.put(usuario, stream);
			return true;
		}
		return false;
	}
	
	public static void enviarTodos(Mensagem mensagem){
		listaDeUsuariosOnline.forEach((nome,stream) -> {
			try{
				stream.writeObject(mensagem);
				stream.reset();
			}catch (IOException e) {
				System.out.println("enviarTodos() " + e.getMessage());
			}
		});
	}
}