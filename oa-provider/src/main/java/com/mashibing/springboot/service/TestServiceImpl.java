package com.mashibing.springboot.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0" , interfaceClass = ITestService.class,
	loadbalance = "roundrobin"/*, executes = 1*/
		,timeout = 1000 ,retries = 5
		)
// executes = 1可以用来限制线程数，可以用来限流
// 幂等   update maxiaoliu = 598  ++ 不行
// 每次重新尝试连接都是开辟一个新的线程去建立连接，所以也会收到executes的限制
// 当连接时长超出timeout即会开辟新线程去连接，并行。
// retries = 5 + 第一次调用1 
public class TestServiceImpl implements ITestService{
	
	@Value("${dubbo.protocol.port}")
	private int port;
	
	@Override
	public int getPort() {

//		long start = System.currentTimeMillis();
//
//		System.out.println("---- action");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		long end = System.currentTimeMillis();
//		System.out.println(Thread.currentThread().getName() + " ：线程  运行时间" + (end-start) + "ms");
		return port;
	}

}
