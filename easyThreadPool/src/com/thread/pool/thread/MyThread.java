package com.thread.pool.thread;

public class MyThread implements Runnable {

	private String name;
	
	public MyThread(String name) {
		super();
		this.name = name;
	}

	public void run() {
//		try {
//			Thread.sleep(100);//����һ�����幦�ܵ�ִ��
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public String getName() {
		return name;
	}
}
