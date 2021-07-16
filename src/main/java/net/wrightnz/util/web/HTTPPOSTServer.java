package net.wrightnz.util.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;


public class HTTPPOSTServer {
 
    public HTTPPOSTServer() {
    }            

    public void read(InputStream inputStream) throws IOException {
        String filename = null;
        PrintWriter fout = null;

        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(inputStream));

        String currentLine = inFromClient.readLine();
        String headerLine = currentLine;
        StringTokenizer tokenizer = new StringTokenizer(headerLine);
        String httpMethod = tokenizer.nextToken();

        System.out.println(currentLine);

        if(httpMethod.equals("POST")){ //POST request
            System.out.println("POST request");

            while(inFromClient.ready()){
                currentLine = inFromClient.readLine();

                if (currentLine.indexOf("Content-Type: multipart/form-data") != -1) {
                    String boundary = currentLine.split("boundary=")[1];// The POST boundary                           

                    String contentLength = null;
                    
                    while (true) {
                        currentLine = inFromClient.readLine();
                        if (currentLine.indexOf("Content-Length:") != -1) {
                            contentLength = currentLine.split(" ")[1];
                            System.out.println("Content Length = " + contentLength);
                        }
                        //Content length should be < 2MB
                        if (contentLength != null && Long.valueOf(contentLength) > 2000000L) {
                            throw new IOException("File size should be < 2MB");
                        }
                        
                        if (currentLine.contains("--" + boundary)) {
                            filename = inFromClient.readLine().split("filename=")[1].replaceAll("\"", "");
                            String[] filelist = filename.split("\\" + File.separator);
                            filename = filelist[filelist.length - 1];
                            System.out.println("File to be uploaded = " + filename);
                            break;
                        }
                    }

                    String fileContentType = inFromClient.readLine().split(" ")[1];
                    System.out.println("File content type = " + fileContentType);

                    inFromClient.readLine();

                    fout = new PrintWriter(filename);
                    String prevLine = inFromClient.readLine();
                    currentLine = inFromClient.readLine();

                    //Here we upload the actual file contents
                    while (true) {
                        if (currentLine.equals("--" + boundary + "--")) {
                            fout.print(prevLine);
                            break;
                        } else {
                            fout.println(prevLine);
                        }
                        prevLine = currentLine;
                        currentLine = inFromClient.readLine();
                    }
                    fout.close();
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {

        ServerSocket Server = new ServerSocket(6123);
        System.out.println("HTTP Server Waiting for client on port 6123");
        
        Socket socket = Server.accept();
        HTTPPOSTServer server = new HTTPPOSTServer();
        server.read(socket.getInputStream());
    }
}

