package pl.borys.quiz

import org.junit.Test

import org.junit.Assert.*

class FactoriesTests {
    @Test
    fun getQuestionsList_ReturnProperListSize() {
        val listSize0 = 0
        val questions0 = QuestionFactory.getQuestionsList(listSize0)
        assertEquals(listSize0, questions0.size)

        val listSize1 = 1
        val questions1 = QuestionFactory.getQuestionsList(listSize1)
        assertEquals(listSize1, questions1.size)

        val listSize5 = 5
        val questions5 = QuestionFactory.getQuestionsList(listSize5)
        assertEquals(listSize5, questions5.size)
    }

    @Test
    fun getAnswersList_ReturnCorrectList() {
        val correctList = listOf(
                false,
                false,
                true,
                false
        )
        val expectedList = listOf(
                AnswersFactory.getAnswer(correctList[0], 0),
                AnswersFactory.getAnswer(correctList[1], 1),
                AnswersFactory.getAnswer(correctList[2], 2),
                AnswersFactory.getAnswer(correctList[3], 3)
                )

        val answers = AnswersFactory.getAnswersList(correctList)
        assertEquals(expectedList, answers)
    }
}
