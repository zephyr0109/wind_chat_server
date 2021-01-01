package com.zephyr.windchat.domain;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

/**
 * mariadb용 데이터배이스 접근으로, r2dbc에서는 아직 라이브러리가 미완성이기 때문에 사용을 보류함
 * 
 * @author zephyr
 *
 */
@Repository
public class DatabaseConfig {

	private ConnectionFactory factory;
    @SuppressWarnings("unused")
    private ConnectionPool pool;
	@Value("${spring.r2dbc.url}")
    private String dbUrl;

	@PostConstruct
    public void init() {
		//dbUrl = "r2dbc:mysql://windchat:wnsduf65@127.0.0.1:3306/windchat";
		factory = ConnectionFactories.get(dbUrl);
		
		ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(factory)
				.maxIdleTime(Duration.ofMillis(1000)).maxSize(20).build();
        this.pool = new ConnectionPool(configuration);
	}

}
