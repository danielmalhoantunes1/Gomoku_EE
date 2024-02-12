package pt.isel.pdm.gomoku_ee.Domain

abstract class InputException : Exception()

object EmptyTitle : InputException()
object EmptyOpponent : InputException()
