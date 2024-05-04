package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

//	// ServletComponentScan을 사용하지 않을 경우 아래 방식을 통해 빈 등록
//	@Bean
//	public ServletRegistrationBean customServletRegistrationBean(){
//		return new ServletRegistrationBean(new CustomServlet(), "/servlet/*");
//	}
}
