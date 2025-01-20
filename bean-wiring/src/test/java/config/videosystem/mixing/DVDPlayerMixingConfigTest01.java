package config.videosystem.mixing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import videosystem.DVDPlayer;

public class DVDPlayerMixingConfigTest01 {

	
	@Autowired
	private DVDPlayer dvdPlayer;
	
	@Test
	public void test() {
		assertEquals("Playing Movie Marvel's Avengers", null);
	}
}
