package jp.co.kopher1601.kopherlog.exception

class PostNotFound(
    message: String? = "Post not found",
) : KopherlogException(message) {
    override fun statusCode(): Int {
        return 404
    }
}
