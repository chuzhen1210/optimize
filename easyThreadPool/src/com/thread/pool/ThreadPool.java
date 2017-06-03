package com.thread.pool;

import java.util.List;
import java.util.Vector;

public class ThreadPool {

	private static ThreadPool instance = null;
	
	//空闲线程队列
	private List<PThread> idleThreads;
	//线程池是否已关闭
	private boolean shutdown = false;

	private int threadCounter;
	
	private ThreadPool() {
		super();
		this.idleThreads = new Vector<PThread>(5);
	}
	
	public static ThreadPool getInstance() {
		if(instance == null) {
			synchronized (ThreadPool.class) {
				if(instance == null) {
					instance = new ThreadPool();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * 将线程放入线程池
	 * @param repoolingThread
	 */
	protected synchronized void repool(PThread repoolingThread) {
		if(!shutdown) {
			idleThreads.add(repoolingThread);
		} else {
			repoolingThread.shutdown();
		}
	}
	
	/**
	 * 停止线程池中所有线程
	 */
	public synchronized void shutdown() {
		for (int i = 0; i < idleThreads.size(); i++) {
			PThread pThread = idleThreads.get(i);
			pThread.shutdown();
		}
		shutdown = true;
	}
	
	/**
	 * 执行任务
	 * @param target
	 */
	public synchronized void start(Runnable target) {
		//没有空闲线程，则创建新线程
		if(idleThreads.isEmpty()) {
			PThread pThread = new PThread("PThread#" + threadCounter, instance, target);
			threadCounter ++;
			pThread.start();
		} else {//有空闲线程，则直接使用
			int last = idleThreads.size() - 1;
			PThread pThread = idleThreads.remove(last);
			//立即执行这个任务
			pThread.setTarget(target);
		}
	}
}
