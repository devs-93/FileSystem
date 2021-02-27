package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.files.{DirEntry, Directory}
import com.ddfs.scala.oop.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = {
    Directory.empty(state.wd.path, name)
  }
}
