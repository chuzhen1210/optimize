package com.thread.pool;

/**
 * ����һ�������˳����߳�
 * �����߳����岿����һ������ѭ�������߳����ֶ��ر�ǰ��������
 * @author chuzhen
 *
 */
public class PThread extends Thread {

	//�̳߳�
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
		//����run�е�wait������ѭ����������
		notifyAll();
	}

	public synchronized void setTarget(Runnable target) {
		this.target = target;
		//����run�е�wait��ִ��ѭ����
		notifyAll();
	}
	
	@Override
	public void run() {
		//ֻҪ���߳�û�ֶ��رգ���һֱ������
		while(!shutdown) {
			if(target != null) {
				target.run();
			}
			
			try {
				//����ִ�н����󣬽������״̬�����ر��̣߳����Ƿ����̳߳صĿ��ж���
				pool.repool(this);
				
				//�߳̿���ʱ���ȴ��µ�������
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
