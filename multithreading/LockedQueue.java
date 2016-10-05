package multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedQueue<T>
{
	private final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	private final T[] items;
	private int tail;
	private int head;
	private int count;
	
	@SuppressWarnings("unchecked")
	public LockedQueue( int capacity )
	{
		items = ( T[] ) new Object[capacity];
	}
	
	public void enq( T x ) throws InterruptedException
	{
		try
		{
			lock.lock();
			while( count == items.length )
			{
				notFull.await();
			}
			items[tail++] = x;
			if ( tail == items.length )
			{
				tail = 0;
			}
			count++;
			notEmpty.signal();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public T deq() throws InterruptedException
	{
		try
		{
			while ( count == 0 )
			{
				notEmpty.await();
			}
			T x = items[head++];
			if ( head == items.length )
			{
				head = 0;
			}
			count--;
			notFull.signal();
			return x;			
		}
		finally
		{
			lock.unlock();
		}
	}
}
