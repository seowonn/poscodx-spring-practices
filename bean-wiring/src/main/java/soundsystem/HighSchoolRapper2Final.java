package soundsystem;

import org.springframework.stereotype.Component;

@Component // 자동 컴포넌트 스캔
public class HighSchoolRapper2Final implements CompactDisc {

	private final static String artist = "아이브";
	private final static String title = "All Night";
	
	@Override
	public String play() {
		return "Playing " + title + " by " + artist;
	}

}
