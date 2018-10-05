package util.image2pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.TreeMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
	
	public static void imagesToPdf(String imagesPath ,String pdfFileName) {
        try {
            File file = new File(pdfFileName);
            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            File files = new File(imagesPath);
            String[] images = files.list();
            TreeMap<Integer,String> map = new TreeMap<Integer,String>() ;
            for(String s:images){
            	int val = Integer.valueOf(s.substring(0,s.lastIndexOf("."))) ;
            	map.put(val, s) ; 
            }
            for(Map.Entry<Integer, String> entry:map.entrySet()){
            	System.out.println(entry.getValue());
            	String temp = imagesPath + "\\" + entry.getValue();
                Image img = Image.getInstance(temp);
                img.setAlignment(Image.ALIGN_CENTER);
                // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                document.newPage();
                document.add(img);
            }
            // 第五步：关闭文档。
            document.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) {
		String pdfFile = "F:\\pdf\\test.pdf" ; 
		String imagePath = "F:\\images" ; 
		imagesToPdf(imagePath, pdfFile);
	}
}




