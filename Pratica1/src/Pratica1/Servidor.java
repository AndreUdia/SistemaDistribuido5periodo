package Pratica1;


import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {

		try {
			ServerSocket s = new ServerSocket(2000);

			System.out.println("Waiting conection......................");
			
			Socket conexao = s.accept();
			System.out.println("Conexão aceita, esperando dados");
			
			DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
			DataInputStream entrada = new DataInputStream(conexao.getInputStream());
			
			for (int i = 0; i < 100000; i++) {
				int resp = entrada.readInt();
				System.out.println("processando............");
				saida.writeUTF("recebi seu dado: " + resp); 

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
