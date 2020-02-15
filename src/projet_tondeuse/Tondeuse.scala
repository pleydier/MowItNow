package projet_tondeuse

/*
* The Tondeuse class allows us to instantiate tondeuse objects, containing:
* - coordinates: the (x,y) current coordinates
* - orientation: the current orientation ('N' / 'S' / 'E' / 'W')
* - max_coordinates: the size of the map
 */
class Tondeuse(var coordinates:(Int, Int),
               var orientation:Char,
               val max_coordinates:(Int, Int)) {

  /*
  * This Map allows us to know what is the new orientation after a turn left / right instruction.
  * For example, if we are facing north and want to turn left, we will take the first value of the tuple of key 'N'
   */
  private val orientationMap = Map('N' -> ('W', 'E'), 'E' -> ('N', 'S'), 'S' -> ('E', 'W'), 'W' -> ('S', 'N'))

  /*
  * String format for displaying the end result.
   */
  override def toString: String = {
    this.coordinates._1 + " " + this.coordinates._2 + " " + this.orientation
  }

  /*
  * Modifies the coordinates by moving forward, only if this does not make the tondeuse go out of the map.
   */
  private def goForward(): Unit ={
    this.coordinates = this.orientation match {
      case 'N' if this.coordinates._2 + 1 <= this.max_coordinates._2 =>  (this.coordinates._1, this.coordinates._2 + 1)
      case 'W' if this.coordinates._1 - 1 >= 0 => (this.coordinates._1 - 1, this.coordinates._2)
      case 'S' if this.coordinates._2 - 1 >= 0 => (this.coordinates._1, this.coordinates._2 - 1)
      case 'E' if this.coordinates._1 + 1 <= this.max_coordinates._2 => (this.coordinates._1 + 1, this.coordinates._2)
      case _ => this.coordinates
    }
  }

  /*
  * Calls the right function depending on the current instruction.
   */
  private def move(instruction:Char):Unit = {
    instruction match {
      case 'G' => this.orientation = this.orientationMap(this.orientation)._1
      case 'D' => this.orientation = this.orientationMap(this.orientation)._2
      case 'A' => this.goForward()
      case _ => throw new IllegalArgumentException()
    }
  }

  /*
  * Sequentially executes all the instructions in the given string.
   */
  def executeMoves(instructions:String): Unit ={
    for(instruction <- instructions){
      try{
        this.move(instruction)
      } catch {
        case _: IllegalArgumentException => throw new IllegalArgumentException("'" + instruction + "'" +
          " instruction in input file is not supported.")
      }
    }
  }
}
