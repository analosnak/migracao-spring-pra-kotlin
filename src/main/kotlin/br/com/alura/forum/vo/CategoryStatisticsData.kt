package br.com.alura.forum.vo

class CategoryStatisticsData(val allTopics: Int,
                             val lastWeekTopics: Int,
                             val unansweredTopics: Int) {
    init {
        assertThatIsValidStatNumbers(allTopics, lastWeekTopics, unansweredTopics)
    }

    private fun assertThatIsValidStatNumbers(vararg statNumbers: Int) {
        for (statNumber in statNumbers) {
            require(statNumber >= 0) { "O número estatístico é invalido: $statNumber" }
        }
    }
}