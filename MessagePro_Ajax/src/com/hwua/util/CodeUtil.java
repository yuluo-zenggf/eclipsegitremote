package com.hwua.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CodeUtil {

	/**
	 * 
	 * @param width
	 *            验证码图片宽度
	 * @param height
	 *            验证码图片高度
	 * @param codeCount
	 *            验证码字符个数
	 * @param distance
	 *            字符之间的间隔
	 * @param fontHeight
	 *            字体高度(大小)
	 * @param codeY
	 *            验证码垂直起始位置
	 * @return
	 */
	public static Map<String, Object> generateCodeAndPic(int width, int height, int codeCount, int distance,
			int fontHeight, int codeY) {
		// 组成验证码的字符
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 图片缓冲区
		Graphics gd = buffImg.getGraphics();// 绘图工具对象(画板)
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);// 填充矩形

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);// 绘制矩形

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 30; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);// 绘制线
		}
		
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * distance, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        // 存放验证码字符串,比如:ABCD
        map.put("code", randomCode);
        // 存放生成的验证码BufferedImage对象
        map.put("codePic", buffImg);
        return map;
	}
}
