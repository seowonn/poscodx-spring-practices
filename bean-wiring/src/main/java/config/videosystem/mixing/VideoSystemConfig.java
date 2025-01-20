package config.videosystem.mixing;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import videosystem.DVDPlayerConfig;
import videosystem.mixing.DVDConfig;

/**
 *  <--- JavaConfig2, JavaConfig1
 */
@Configuration
@Import({DVDConfig.class, DVDPlayerConfig.class})
public class VideoSystemConfig {

}
