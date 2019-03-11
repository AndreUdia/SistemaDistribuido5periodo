package pratica2;


import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {

		try {
                    
                        InetAddress endereco_remoto;
                        
                        int porta_remota;
                    
			ServerSocket s = new ServerSocket(2000);

			System.out.println("Waiting conection......................");
			
			Socket conexao = s.accept();
			System.out.println("Conexão aceita, esperando dados");
                        
                        endereco_remoto = conexao.getInetAddress();
                        
                        porta_remota = conexao.getPort();
                        
                        
                        
                        FileWriter arq = new FileWriter("D:\\putz.txt");
                        
                        PrintWriter escrita = new PrintWriter(arq);
                        
                        escrita.println("Nome da maquina remota: " + endereco_remoto.getHostName());
                        escrita.println("IP da maquina remota: " + endereco_remoto.getHostAddress());
                        escrita.println("Porta maquina remota: " + porta_remota);
                        
                        
                        DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
			DataInputStream entrada = new DataInputStream(conexao.getInputStream());
			
			for (int i = 0; i < 200; i++) {
				int resp = entrada.readInt();
				System.out.println("processando............");
				saida.writeUTF("recebi seu dado: " + resp); 

			}
                        
                        arq.close();
                        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
