package dataoke;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class CmdTestSelenium {
	
     static Map<String,File> map = new HashMap<String, File>();
    static File base = new File("D:\\dataoke\\cks");
	static{
		for(File f:base.listFiles()){
			map.put(f.getName().replace(".txt", ""), f);
		}
	}
	
	public static int min = 3000;
	
	public static int max = 10000;
	
	public static int getSleepTime(){
	   return getSleepTime(min, max);
	}
	
	public static int getSleepTime(int min,int max){
		Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println("获取随机时间为："+s);
        return s;
	}
	
	public static void main(String[] args) throws Exception{
		
		//Thread.sleep(1000 * 60 * 60);
		//args = new String[]{"2882511,2895769","103","5000,20000","1"}; 
		//args = new String[]{"2947807","106","5000,30000","1"};
		
		//args = new String[]{"4021765,4041550","1","50,100","1"};
		
		
		String pids = args[0];
		String fileIds = args[1];
		
		if(args.length > 2){
			System.out.println("时间参数为>>>>>>>(单位毫秒)>>>>>>>>>>"+args[2]);
			Cmd.min = Integer.parseInt(args[2].split(",")[0].trim());
			Cmd.max = Integer.parseInt(args[2].split(",")[1].trim());
		}
		
		if(args.length > 3){
			Integer sleep = Integer.parseInt(args[3].trim());
			System.out.println("睡眠 ："+sleep+"秒 在执行程序>>>>>>>>>>>>>>>");
			try {
				Thread.sleep(1000 * sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		execute(pids, fileIds);
		
		//System.out.println(getSleepTime(1000, 6000));
		
		//getFiles("7,8");
	}
	
	
	public static void execute(String pids,String fileIds){
		System.out.println("输入的商品id==="+pids);
		
		System.out.println("文件id==="+fileIds);
		
		System.out.println("开始验证>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if(Test.check()){
			try {
				TestSelenium.shouquanAndTuiGuang(pids.split(","),getFiles(fileIds));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static File[]  getFiles(String fileIds){
		//fileIds.split(",").length
		File[] files = new File[]{};
		if(StringUtils.isNotBlank(fileIds)){
			for(String id:fileIds.split(",")){
				if(map.containsKey(id)){
					File f = map.get(id);
					if(f!=null){
						files=(File[])ArrayUtils.add(files, f);
					}
				}
			}
		}
		for(File f:files){
			if(f!=null){
				System.out.println("1111输入的文件名称>>>>>>>>>>>>>>"+f.getAbsolutePath());
			}
		}
		return files;
	}
	
	

}
