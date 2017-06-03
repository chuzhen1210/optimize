package com.thread.pool;

import java.util.List;
import java.util.Vector;

public class ThreadPool {

	private static ThreadPool instance = null;
	
	//�����̶߳���
	private List<PThread> idleThreads;
	//�̳߳��Ƿ��ѹر�
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
	 * ���̷߳����̳߳�
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
	 * ֹͣ�̳߳��������߳�
	 */
	public synchronized void shutdown() {
		for (int i = 0; i < idleThreads.size(); i++) {
			PThread pThread = idleThreads.get(i);
			pThread.shutdown();
		}
		shutdown = true;
	}
	
	/**
	 * ִ������
	 * @param target
	 */
	public synchronized void start(Runnable target) {
		//û�п����̣߳��򴴽����߳�
		if(idleThreads.isEmpty()) {
			PThread pThread = new PThread("PThread#" + threadCounter, instance, target);
			threadCounter ++;
			pThread.start();
		} else {//�п����̣߳���ֱ��ʹ��
			int last = idleThreads.size() - 1;
			PThread pThread = idleThreads.remove(last);
			//����ִ���������
			pThread.setTarget(target);
		}
	}
}
