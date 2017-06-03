package com.thread.pool;

/**
 * 这是一个永不退出的线程
 * 它的线程主体部分是一个无限循环，该线程在手动关闭前永不结束
 * @author chuzhen
 *
 */
public class PThread extends Thread {

	//线程池
	private ThreadPool pool;
	private Runnable target;
	private boolean shutdown = false;
	
	PThread(String name, ThreadPool pool, Runnable target) {
		super(name);
		this.pool = pool;
		this.target = target;
	}

	public synchronized void shutdown() {
		this.shutdown = true;
		//唤醒run中的wait，但是循环主体会结束
		notifyAll();
	}

	public synchronized void setTarget(Runnable target) {
		this.target = target;
		//唤醒run中的wait，执行循环体
		notifyAll();
	}
	
	@Override
	public void run() {
		//只要该线程没手动关闭，则一直不结束
		while(!shutdown) {
			if(target != null) {
				target.run();
			}
			
			try {
				//任务执行结束后，进入空闲状态，不关闭线程，而是放入线程池的空闲队列
				pool.repool(this);
				
				//线程空闲时，等待新的任务到来
				synchronized(this) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("over");
	}

}
