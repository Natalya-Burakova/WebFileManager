package fileManager.config.planning;


import fileManager.app.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class PlanTaskConfig {

    @Autowired
    FileService fileService;

    @Scheduled(fixedRate = 4*24*60*60*1000) //каждые 4 дня
    public void scheduleFixedDelayTask() {
        fileService.monitorFile();
    }
}
