package pratica2;


import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{
    
        private static boolean done = false;
        private final Socket conexao;
        
        public Cliente(Socket s){
            conexao = s;
        }

	public static void main(String[] args) {
            try {
                Socket conexao = new Socket ("localhost", 2000);
                PrintStream saida = new PrintStream(conexao.getOutputStream());
                
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
                
                System.out.println("Entre com o seu nome: ");
                String meuNome = teclado.readLine();
                saida.println(meuNome);
                Thread t = new Cliente(conexao);
                t.start();
                String linha;
                
                while (true){
                    if (done){
                        break;
                    }
                    System.out.println("> ");
                    linha = teclado.readLine();
                    saida.println (linha);
                }
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
		
	}
        
        @Override
        public void run(){
            BufferedReader entrada = null;
            try {
                entrada = new BufferedReader (new InputStreamReader(conexao.getInputStream()));
                String linha;
                while (true){
                    linha = entrada.readLine();
                    if (linha.trim().equals("")){
                        System.out.println("Conexao encerrada!!!");
                        break;
                    }
                    System.out.println();
                    System.out.println(linha);
                    System.out.println("...> ");
                }   done = true;
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(entrada != null){
                        entrada.close();
                    }
            
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

}
