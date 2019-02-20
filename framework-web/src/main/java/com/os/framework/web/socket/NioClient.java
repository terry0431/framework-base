package com.os.framework.web.socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 *
 * @author wangbo
 */
public class NioClient {

	private static Socket s;

	public static void main(String[] args) throws UnknownHostException, IOException {

		s = new Socket("localhost", 10001);

		System.out.println("与服务器连接成功");

		Scanner input = new Scanner(System.in);

		PrintStream ps = new PrintStream(new BufferedOutputStream(s.getOutputStream()));

		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

		boolean flag = true;

		while (flag) {

			System.out.println("输入消息");

			String info = input.next();

			if ("bye".equals(info)) {

				flag = false;

			} else {

				ps.println(info);

				ps.flush();

				System.out.println(br.readLine());

			}

		}

		br.close();

		ps.close();

	}
}
