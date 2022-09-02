
public class Participant {
	private String name;
	private double score;

	public String getName() {
		return name;
	}

	public double getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Participant(String data) {
		this.name = data.substring(0, 15);
		this.score = Double.parseDouble(data.substring(15).trim());
	}

	public Participant(String name, double Score) {
		this.name = name;
		this.score = Score;
	}

}
