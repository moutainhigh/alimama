package dataoke;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import jodd.http.Cookie;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.SeleniumUtil;

public class ZhuCeSelenium {

	public static void main(String[] args)throws Exception {
		browser = new HttpBrowser();
	    executeAll();
	}
	
	static WebDriver webDriver = null;
	
	static{
		
	}
	static String tnum = "";
	
	public static void executeAll()throws Exception{

		 Ma60.login();
		 for(int i=0;i<10000;i++){
			 try{
				 execute();
			 }catch(Exception e){
				 e.printStackTrace();
			 }finally{
				 try{
					 System.out.println("释放手机号码");
					 Ma60.resleNum(tnum);
				 }catch(Exception e){
					 e.printStackTrace();
					 Thread.sleep(2000);
				 }
			 }
		 }
	}
	static int okSize = 0;
	static File out = new File("d:\\dataokeuser1.txt");
	public static void execute()throws Exception{
		if(webDriver==null){
			webDriver = SeleniumUtil.initChromeDriver();
		}
		 webDriver.manage().window().maximize();
		 webDriver.get("http://www.dataoke.com/login/?user=reg");
		 String num = Ma60.getnum();
		 tnum = num;
		 if(StringUtils.isBlank(num)){
			 System.out.println("获取手机号码有问题》》》》》》》》》》》》》》");
		 }else if(num.contains("余额不足")){
			 System.out.println("余额不足退出程序>>>>>>>>>>>>>>>>>>>>>>>>>.");
			 System.exit(0);
		 }
		 
		System.out.println("开始输入手机号码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); 
		WebElement webElement = webDriver.findElement(By.id("phone"));
		webElement.click();
	    webElement.sendKeys(num);
	    Thread.sleep(200);
	    System.out.println("点击获取短信按钮》》》》》》》》》》》》》》》》》》》");
	    webElement = webDriver.findElement(By.xpath("//button[@class='get-phone-verify get-phone-verify-fn']"));
		webElement.click();
		Thread.sleep(200);
		
		   // 选取frame  
		webDriver.switchTo().frame("captcha_widget");;  
       
		System.out.println("点击人机识别验证》》》》》》》》》》》》》》》》》》》");
		webElement = webDriver.findElement(By.xpath("//span[@class='captcha-widget-text']"));
		webElement.click();
		Thread.sleep(200);
		
		 // 跳出iframe  
		webDriver.switchTo().defaultContent();  
		
	    
	    System.out.println("开始手动输入验证码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>停顿15秒");
	    for(int i=0;i<15;i++){
	    	System.out.println("已经休息>>>>>>>>>>>>>"+i);
	    	Thread.sleep(1000);
	    	/*String pageSource = webDriver.getPageSource();
	    	if(pageSource.contains("注册数量超限")){
	    		 Ma60.jiaheiNum();
	    		 return ;
	    	}*/
	    }
	    //System.in.read();
	    
	    String code = "";
		 System.out.println("获取短信内容");
		 for(int i=0;i<10;i++){
			 String str = Ma60.getmsg();
			 if(StringUtils.isBlank(str)){
				 Thread.sleep(4000);
				 continue;
			 }else{
				 if(str.contains("大淘客")){
					 code = str.replace("【大淘客】您的验证码是", "").replace("，请于5分钟内正确输入", "");
					 break;
				 }
			 }
		 }
		 System.out.println("验证码为："+code);
		 
		 if(StringUtils.isNotBlank(code)){
			 
			 webElement = webDriver.findElement(By.xpath("//input[@id='code']"));
			 webElement.click();
			 webElement.sendKeys(code);
			 Thread.sleep(200);
			 
			 int size = new Random().nextInt(4);
			 String pwd =  new PassWordCreate().createPassWord(6+size);
			 webElement = webDriver.findElement(By.xpath("//input[@data-id='pwd']"));
			 webElement.click();
			 webElement.sendKeys(pwd);
			 Thread.sleep(200);
			 webElement = webDriver.findElement(By.xpath("//input[@data-id='repwd']"));
			 webElement.click();
			 webElement.sendKeys(pwd);
			 Thread.sleep(200);
			 
			 webElement = webDriver.findElement(By.xpath("//a[@class='submit-btn register-btn']"));
			 webElement.click();
			 System.out.println("注册成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			 
			 okSize +=1;
			 FileUtils.write(out, num+"----"+pwd+"\r\n",true);
			 
			 Thread.sleep(2000);
		 }else{
			 System.out.println("验证码为null>>>>>>>>>>>>>>>>>>>>>");
			 Ma60.jiaheiNum();
		 }
	    
	    //webDriver.close();
	}
	
	static HttpBrowser browser = null;
	
	public static boolean reg(String username,String password,String code)throws Exception{
		 String url = "http://www.dataoke.com/login?user=reg";
		 HttpRequest httpRequest =HttpRequest.post(url).timeout(10000);
		 httpRequest.header("Host", "www.dataoke.com");
		 httpRequest.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
		 httpRequest.header("Referer", "http://www.dataoke.com/login?user=reg");
		 httpRequest.header("Connection", "keep-alive");
		 httpRequest.header("X-Requested-With", "XMLHttpRequest");
		 
		 httpRequest.form("username", username);
		 httpRequest.form("password", password);
		 httpRequest.form("code", code);
		 
		 HttpResponse response = browser.sendRequest(httpRequest);
		 
		 String rc = response.body();
		 System.out.println(rc);
		 
		 if(rc.contains("1")){
			System.out.println("注册成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			return true;
		 }else{
			 System.out.println("注册失败>>>>>>>>>>>>>>>>>>>>>>>>");
			 return false;
		 }
	}
	
	public static boolean execute(String id,String uname)throws Exception{
		/*if(webDriver==null){
			webDriver = SeleniumUtil.initChromeDriver();
		}*/
		
		/* List<HttpHost> hosts = IpUtils.getips("http://ip.memories1999.com/api.php?dh=2764810913906166&sl=1&xl=%E5%9B%BD%E5%86%85&gl=1");
		 if(CollectionUtils.isEmpty(hosts)){
			 System.out.println("获取ip为kong>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			 Thread.sleep(5000);
			 return false;
		 }else{
			 System.out.println("获取代理ip成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ip="+hosts.get(0).getHostName()+" prot:"+hosts.get(0).getPort());
		 }
		HttpHost host = hosts.get(0); */
		
		//http://ip.memories1999.com/api.php?dh=2764810913906166&sl=1&xl=%E5%9B%BD%E5%86%85&gl=1
		
		webDriver = SeleniumUtil.initChromeDriver();
		try{
			webDriver.get("http://www.dataoke.com/item?id="+id);
			setCookis(uname, webDriver);
			webDriver.get("http://www.dataoke.com/item?id="+id);
			Thread.sleep(Cmd.getSleepTime());
			WebElement element =webDriver.findElement(By.xpath("//*[@class='add-tui J_add_tui']"));
		    element.click();
		    Thread.sleep(Cmd.getSleepTime());
		    webDriver.get("http://www.dataoke.com/ucenter/favorites_quan.asp");
		    Thread.sleep(Cmd.getSleepTime());
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			webDriver.close();
		}
		return true;
	}
	
	
	public static void setCookis(String uname,WebDriver webDriver)throws Exception{
		Map<String, String> cookisMap = new HashMap<String, String>();
		String cookisStr = "random=8696; ASPSESSIONIDSQCRRSDT=PMFGMKPANNDIPLFIEAGFOJHD; dtk_web=mgbpf1uvaohssnvee7m02u1lt7; UM_distinctid=15b906fc3d99a-0ca45cd09b0c9d-12616a4a-1fa400-15b906fc3db105; CNZZDATA1257179126=1538129784-1492772062-http%253A%252F%252Fwww.dataoke.com%252F%7C1492777462; userid=537000; user_email=15201733860; user%5Femail=15201733860; upe=537e2926; e88a8013345a8f05461081898691958c=834b4337570611838d9b6989521575fb85ae30b6a%3A4%3A%7Bi%3A0%3Bs%3A6%3A%22537000%22%3Bi%3A1%3Bs%3A11%3A%2215201733860%22%3Bi%3A2%3Bi%3A2592000%3Bi%3A3%3Ba%3A0%3A%7B%7D%7D; ASPSESSIONIDSSBQSTCT=ICEPOLPACLKKGLDMHNNFFFIA; ASPSESSIONIDQSCRRTDS=CACEBLPAJEAMCMJMGPHFAEOB; ASPSESSIONIDSQCTQTCS=HNCMFMPAEKHOCBIEFGDHDDLH; ASPSESSIONIDQQCTRTCS=OMKMBNPAOFLEBJBEGOKDNIIF; ASPSESSIONIDQSBTQSCS=OHDGKNPACFIHDDFNANILEPKF; token=883f5a1f6733a23954015a6672a70099; ASPSESSIONIDSQBQSSDT=KNFMMBABCBPCEFDLDGGAGLJO; ASPSESSIONIDQSASQTDT=CDAFIBABKOLMLCOGGMEINGBM";
		for (String str : cookisStr.split("\\;")) {
			cookisMap.put(str.split("\\=")[0].trim(), str.split("\\=")[1]);
		}

		for (Cookie c : Test.getObjToFile(uname)) {
			// System.out.println(c.getName()+"===="+c.getValue());
			// buffer.append(c.getName()).append("=").append(c.getValue()).append("; ");
			cookisMap.put(c.getName().trim(), c.getValue());
		}

		StringBuffer buffer = new StringBuffer();

		for (Entry<String, String> en : cookisMap.entrySet()) {
			buffer.append(en.getKey()).append("=").append(en.getValue())
					.append("; ");
			
			org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie(en.getKey().trim(),en.getValue());
					
			webDriver.manage().addCookie(cookie);
					
		}

		//System.out.println("====="+buffer.toString());

	}

}