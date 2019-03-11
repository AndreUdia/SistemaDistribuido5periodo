package pratica2;


import java.io.*;
import java.net.*;

public class Cliente {

	public static void main(String[] args) {

		try {
			Socket s = new Socket("localhost", 2000);

			DataOutputStream saida = new DataOutputStream(s.getOutputStream());
			DataInputStream entrada = new DataInputStream(s.getInputStream());

			for (int i = 0; i < 200; i++) {
				saida.writeInt(i);
				System.out.println("Enviei: " + i);
				String en = entrada.readUTF();
				System.out.println("Recebi: " + en);

			}
			s.close();
		} catch (java.net.ConnectException e) {
			System.out.println(e.getMessage());
		}

		catch (IOException e) {
			
			System.out.println(e.getMessage());
		}
	}

}
