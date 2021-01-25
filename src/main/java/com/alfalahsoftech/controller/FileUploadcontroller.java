package com.alfalahsoftech.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.alfalahsoftech.base.AFBaseObject;
import com.alfalahsoftech.base.FileHandlingException;

@Path("/fileHandler")
public class FileUploadcontroller extends AFBaseObject {
	@Context SecurityContext secCtxt;

	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
//	public Response upploadFile(@Context HttpServletRequest servletReq,@FormDataParam("isNew") String isNew,@FormDataParam("destDir") String dest,  @FormDataParam("file") InputStream inStream, @FormDataParam("file") FormDataContentDisposition fileMetaData) {
	public Response upploadFile(@Context HttpServletRequest servletReq,@BeanParam MultiPartForm multiPartForm) {
		afDebug("Destination: "+multiPartForm.getDestDir());
		afDebug("Is New: "+ multiPartForm.getIsNew());
		afDebug("getFile: "+multiPartForm.getFile());

		afDebug(secCtxt.getAuthenticationScheme());
		afDebug("servletReq== "+servletReq.getRealPath("/"));

		String appPath =servletReq.getRealPath("/");
		java.nio.file.Path p =Paths.get(appPath);
		uploadFileAt(p, multiPartForm.getFile(), multiPartForm.getFileMetaData());


		return Response.ok("{\"response\":File uploaded scuccessfully!}").build();
	}
	private void uploadFileAt(java.nio.file.Path p, InputStream inStream, FormDataContentDisposition fileMetaData) {

		AtomicReference<String> filePath = new AtomicReference<>();
		String fileDirLocation = getFileOrDirLocation(fileMetaData.getFileName(),p, filePath);
		System.out.println("fileDirLocation:   "+ fileDirLocation);
		File serverFile = new File(fileDirLocation);
		System.out.println("File goin to be replaced at: "+serverFile.getAbsolutePath());
		
		try(BufferedInputStream bfreader = new BufferedInputStream((inStream));
				BufferedOutputStream bfwriter = new BufferedOutputStream(new FileOutputStream(serverFile));) {

			int line = bfreader.read();
			while(line != -1) {
			
				bfwriter.write(line);
				line = bfreader.read();
			}

			System.out.println("Server File Location=" + serverFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*try(BufferedReader bfreader = new BufferedReader(new InputStreamReader(inStream));
				BufferedWriter bfwriter = new BufferedWriter(new FileWriter(serverFile));) {

			String line = bfreader.readLine();
			while(line != null) {
				if(line.trim().startsWith("package")) {
					System.out.println("Folder name of file: " + line.substring(line.indexOf("package")+1));
				}
				bfwriter.write(line);
				line = bfreader.readLine();
			}

			System.out.println("Server File Location=" + serverFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	private static String getFileOrDirLocation(String fileName, java.nio.file.Path path, AtomicReference<String> filePath) {
		try {
			Files.list(path).
			forEach(e->{
				System.out.println(e);
				if(e.endsWith(fileName)) {
					filePath.getAndSet(e.toString());
					System.out.println("  File has been at path: " + filePath);
					return ;
				}
				if(Files.isDirectory(e)) {
					System.out.println("Directory");
					getFileOrDirLocation(fileName, e,filePath);
				}
			});	
			System.out.println("filePath 11= "+filePath.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath.get();
	}
	@GET
	@Path("/downloadFile/{fileName}")
	public Response downloadFile(@PathParam("fileName") String fileName) throws FileHandlingException {
		System.out.println("sssssssssssssssssssssssssssssssssssssssss");
		if(!fileName.equals("query.sql")) {
			throw new FileHandlingException();
		}

		return Response.status(Status.FOUND).build();
	}


}
