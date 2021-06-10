package com.mashibing.springboot.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mashibing.springboot.config.WxConfig;
import com.mashibing.springboot.filter.WxAuthFilter;

import weixin.popular.api.MessageAPI;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.bean.qrcode.QrcodeTicket;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;

@RestController
@RequestMapping("/msg")
public class MsgController {
	

	private static final Logger logger = LoggerFactory.getLogger(WxAuthFilter.class);

	@Autowired
	WxConfig wxConf;
	
	@RequestMapping("")
	public Object list(@RequestParam Map<String, String> param,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		
		QrcodeTicket qrcodeCreateTemp = QrcodeAPI.qrcodeCreateTemp(TokenManager.getDefaultToken(), 604800, "user_id=10000");
		System.out.println("qrcodeCreateTemp:" + ToStringBuilder.reflectionToString(qrcodeCreateTemp));
		BufferedImage showqrcode = QrcodeAPI.showqrcode(qrcodeCreateTemp.getTicket());
		
		ByteArrayOutputStream os=new ByteArrayOutputStream();//新建流。
		ImageIO.write(showqrcode, "png", os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
		byte b[]=os.toByteArray();//从流中获取数据数组。

		
		resp.getOutputStream().write(b);
		
		return qrcodeCreateTemp;
	}

}
