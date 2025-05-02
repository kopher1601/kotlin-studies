package jp.co.kopher.board.article.service.request

data class ArticleCreateRequest(
    val title: String,
    val content: String,
    val boardId: Long,
    val writerId: Long,
)