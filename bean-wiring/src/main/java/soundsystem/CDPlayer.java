package soundsystem;

import org.springframework.stereotype.Component;

@Component
public class CDPlayer {
	
	// CompactDisc 인터페이스를 주입받음
	private final CompactDisc cd;
	
	public CDPlayer(CompactDisc cd) {
		this.cd = cd;
	}
	
	public String play() {
		return cd.play();
	}
	
}
