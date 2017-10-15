package com.primeton.uaapframe;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    }
}
