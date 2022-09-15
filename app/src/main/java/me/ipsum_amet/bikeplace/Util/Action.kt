package me.ipsum_amet.bikeplace.Util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION,
    LOG_OUT,
    GET_ALL_BIKES,
    GET_TOP_CHOICE_BIKES,
    GET_ALL_BIKES_BY_TOP_CHOICE,
    GET_ALL_BIKES_BY_CATEGORY,
    GET_CATEGORIES_OF_BIKE,
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
        this == "GET_ALL_BIKES_BY_TOP_CHOICE" -> {
            Action.GET_ALL_BIKES_BY_TOP_CHOICE
        }
        this == "GET_ALL_BIKES_BY_CATEGORY" -> {
            Action.GET_ALL_BIKES_BY_CATEGORY
        }
        this == "GET_TOP_CHOICE_BIKES" -> {
            Action.GET_TOP_CHOICE_BIKES
        }
        this == "GET_CATEGORIES_OF_BIKE" -> {
            Action.GET_CATEGORIES_OF_BIKE
        }
        else -> {
            Action.NO_ACTION
        }
    }
}