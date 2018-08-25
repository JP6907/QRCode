package com.jp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
	 private static final int BLACK = 0xFF000000;  
	    private static final int WHITE = 0xFFFFFFFF;  
	  
	    private static BufferedImage toBufferedImage(BitMatrix matrix) {  
	        int width = matrix.getWidth();  
	        int height = matrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	  
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
	            }  
	        }  
	        return image;  
	    }  
	  
	    public static void addLogo(BufferedImage qrcodeImg, BufferedImage logoImg) {  
	        int qrcodeImgWidth = qrcodeImg.getWidth();  
	        int suitableWidth = qrcodeImgWidth/5;  
	        qrcodeImg.getGraphics().drawImage(logoImg, (qrcodeImgWidth - suitableWidth) / 2,  
	                (qrcodeImgWidth - suitableWidth) / 2, suitableWidth, suitableWidth, null);  
	  
	    }  
	/** 
     * 生成二维码 
     * @param content 二维码内容 
     * @param width 图片尺寸 
     * @param dest  存放的二维码的输出流 
     * @param logo  是否在二维码中间加logo 
     * @throws WriterException 
     * @throws IOException 
     */  
    public static void generate(String content, Integer width, OutputStream dest, BufferedImage logo)  
            throws WriterException, IOException {  
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        // 指定纠错等级  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, width, hints);  
        BufferedImage qrcode = toBufferedImage(bitMatrix);  
        if (logo != null) {  
            addLogo(qrcode, logo);  
        }  
        ImageIO.write(qrcode, "jpg", dest);  
        dest.close();  
    }  
    
    public static void main(String[] args) {
		File file = new File("test.jpg");
		try {
			OutputStream os = new FileOutputStream(file);
			//生成二维码
			generate("15521374237,888888,2016-10-16 12:22:22", 80, os, null);
		} catch (WriterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
