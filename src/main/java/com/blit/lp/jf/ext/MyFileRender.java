package com.blit.lp.jf.ext;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.JFinal;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class MyFileRender extends Render {

    private File file;
    private ServletContext servletContext;
    private String DEFAULT_FILE_CONTENT_TYPE ="application/octet-stream";
    private String showName;
    public MyFileRender(String filePath) {
        File oldFile=new File(filePath);
        this.file = oldFile;
        this.servletContext =  JFinal.me().getServletContext();
    }
    
    /**
     * 此构造方法为解决下载文件时，文件名乱码准备。（主要是IE浏览器）<br>
     * @param filePath
     * @param response
     */
    public MyFileRender(String filePath, String showName, HttpServletResponse response) {
    	this.response = response;
        File oldFile=new File(filePath);
        this.file = oldFile;
        this.servletContext =  JFinal.me().getServletContext();
        this.showName = showName;
    }
    
    @Override
    public void render() {
        if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
        	throw new RuntimeException("下载文件丢失...");
        }
        //response.addHeader("Content-disposition", "attachment; filename=" + file.getName());
        //修改后的代码 解决中文乱码问题
        try {
            response.addHeader("Content-disposition", 
                "attachment; filename=" + new String(showName.getBytes("GBK"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String contentType = servletContext.getMimeType(file.getName());
        if (contentType == null) {
            contentType = DEFAULT_FILE_CONTENT_TYPE;        // "application/octet-stream";
        }
        
        response.setContentType(contentType);
        response.setContentLength((int)file.length());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        }
        catch (Exception e) {
            throw new RenderException(e);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }   
}
