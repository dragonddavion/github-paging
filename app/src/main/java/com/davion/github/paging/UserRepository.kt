package com.davion.github.paging

import android.util.Log
import com.davion.github.paging.network.GitHubApi
import com.davion.github.paging.network.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class UserRepository () {

    var since: Int? = null

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bsince=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }
    }

    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            val requestParams = HashMap<String, String>()
            if (since != null) {
                requestParams["since"] = since.toString()
            }

            val response = GitHubApi.retrofitService.getUsersAsync(requestParams).await()

            if (response.isSuccessful) {
                since = getNextUserPage(response.headers().get("link"))
                return@withContext response.body()!!
            } else {
                return@withContext listOf()
            }

        }
    }

    private fun getNextUserPage(linkHeader: String?): Int? {
        val links: Map<String, String> = linkHeader?.extractLinks() ?: emptyMap()

        return links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    null
                }
            }
        }
    }
}