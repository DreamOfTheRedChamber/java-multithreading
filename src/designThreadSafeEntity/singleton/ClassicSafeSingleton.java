package designThreadSafeEntity.singleton;

/**
 * 
 * Not threadsafe
 */

public class ClassicSafeSingleton
{
	// define an instance field
	private static ClassicSafeSingleton instance;

	// make constructure private
	private ClassicSafeSingleton(){}
	
	// use static method to return instance
	public static ClassicSafeSingleton getInstance()
	{
		// lazy initialization
		if ( instance == null )
		{
			instance = new ClassicSafeSingleton();
		}
		return instance;
	}
	
}
