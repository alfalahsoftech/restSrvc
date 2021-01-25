package com.alfalahsoftech.base;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.alfalahsoftech.controller.CRUDOperationTest;
import com.alfalahsoftech.controller.FileUploadcontroller;
import com.example.MyResource;

@ApplicationPath("/rest")
public class AFApplicationBase extends ResourceConfig{
	
	public  AFApplicationBase(){
		System.out.println("AFApplicationBase.getClasses()");
		register(CRUDOperationTest.class);
		register(MultiPartFeature.class);
		register(MyResource.class);
		register(FileUploadcontroller.class);
		register(FileHandlingException.class);
		
	}
	
	@Context
    public void setServletContext(ServletContext context)
    {
        if (context != null)
        {
        	System.out.println("ContextPath=>>>> "+ context.getContextPath());
           // context.getInitParameter("system.info.allow");
        }
    }

}
