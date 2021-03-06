package dataoke;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.IpPoolUtil;
import util.LOG;
import util.SeleniumUtil;

public class ShouQuan {

	static String tname = "luoyuna0905@163.com";
	static String tpwd = "xiaomin0322";
	
	static String taobaoName = "luoyuna0905_163";

	public static void main(String[] args) throws Exception {
	  webDriver =SeleniumUtil.initChromeDriver();
    	webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    	
		File shouquanDir = new File("D:\\dataoke\\shouquan");
		// execute("13283422058", "gk3qhvz",tname, tpwd);
		executeAll(shouquanDir.listFiles());
	}

	public static void executeAll(File... files) throws Exception {
		for (File f : files) {
			executeDeleteHttpClient(f);
		}
	}

	static int count = 0;
	static int tuiguangOk = 0;
	static HttpHost proxy = null;

	
	public static boolean tuijian(String id)throws Exception{
		try{
			try{
				//webDriver.get("http://www.dataoke.com");
				Thread.sleep(Cmd.getSleepTime(1000, 3000));
			}catch(Exception e){
				
			}
			webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			webDriver.get("http://www.dataoke.com/item?id="+id);
			 
			
			Thread.sleep(Cmd.getSleepTime(1000, 3000));
			WebElement element =webDriver.findElement(By.xpath("//*[@class='add-tui J_add_tui']"));
		    element.click();
		    //Thread.sleep(Cmd.getSleepTime());
		    //webDriver.get("http://www.dataoke.com/ucenter/favorites_quan.asp");
		    Thread.sleep(Cmd.getSleepTime(1000, 3000));
		    //Thread.sleep(Cmd.getSleepTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	
	public static boolean zhuan2and1(String id)throws Exception{
		try{
			try{
				//webDriver.get("http://www.dataoke.com");
				Thread.sleep(Cmd.getSleepTime(1000, 3000));
			}catch(Exception e){
				
			}
			webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			webDriver.get("http://www.dataoke.com/item?id="+id);
			 
			
			Thread.sleep(Cmd.getSleepTime(1000, 3000));
			WebElement element =webDriver.findElement(By.xpath("//*[@class='trans-ehy']"));
		    element.click();
		    //Thread.sleep(Cmd.getSleepTime());
		    //webDriver.get("http://www.dataoke.com/ucenter/favorites_quan.asp");
		    Thread.sleep(Cmd.getSleepTime(1000, 3000));
		    //Thread.sleep(Cmd.getSleepTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public static void executeDeleteHttpClient(File file) throws Exception {
		List<String> lists = FileUtils.readLines(file);
		for (String s : lists) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			count++;

			try {
				final HttpHost proxy = IpPoolUtil.getHttpHost();
				Test.proxy = proxy;
				String uname = s.split("\\----")[0].trim();
				String pwd = s.split("\\----")[1].trim();
				System.out.println("u = " + uname + "p = " + pwd + " 开始登陆  当前已刷>>>>>>>>>>>>>>>" + count + "当前 文件名称："
						+ file.getName());

				boolean flag = login(uname, pwd);
				System.out.println("u = " + uname + "登陆>>>>>>>>>>>>>" + flag);
				Thread.sleep(1000);
				if (!isShouquan()) {
					flag = shouquan(uname, pwd, tname, tpwd);
					System.out.println("授权结果>>>>>>>>>>>" + flag);
					if (flag) {
						tuiguangOk += 1;
						flag = Test.loginHttpClient(uname, pwd);
						Thread.sleep(1000);
						flag = Test.tuijianHttpClient("2832996", uname);
						System.out.println("授權成功》》》》》》》》》》》》》》》》》》》 uname=" + uname + " 当前删除成功：" + tuiguangOk
								+ " 当前ip==" + (proxy == null ? "无" : proxy.getHostName()));
						//System.out.println(tuijian("2838197"));
						
					} else {
						FileUtils.write(new File("D:\\dataoke\\shouquan\\fail.txt"), s+"\r\n",true);
						System.out.println("授權失败》》》》》》》》》》》》》》》》》》uname=" + uname);
					}
				}else{
					tuiguangOk += 1;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				proxy = null;
				try{
					webGet("http://www.dataoke.com/logout");
					Thread.sleep(Cmd.getSleepTime(2000, 3000));
				}catch(Exception e){
					
				}
			}
		}
	}
	
	public static void webGet(String url){
		try {
			webDriver.get(url);
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
	        js.executeScript("window.stop();");  
	        System.out.println("已停止加载页面》》》》》》》》》》》》》》》》》》》》》》》》");
		  }
		}

	static WebDriver webDriver = null;
    static{
    	/*	webDriver =SeleniumUtil.initChromeDriver();
    	webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);*/
    }   
	public static void execute(String uname, String pwd, String tuanme, String tpwd) throws Exception {
		boolean flag = login(uname, pwd);
		if (flag) {
			if (!isShouquan()) {
				flag = shouquan(uname, pwd, tuanme, tpwd);
				System.out.println("授权结果>>>>>>>>>>>" + flag);
			}
		}

	}

	public static boolean shouquan(String uname, String pwd, String tuanme, String tpwd) throws Exception {
         try{ 
		webGet("http://www.dataoke.com/ucenter/mypid.asp");

		WebElement element = webDriver.findElement(By.id("update_shouquan"));
		element.click();

		if(webDriver.getPageSource().contains("使用淘宝账号授权")){
			element = webDriver.findElement(By.linkText("使用淘宝账号授权"));
			element.click();
		}
		
		
		Thread.sleep(5000);

		webDriver = webDriver.switchTo().frame("layui-layer-iframe1");

		Thread.sleep(2000);

		
		String str= webDriver.getPageSource();
		
		if(str.contains("检测到您已登录淘宝")){
		System.out.println("检测到您已登录淘宝>>>>>>>>>>>>>>>>");
			//sub 已檢測到登錄
			try{
				element = webDriver.findElement(By.id("sub"));
				if(element!=null && element.isDisplayed()){
					element.click();
				}
			}catch(Exception e){
			 e.printStackTrace();	
			}	
		}else{
		
		webDriver = webDriver.switchTo().frame("J_loginIframe");
		Thread.sleep(2000);
		
		element = webDriver.findElement(By.id("TPL_username_1"));
		element.sendKeys(tuanme);

		Thread.sleep(1000);

		element = webDriver.findElement(By.id("TPL_password_1"));
		element.sendKeys(tpwd);

		Thread.sleep(1000);

		element = webDriver.findElement(By.id("J_SubmitStatic"));
		element.click();
		
		}

		Thread.sleep(5000);

		return isShouquan();
         }catch(Exception e){
        	 e.printStackTrace();
         }
         return false;

	}
	
	public static boolean shouquan2(String uname, String pwd, String tuanme, String tpwd) throws Exception {
        try{ 
		webGet("http://www.dataoke.com/ucenter/mypid.asp");

		if(webDriver.getPageSource().contains(tuanme)){
			LOG.printLog("uname==="+uname+"已经授权");
			return true;
		}
		
		
		WebElement element = webDriver.findElement(By.id("update_shouquan"));
		element.click();

		if(webDriver.getPageSource().contains("使用淘宝账号授权")){
			element = webDriver.findElement(By.linkText("使用淘宝账号授权"));
			element.click();
		}
		
		
		Thread.sleep(5000);

		webDriver = webDriver.switchTo().frame("layui-layer-iframe2");

		Thread.sleep(2000);

		
		String str= webDriver.getPageSource();
		
		if(str.contains("检测到您已登录淘宝")){
		System.out.println("检测到您已登录淘宝>>>>>>>>>>>>>>>>");
			//sub 已檢測到登錄
			try{
				element = webDriver.findElement(By.id("sub"));
				if(element!=null && element.isDisplayed()){
					element.click();
				}
			}catch(Exception e){
			 e.printStackTrace();	
			}	
		}else{
		
		webDriver = webDriver.switchTo().frame("J_loginIframe");
		Thread.sleep(2000);
		
		element = webDriver.findElement(By.id("TPL_username_1"));
		element.clear();
		element.sendKeys(tuanme);

		Thread.sleep(1000);

		element = webDriver.findElement(By.id("TPL_password_1"));
		element.sendKeys(tpwd);

		Thread.sleep(1000);

		WebElement ckE=null;
		try{
			ckE=webDriver.findElement(By.id("nc_1__scale_text"));
		}catch(Exception e){
			
		}
		/*if(webDriver.getPageSource().contains("滑块")|| ckE!=null){
			LOG.printLog("有拖动验证码");
			//System.in.read();
			Thread.sleep(20000);
		}
		*/
		
		element = webDriver.findElement(By.id("J_SubmitStatic"));
		element.click();
		
		}

		Thread.sleep(3000);
		
		
		

		return isShouquan(tuanme);
        }catch(Exception e){
       	 e.printStackTrace();
        }
        return true;

	}

	public static boolean isShouquan() throws Exception {
		return isShouquan(taobaoName);
	}
	public static boolean isShouquan(String taobaoName) throws Exception {
		/*webGet("http://www.dataoke.com/ucenter/mypid.asp");
		Thread.sleep(2000);
		String str = webDriver.getPageSource();
		if (str.contains("你还没有授权")) {
			System.out.println("点击进行授权登录》》》》》》》》》》》》");
			return false;
		} else {
			System.out.println("授权成功>>>>>>>>>>>>>>>>>>>>");
		}*/
		webGet("http://www.dataoke.com/ucenter/mypid.asp");
		Thread.sleep(2000);
		String str = webDriver.getPageSource();
		if (!str.contains(taobaoName)) {
			System.out.println("点击进行授权登录》》》》》》》》》》》》");
			return false;
		} else {
			System.out.println("授权成功>>>>>>>>>>>>>>>>>>>>");
		}
		
		return true;
	}

	public static boolean login(String uname, String pwd) throws Exception {
		try {
			webGet("http://www.dataoke.com/login");
			Thread.sleep(Cmd.getSleepTime(1000, 2000));
			WebElement element = webDriver.findElement(By.xpath("//input[@data-id='email']"));
			element.sendKeys(uname);

			element = webDriver.findElement(By.xpath("//input[@data-id='pwd']"));
			element.sendKeys(pwd);

			element = webDriver.findElement(By.xpath("//a[@class='submit-btn login-btn']"));
			element.click();

			Thread.sleep(Cmd.getSleepTime(3000, 5000));

			String str = webDriver.getPageSource();

			if (str.contains("")) {

			}
			// Thread.sleep(Cmd.getSleepTime());
			// webGet("http://www.dataoke.com/ucenter/favorites_quan.asp");
			// Thread.sleep(Cmd.getSleepTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
