package jp.kopher.customfailureanalyzer.exception

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis

class UrlNotAccessibleFailureAnalyzer: AbstractFailureAnalyzer<UrlNotAccessibleException>() {
    override fun analyze(rootFailure: Throwable?, cause: UrlNotAccessibleException?): FailureAnalysis {
        return FailureAnalysis("Unable to access the URL ${cause?.url}", "Validate the URL and ensure it is accessible", cause)
    }
}
