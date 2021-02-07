object Farm {

  def countSheep(sheep: Array[Boolean]): Int = sheep.filter(_ == true).map(_ => 1).sum
} 