package com.thread.pool.thread;

public class MyThread implements Runnable {

	private String name;
	
	public MyThread(String name) {
		super();
		this.name = name;
	}

	public void run() {
//		try {
//			Thread.sleep(100);//代替一个具体功能的执行
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public String getName() {
		return name;
	}
}
