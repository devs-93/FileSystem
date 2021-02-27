package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.files.{DirEntry, File}
import com.ddfs.scala.oop.filesystem.State

class vim(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = {
    File.empty(state.wd.path, name)
  }
}
