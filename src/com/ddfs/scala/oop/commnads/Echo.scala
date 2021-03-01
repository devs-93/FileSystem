package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
    if (args.isEmpty) {
      state
    }
    else if (args.length == 1) {
      state.setMessage(args(0))
    } else {
      val opt = args(args.length - 2)
      val fileName = args(args.length - 1)
      val content = createContent(args, args.length - 2)
      if (">>".equals(opt)) {
        doEcho(state, content, fileName, true)
      }
      else if (">".equals(opt)) {
        doEcho(state, content, fileName, false)
      }
      else {
        state.setMessage(createContent(args, args.length))
      }
    }
  }

  def doEcho(state: State, content: String, fileName: String, append: Boolean): State = {
    ???
  }

  // topIndex in not inclusive
  def createContent(args: Array[String], topIndex: Int): String = {

    def createContentHelper(currentIndex: Int, acc: String): String = {
      if (currentIndex >= topIndex) {
        acc
      }
      else {
        createContentHelper(currentIndex + 1, acc + " " + args(currentIndex))
      }
    }

    createContentHelper(0, "")
  }

}
