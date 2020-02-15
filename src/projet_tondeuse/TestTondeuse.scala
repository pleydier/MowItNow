package projet_tondeuse

import scala.collection.mutable.ListBuffer
import scala.io.Source

object TestTondeuse {
  def main(args: Array[String]): Unit = {
    val filename = "C:\\Users\\paull\\IdeaProjects\\MowItNow\\data\\input.txt" // Input file

    // Handling input file
    val bufferedSource = Source.fromFile(filename)
    val lines = bufferedSource.getLines.toList
    bufferedSource.close

    // Getting the values from the input file
    // Line 0 is always map maximum coordinates
    val max_coords = lines(0).split(" ")
    var list_initial_position = ListBuffer[Array[String]]()
    var instructions = ListBuffer[String]()
    for((line, i) <- lines.zipWithIndex){
      // All odd lines are initial positions of a tondeuse
      if(i > 0 && i%2 == 1){
        list_initial_position += line.split(" ")
      } else if(i > 0 && i%2 == 0){ // All even lines (after 0) are runtime instructions of a tondeuse
        instructions += line
      }
    }

    // For every tondeuse
    for((initial_position, instruction) <- list_initial_position.zip(instructions)){
      // Instantiating the Tondeuse
      val tondeuse = new Tondeuse(
        (initial_position(0).toInt, initial_position(1).toInt),
        initial_position(2).charAt(0),
        (max_coords(0).toInt, max_coords(1).toInt)
      )
      // Executing the instructions of the file
      tondeuse.executeMoves(instruction)
      // Displaying the end result
      println(tondeuse.toString())
    }
  }
}
