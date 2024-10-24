package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    fun saveBookTest() {
        // given
        val request = BookRequest("Spring Boot in Practice")

        // when
        bookService.saveBook(request)


        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("Spring Boot in Practice")
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다.")
    fun loanBookTest() {
        // given
        bookRepository.save(Book("Spring Boot in Practice"))
        val savedUser = userRepository.save(User("라인", null))
        val request = BookLoanRequest("라인", "Spring Boot in Practice")

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results[0].bookName).isEqualTo("Spring Boot in Practice")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isFalse()

    }

    @Test
    @DisplayName("책이 진짜 대출되어 있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest() {
        // given
        bookRepository.save(Book("Spring Boot in Practice"))
        val savedUser = userRepository.save(User("라인", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "Spring Boot in Practice", false))
        val request = BookLoanRequest("라인", "Spring Boot in Practice")

        // expected
        assertThatThrownBy { bookService.loanBook(request) }
            .hasMessage("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반남이 정상 동작한다.")
    fun returnBookTest() {
        // given
        val savedUser = userRepository.save(User("라인", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "Spring Boot in Practice", false))
        val request = BookReturnRequest("라인", "Spring Boot in Practice")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isTrue()
    }

}