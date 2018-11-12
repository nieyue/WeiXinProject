package com.nieyue;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
       System.out.println(22);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() 
    {
        assertTrue( true );
    }
    
    public static void aaa(String a){
    	List<String> list=new CopyOnWriteArrayList<String>();
		list.add(a);
		list.add(a+"2");
		list.add(a+"3");
		list.forEach(e->{
			System.out.println(e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		});
    }
    public static void main (String[] args) {
    	System.out.println(111);
    	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    	//ExecutorService singleThreadExecutor = Executors.newFixedThreadPool(5);
    	singleThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				aaa("a");
				singleThreadExecutor.shutdown();
			}
		});
    	singleThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				aaa("b");
				//singleThreadExecutor.shutdown();
			}
		});
    	System.out.println(222);
    	
	}
}
