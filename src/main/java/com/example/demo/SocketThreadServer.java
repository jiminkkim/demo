package com.example.demo;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@NotNull
public class SocketThreadServer extends Thread {
    private static final Logger logger = Logger.getLogger(SocketThreadServer.class);

    private BufferedReader br;
    private PrintWriter pw;
    private Socket socket;
    public SocketThreadServer(Socket socket) { //소켓을 받아준다.
        this.socket=socket;
    }
    //단순 문자열 Thread server
    public void run(){
        try{
            String connIp = socket.getInetAddress().getHostAddress();
            System.out.println(connIp + "에서 연결 시도.");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = null;
            while ((input = br.readLine()) != null) {
                /*
                 * 접근한 소켓 계정의 ip를 체크한다. KTOA 연동 모듈인지 체크
                 * 정상이면 먼저 정상 접근되었음을 알린다.
                 **/

                pw = new PrintWriter(socket.getOutputStream());
                // 클라이언트에서 보낸 문자열 출력
                System.out.println(input);
                logger.info("[From " + connIp + "] " + input);
                // 클라이언트에 문자열 전송
                pw.println("수신Ok");
                pw.flush();
            }
        } catch(IOException e){
            logger.error(e);
        } finally{
            try{
                if(pw != null) { pw.close();}
                if(br != null) { br.close();}
                if(socket != null){socket.close();}
            } catch(IOException e){
                logger.error(e);
            }
        }
    }
}

