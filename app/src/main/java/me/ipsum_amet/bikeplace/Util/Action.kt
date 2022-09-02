package me.ipsum_amet.bikeplace.Util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION,
    LOG_OUT,
    GET_ALL_BIKES
}

fun String?.toAction(): Action {
    return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        this == "GET_ALL_BIKES" -> {
            Action.GET_ALL_BIKES
        }
        else -> {
            Action.NO_ACTION
        }
    }
}