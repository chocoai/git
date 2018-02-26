package com.blit.lp.bus.autocode;

import java.io.File;
import java.io.FileOutputStream;

import com.jfinal.kit.JMap;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;

public class BaseRender implements IRender {
	public BaseRender(String tmplPath,RenderTypeEnum type,String fileNameTmpl){
		this.tmplPath = tmplPath;
		this.type=type;
		this.fileNameTmpl = fileNameTmpl;
	}
	protected String tmplPath;
	protected RenderTypeEnum type;
	protected String fileNameTmpl;
	
	@Override
	public String getName() {
		return this.tmplPath;
	}
	
	@Override
	public void render(Engine engine, JMap data) throws Exception {
		if(this.type == RenderTypeEnum.HTML){
			renderHtml(engine, data);
		}
		else if(this.type == RenderTypeEnum.JAVA){
			renderJava(engine, data);
		}
		else if(this.type == RenderTypeEnum.CONSOLE){
			renderConsole(engine, data);
		}
	}
	
	protected void renderHtml(Engine engine, JMap data) throws Exception{
		String dir = data.getStr("dir");
		
		String filepath = engine.getTemplateByString(this.fileNameTmpl).renderToString(data);
		String destPath = dir + "/WebRoot/view" + filepath;
		
		String viewPathName = "";
		for (int i=0; i < 10; i++) {
			viewPathName = "viewPath" + String.valueOf(i);
			if(!data.containsKey(viewPathName)){
				break;
			}
		}
		data.set(viewPathName, filepath);
		
		Template template = engine.getTemplate(this.tmplPath);
		String txt = template.renderToString(data);
		renderFile(txt,destPath);
	}
	
	protected void renderJava(Engine engine, JMap data) throws Exception{
		String dir = data.getStr("dir");
		
		String filepath = engine.getTemplateByString(this.fileNameTmpl).renderToString(data);
		String destPath = dir + "/src" + filepath;

		Template template = engine.getTemplate(this.tmplPath);
		String txt = template.renderToString(data);
		renderFile(txt,destPath);
	}

	protected void renderConsole(Engine engine, JMap data){
		Template template = engine.getTemplate(this.tmplPath);
		String txt = template.renderToString(data);
		System.out.println("\r\r" + txt);
	}
	
	private static void renderFile(String txt,String destPath) throws Exception {
		File destFile=new File(destPath);
		
		File fileDir = destFile.getParentFile();
		if(!fileDir.exists())
			makeDir(fileDir);
     
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
            out.write(txt.getBytes());
            out.close();
		}
		finally{
			if(out != null)
				out.close();
		}
	}
	
	private static void makeDir(File dir){
		File pFile = dir.getParentFile();
		if(!pFile.exists())
			makeDir(pFile);
		
		if(!dir.exists())
			dir.mkdir();
	}
}
