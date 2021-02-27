package com.ddfs.scala.oop.filesystem

import com.ddfs.scala.oop.commnads.Command
import com.ddfs.scala.oop.files.Directory

import java.util.Scanner

object FileSystem extends App {

  val root = Directory.ROOT
  var state = State(root,root)
  val scanner = new Scanner(System.in)
  while (true) {
    state.show
    val input = scanner.nextLine()
    state= Command.from(input).apply(state)
  }
}
