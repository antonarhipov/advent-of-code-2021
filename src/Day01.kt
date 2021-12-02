fun main() {
    fun part1(input: List<Int>): Int {
        return input.windowed(2).count { (a, b) -> a < b }
    }

    // A + B + C <=> B + C + D
    fun part2(input: List<Int>): Int {
        return input
            .windowed(4)
            .count {
                it[0] < it[3]
            }
    }

    val input = readInputAsInts("Day01")
    println(part1(input))
    println(part2(input))
}
