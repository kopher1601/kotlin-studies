package jp.co.kopher1601.kopherlog.controller

import jp.co.kopher1601.kopherlog.config.data.UserSession
import jp.co.kopher1601.kopherlog.request.PostCreate
import jp.co.kopher1601.kopherlog.request.PostEdit
import jp.co.kopher1601.kopherlog.request.PostSearch
import jp.co.kopher1601.kopherlog.response.PostResponse
import jp.co.kopher1601.kopherlog.service.PostService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class PostController(
    private val postService: PostService,
) {

    val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/test")
    fun test(userSession: UserSession): String {
        log.info("userSession: {}", userSession)
        return "hello"
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    fun post(@RequestBody @Validated postCreate: PostCreate, @RequestHeader authorization: String) {
        if (authorization.equals("코퍼")) {
            postCreate.validate()
            postService.write(postCreate)
        }
    }

    @GetMapping("/posts/{postId}")
    fun get(@PathVariable("postId") id: Long): PostResponse {
        return postService.get(id)
    }

    @GetMapping("/posts")
    fun getList(@ModelAttribute postSearch: PostSearch): List<PostResponse> {
        return postService.getList(postSearch)
    }

    @PatchMapping("/posts/{postId}")
    fun editPost(@PathVariable postId: Long, @RequestBody @Validated postEdit: PostEdit) {
        postService.edit(postId, postEdit)
    }

    @DeleteMapping("/posts/{postId}")
    fun delete(@PathVariable("postId") postId: Long) {
        postService.delete(postId)
    }
}
