package jp.co.kopher1601.kopherlog.response

/**
 * {
 *      code: "400",
 *      message: "잘못된 요청입니다"
 *      validationErrors : {
 *          title: "한 글자 이상 작성해주세요"
 *      }
 * }
 */
data class ErrorResponse(
    private val code: String,
    private val message: String,
    private val validationErrors: MutableMap<String, String> = mutableMapOf()
) {

    fun addValidation(name: String, message: String) {
        validationErrors[name] = message
    }
}