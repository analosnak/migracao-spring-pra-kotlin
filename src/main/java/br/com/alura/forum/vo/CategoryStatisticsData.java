package br.com.alura.forum.vo;

public class CategoryStatisticsData {

	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;

	public CategoryStatisticsData(int allTopics, int lastWeekTopics, int unansweredTopics) {		
		assertThatIsValidStatNumbers(allTopics, lastWeekTopics, unansweredTopics);

		this.allTopics = allTopics;
		this.lastWeekTopics = lastWeekTopics;
		this.unansweredTopics = unansweredTopics;
	}

	public int getAllTopics() {
		return allTopics;
	}

	public int getLastWeekTopics() {
		return lastWeekTopics;
	}

	public int getUnansweredTopics() {
		return unansweredTopics;
	}

	private void assertThatIsValidStatNumbers(int... statNumbers) {
		for (int statNumber : statNumbers) {

			if(statNumber < 0)
				throw new IllegalArgumentException("O número estatístico é invalido: " + statNumber);
		}
	}

}
