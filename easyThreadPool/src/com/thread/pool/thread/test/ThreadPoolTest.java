package com.thread.pool.thread.test;

import com.thread.pool.ThreadPool;
import com.thread.pool.thread.MyThread;

public class ThreadPoolTest {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		testThreadPool();
		
		long end = System.currentTimeMillis();
		System.out.println((end - start));
	}
	
	private static void testThreadPool() {
		ThreadPool threadPool = ThreadPool.getInstance();
		for(int i = 0; i < 2; i++) {
			threadPool.start(new MyThread("NoThreadPool#" + i));
		}
		
		try {
			Thread.sleep(100);
			threadPool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void testNoThreadPool() {
		for(int i = 0; i < 100; i++) {
			new Thread(new MyThread("NoThreadPool" + i)).start();
		}
	}
}
