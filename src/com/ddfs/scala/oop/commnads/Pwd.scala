package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State = {
    state.setMessage(state.wd.path)
  }
}
