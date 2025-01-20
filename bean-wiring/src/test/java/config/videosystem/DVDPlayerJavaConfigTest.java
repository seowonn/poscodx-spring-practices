package config.videosystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import videosystem.DVDPlayer;
import videosystem.DVDPlayerConfig;
import videosystem.DigitalVideoDisc;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DVDPlayerConfig.class})
public class DVDPlayerJavaConfigTest {

	// 같은 타입의 빈이 2개 이상 있는 경우
	// 설정 클래스의 빈 생성 메소드의 이름으로 Qualifier 하기
	@Autowired
	@Qualifier("avengers")
	DigitalVideoDisc dvd1;
	
	@Autowired
	@Qualifier("ironMan")
	DigitalVideoDisc dvd2;
	
	@Autowired
	@Qualifier("dvdPlayer1")
	private DVDPlayer dvdPlayer1;
	
	// 같은 타입의 빈이 2개 이상 있는 경우
	// 설정 클래스의 빈 생성 메소드의 @Bean의 name(default) 속성으로 Qualifier 하기
	@Autowired
	@Qualifier("DVDPlayer2nd")
	private DVDPlayer dvdPlayer2;
	
	@Test
	public void testDVD1() {
		assertNotNull(dvd1);
	}
	
	@Test
	public void testDVD2() {
		assertNotNull(dvd2);
	}
	
	public void testDVDPlayer1() {
		assertNotNull(dvdPlayer1);
		assertEquals("Playing Movie Marvel's Avengers", dvdPlayer1.play());
	}
	
}
