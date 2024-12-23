package jp.co.kopher1601.springtxkotlin.propagation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.UnexpectedRollbackException

@SpringBootTest
class MemberServiceTest @Autowired constructor(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val logRepository: LogRepository,
) {

    /**
     * memberService        @Transactional: OFF
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON
     */
    @Test
    fun outerTxOff_success() {
        // given
        val username = "outerTxOff_success"

        // when
        memberService.joinV1(username)

        // then 정상저장
        assertThat(memberRepository.find(username)).isNotNull
        assertThat(logRepository.find(username)).isNotNull

    }

    /**
     * memberService        @Transactional: OFF
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON
     */
    @Test
    fun outerTxOff_fail() {
        // given
        val username = "로그예외_outerTxOff_success"

        // when
        assertThatThrownBy { memberService.joinV1(username) }
            .isInstanceOf(RuntimeException::class.java)

        // then
        assertThat(memberRepository.find(username)).isNotNull()
        assertThat(logRepository.find(username)).isNull()

    }

    /**
     * memberService        @Transactional: ON
     * memberRepository     @Transactional: OFF
     * logRepository        @Transactional: OFF
     */
    @Test
    fun singleTx() {
        // given
        val username = "outerTxOn_success"

        // when
        memberService.joinV1(username)

        // then
        assertThat(memberRepository.find(username)).isNotNull()
        assertThat(logRepository.find(username)).isNotNull()
    }

    /**
     * memberService        @Transactional: ON
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON
     */
    @Test
    fun outerTxOn_success() {
        // given
        val username = "outerTxOn_success"

        // when
        memberService.joinV1(username)

        // then
        assertThat(memberRepository.find(username)).isNotNull()
        assertThat(logRepository.find(username)).isNotNull()
    }

    /**
     * memberService        @Transactional: On
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON
     */
    @Test
    fun outerTxOn_fail() {
        // given
        val username = "로그예외_outerTxOn_fail"

        // when
        assertThatThrownBy { memberService.joinV1(username) }
            .isInstanceOf(RuntimeException::class.java)

        // then 모든 데이터가 롤백
        assertThat(memberRepository.find(username)).isNull()
        assertThat(logRepository.find(username)).isNull()
    }

    /**
     * memberService        @Transactional: On
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON
     */
    @Test
    fun recoverException_fail() {
        // given
        val username = "로그예외_recoverException_fail"

        // when
        assertThatThrownBy { memberService.joinV2(username) }
            .isInstanceOf(UnexpectedRollbackException::class.java)

        // then try-catch 로 잡아서 커밋하려고 했지만 이미 rollbackOnly 마크되었기 때문에 UnexpectedRollbackException 를 터트리고 롤백된다.
        assertThat(memberRepository.find(username)).isNull()
        assertThat(logRepository.find(username)).isNull()
    }

    /**
     * memberService        @Transactional: On
     * memberRepository     @Transactional: ON
     * logRepository        @Transactional: ON (REQUIRES_NEW)
     */
    @Test
    fun recoverException_success() {
        // given
        val username = "로그예외_recoverException_success"

        // when
        memberService.joinV2(username)

        // then try-catch 로 잡아서 커밋하려고 했지만 이미 rollbackOnly 마크되었기 때문에 UnexpectedRollbackException 를 터트리고 롤백된다.
        assertThat(memberRepository.find(username)).isNotNull()
        assertThat(logRepository.find(username)).isNull()
    }
}