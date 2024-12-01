package retar.advent.code

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform