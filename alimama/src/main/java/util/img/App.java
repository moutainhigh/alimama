package util.img;

public class App {

	public static void main(String[] args) throws Exception {
		
		//生成带logo 的二维码 
		//String text = "https://s.click.taobao.com/3SgJ3iw";
		//QRCodeUtil.encode(text, "11.JPG", "d:/dataoke", true);
		
		//生成不带logo 的二维码
		String textt = "https://s.click.taobao.com/3SgJ3iw";
		QRCodeUtil.encode(textt,"","d:/dataoke",true);
		
		//指定二维码图片，解析返回数据
		//System.out.println(QRCodeUtil.decode("D:/WPS/75040887.jpg"));
 
	}
}
