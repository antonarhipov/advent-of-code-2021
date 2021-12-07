fun main() {
    val input = readInput("Day04")

    val bets = input[0].split(",").map { it.toInt() }

    val boards = input
        .drop(1)
        .chunked(6)
        .map { boardLines ->
            boardLines.filter { line ->
                line.isNotBlank()
            }
        }

    val cleanedInput = boards.map { board ->
        board.map { line ->
            line.trim()
                .split(Regex("\\W+"))
                .map { it.toInt() }
        }
    }


    var bingoBoards = cleanedInput.map { Board.fromCollection(it) }

    // Prints all winners in order of appearance.
    // Part 1 is the first winner.
    // Part 2 is the last winner.
    for (draw in bets) {
        for (board in bingoBoards) {
            board.mark(draw)
            if (board.isComplete()) {
                val sumOfUnmarkedFields = board.unmarked().sum()
                println(sumOfUnmarkedFields * draw)
                bingoBoards = bingoBoards - board
            }
        }
    }
}

data class Field(val value: Int, val marked: Boolean = false)

data class Board(val fields: List<MutableList<Field>>) {

    private val widthIndices = fields[0].indices
    private val heightIndices = fields.indices

    companion object {
        fun fromCollection(coll: List<List<Int>>): Board {
            return Board(coll.map { row -> row.map { field -> Field(field) }.toMutableList() })
        }
    }

    fun isComplete() = checkRow() || checkColumn()
    private fun checkRow() = fields.any { row -> row.all { it.marked } }

    private fun checkColumn(): Boolean {
        for (column in widthIndices) {
            var columnOk = true
            for (row in heightIndices) {
                if (!fields[row][column].marked) {
                    columnOk = false
                    continue
                }
            }
            if (columnOk) return true
        }
        return false
    }

    fun mark(num: Int) {
        for (row in this.fields) {
            row.replaceAll {
                if (it.value == num) it.copy(marked = true) else it
            }
        }
    }

    fun unmarked() = fields.flatten().filter { !it.marked }.map { it.value }
}