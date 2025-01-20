package videosystem;

import java.util.List;

public class DVDPack {

	private final String title;
	private final List<DigitalVideoDisc> dvds;

	public DVDPack(String title, List<DigitalVideoDisc> dvds) {
	    this.title = title;
	    this.dvds = dvds;
	}
}
