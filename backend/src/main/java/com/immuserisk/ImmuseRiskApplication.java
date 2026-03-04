package com.immuserisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling // 添加此注解启用定时任务
@MapperScan(basePackages = {"com.immuserisk.dao"})
public class ImmuseRiskApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

        // 加载根目录的 .env 文件到系统环境变量
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // 指定 .env 所在目录（根目录）
                .load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(ImmuseRiskApplication.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(ImmuseRiskApplication.class);
	}

}
