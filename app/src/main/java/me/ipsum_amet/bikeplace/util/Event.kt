package me.ipsum_amet.bikeplace.util

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun GetContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}