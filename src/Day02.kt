import kotlin.math.max
import kotlin.math.min

fun main() {
  // Givens for testing
  var game1: String = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
  var game2: String = "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
  var game3: String = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
  var game4: String = "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
  var game5: String = "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"

  var colors = listOf("red" ,"blue", "green")

  var part1TestInput = readInput("Day02_test")
  var input = readInput("Day02")

  fun possibleGame(line: String): Boolean {
    val given: HashMap<String, Int> = hashMapOf(
      "red" to 12,
      "green" to 13,
      "blue" to 14
    )

    val turns = line.split(":").last().split(";")
    for (turn in turns) {
      val colorGroups = turn.split(", ")
      for (group in colorGroups) {
        val quantity = group.trim().split(" ").first().toInt()
        val color = group.trim().split(" ").last()
        val givenQuantity = given.getOrDefault(color, 0)
        // println("Quantity $quantity, Color $color")

        if (quantity > givenQuantity) {
          return false
        }
      }
    }
    return true
  }
  check(possibleGame(game1))
  check(possibleGame(game2))
  check(!possibleGame(game3))
  check(!possibleGame(game4))
  check(possibleGame(game5))


  fun extractTargetQuantity(turn: String, targetColor: String): Int {
    val colorGroups = turn.split(", ").map { it.trim() }
    val candidates = colorGroups.filter { it.contains(targetColor) }
    if (candidates.isEmpty()) {
      return 0
    } else {
      return candidates.first().split(" ").first().toInt()
    }
  }
  check(extractTargetQuantity("3 blue, 4 red", "red") == 4)
  check(extractTargetQuantity("3 blue, 4 red", "blue") == 3)
  check(extractTargetQuantity("3 blue, 4 red", "green") == 0)

  fun minimumCubes(game: String, givenColor: String): Int {
    val turns = game.split(":").last().split(";")
    return turns.fold(0) { acc, s ->  max(acc, extractTargetQuantity(s, givenColor))}
  }

  check(minimumCubes(game1, "red") == 4)
  check(minimumCubes(game1, "green") == 2)
  check(minimumCubes(game1, "blue") == 6)
  check(minimumCubes(game2, "red") == 1)
  check(minimumCubes(game2, "green") == 3)
  check(minimumCubes(game2, "blue") == 4)
  check(minimumCubes(game3, "red") == 20)
  check(minimumCubes(game3, "green") == 13)
  check(minimumCubes(game3, "blue") == 6)
  check(minimumCubes(game4, "red") == 14)
  check(minimumCubes(game4, "green") == 3)
  check(minimumCubes(game4, "blue") == 15)
  check(minimumCubes(game5, "red") == 6)
  check(minimumCubes(game5, "green") == 3)
  check(minimumCubes(game5, "blue") == 2)

  fun minimumCubes(game: String): List<Int> {
    return colors.map { minimumCubes(game, it) }
  }
  check(minimumCubes(game1) == listOf(4,6,2))
  check(minimumCubes(game2) == listOf(1,4,3))
  check(minimumCubes(game3) == listOf(20, 6, 13))
  check(minimumCubes(game4) == listOf(14, 15, 3))
  check(minimumCubes(game5) == listOf(6, 2, 3))

  fun power(game: String): Int {
    return minimumCubes(game).fold(1) { acc, i -> acc * i }
  }
  check(power(game1) == 48)
  check(power(game2) == 12)
  check(power(game3) == 1560)
  check(power(game4) == 630)
  check(power(game5) == 36)

  fun possibleGameIds(games: List<String>): List<Int> {
    val possibleGames = games.filter { possibleGame(it) }
    return possibleGames.map { it.split(":").first().filter { it.isDigit() }.toInt() }
  }
  check(possibleGameIds(part1TestInput) == listOf(1,2,5))

  fun part1(games: List<String>): Int {
    return possibleGameIds(games).sum()
  }
  check(part1(part1TestInput) == 8)

  fun part2(games: List<String>): Int {
    return games.sumOf { power(it) }
  }
  check(part2(part1TestInput) == 2286)

  part1(input).println()
  part2(input).println()
}