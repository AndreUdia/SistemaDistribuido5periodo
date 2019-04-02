/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1;

/**
 *
 * @author andre
 */

//Aqui fica as declarações

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class Servidor extends Thread{
    
    //aqui temos a declaração dos atributos estáticos e de instâncias da classe servidor.java
    
    private static ArrayList<BufferedWriter>clientes;           
    private static ServerSocket server; 
    private String nome;
    private Socket con;
    private InputStream in;  
    private InputStreamReader inr;  
    private BufferedReader bfr;
    

    //Método construtor 

    public Servidor(Socket con){
        this.con = con;
         try {
                in  = con.getInputStream();
                inr = new InputStreamReader(in);
                bfr = new BufferedReader(inr);
        } catch (IOException e) {
          e.printStackTrace();
        }                          
    }
    
    //Depois vem o método RUN
    
    public void run(){
        try{
            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw); 
            clientes.add(bfw);
            nome = msg = bfr.readLine();
               
            while(!"Sair".equalsIgnoreCase(msg) && msg != null){
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);
                writeUsingFiles(server.getLocalPort() + '@' + server.getInetAddress().toString() + '@' + bfw.toString()+ '@' + ou.toString()+ '@' +  msg); //
                // aqui ele escreve no documento do texto os dados que preciso.
            }
                                      
        }catch (Exception e) {
            e.printStackTrace();
        }                       
    }
    
    // Em seguida vem o método sendToAll
    
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException{ 

        BufferedWriter bwS;
        for(BufferedWriter bw : clientes){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(nome + " -> " + msg+"\r\n");
                bw.flush(); 
            }
        }          
    }
    
    // O método main então é criado para iniciar o servido na porta de sua escolha.
    public static void main(String []args) {
    
        try{
            //Cria os objetos necessário para instânciar o servidor
            JLabel lblMessage = new JLabel("Porta do Servidor:");
            JTextField txtPorta = new JTextField("2000");
            Object[] texts = {lblMessage, txtPorta };  
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+ txtPorta.getText());
    
            while(true){
                System.out.println("Aguardando conexão...");
                Socket con = server.accept();
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con);
                t.start();   
            }
                              
        }catch (Exception e) {
           e.printStackTrace();
        }                       
    }     
    
    //Função para escrever dados
    
    private static void writeUsingFiles(String data) {
        try {
            Files.write(Paths.get("c:/dados/files.txt"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
