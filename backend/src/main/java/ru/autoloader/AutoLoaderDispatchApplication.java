package ru.autoloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "ru.autoloader")  // ✅ Добавляем сканирование контроллеров
@EnableJpaRepositories("ru.autoloader.repository")  // ✅ Добавляем репозитории
@EntityScan("ru.autoloader.model")
public class AutoLoaderDispatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoLoaderDispatchApplication.class, args);
	}

}
