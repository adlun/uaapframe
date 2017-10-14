package com.primeton.uaapframe;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	System.out.println(UUID.randomUUID().toString());
		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    }
}
