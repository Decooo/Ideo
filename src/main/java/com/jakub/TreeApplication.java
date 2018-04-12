package com.jakub;

import com.jakub.config.StageManager;
import com.jakub.view.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jakub.repository")
public class TreeApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private StageManager stageManager;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception{
		springContext = springBootApplicationContext();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		displayInitialScene();
	}

	@Override
	public void stop() throws Exception{
		springContext.close();
	}

	protected void displayInitialScene(){
		stageManager.switchScene(FxmlView.TREE);
	}

	private ConfigurableApplicationContext springBootApplicationContext(){
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TreeApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}
}
