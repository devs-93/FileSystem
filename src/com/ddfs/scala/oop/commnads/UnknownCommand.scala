package com.ddfs.scala.oop.commnads
import com.ddfs.scala.oop.filesystem.State


class UnknownCommand extends Command {
  override def apply(state: State): State = {
    state.setMessage("Command Not found !")
  }
}
