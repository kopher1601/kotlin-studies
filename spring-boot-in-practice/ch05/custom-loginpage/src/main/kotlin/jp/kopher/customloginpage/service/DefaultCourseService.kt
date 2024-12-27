package jp.kopher.customloginpage.service

import jp.kopher.customloginpage.model.Course
import jp.kopher.customloginpage.repository.CourseRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultCourseService(
    private val courseRepository: CourseRepository,
): CourseService {

    override fun createCourse(course: Course): Course {
        return courseRepository.save(course)
    }

    override fun findCourseById(courseId: Long): Optional<Course> {
        return courseRepository.findById(courseId)
    }

    override fun findAllCourses(): Iterable<Course> {
        return courseRepository.findAll()
    }

    override fun updateCourse(course: Course): Course {
        return courseRepository.save(course)
    }

    override fun deleteCourseById(courseId: Long) {
        courseRepository.deleteById(courseId)
    }
}