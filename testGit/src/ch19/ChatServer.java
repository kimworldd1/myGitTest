package ch19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	public static void main(String[] args) throws Exception {
		//발신용 스트림에 빨간줄 생심 add throws 하면 여기에 IOException 생김 -> Exception으로 변경
		//서버쪽 ip주소를 알면 원격으로 채팅 가능
		//서버용 소켓
		ServerSocket serverSocket = null;
		try {
			//서비스를 하기 위한 포트 개방(지정)
			serverSocket = new ServerSocket(5555);//포트번호 5555
			System.out.println("서비스가 시작되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			//네트워크 프로그램은 다양한 변수상황이 있음.
			System.out.println("서비스를 시작할 수 없습니다.");
			System.exit(1);//비정상적 종료. 1 or -1
		}
		
		//일반소켓(클라이언트)
		Socket clientSocket = null;
		try {
			//클라이언트의 접속을 기다렸다가, 접속 시 소켓을 연결하기 위해 접근필요
			clientSocket = serverSocket.accept();
			System.out.println("클라이언트의 ip : "+ clientSocket.getInetAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//발신용 스트림
		PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
		//수신용 스트림(BufferedReader 대량)
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String receive = "";
		String send = "Welcome!!!";
		
		writer.println(send);//메시지 보내기
		Scanner sc = new Scanner(System.in);
		while(true) {
			receive = reader.readLine();//한 라인을 읽음
			if(receive == null || receive.equals("quit")) break; //종료조건
		
			System.out.println("[client] " + receive);
			System.out.println("서버님 입력하세요(종료: quit) : ");
			send = sc.nextLine();
			writer.println(send);//메시지 보내기
			if(send.equals("quit")) break;
		}
		//리소스 정리
		sc.close();
		writer.close();
		reader.close();
		clientSocket.close();
		serverSocket.close();
	}//end main	
}
