package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.files.{DirEntry, Directory}
import com.ddfs.scala.oop.filesystem.State


class Mkdir(name: String) extends Command {
  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doMkdir(name: String, state: State): State = {

    def updateStructure(currentDir:Directory,path:List[String],newEntry:DirEntry):Directory= ???

    val wd = state.wd


    // 1. all the directory in the full path
    val allDirsInPath=wd.getAllFolderNameInPath

    // 2. create new directory entry in th wd
    val newDirectory = Directory.empty(wd.path,name)

    // 3. update the whole directory structure from the root(the directory is IMMUTABLE)
    val newRoot = updateStructure(state.root,allDirsInPath,newDirectory)

    // 4. find new working directory instance given wd's full path, in the NEW directory structure.
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot,newWd)
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists!")
    }
    else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separator !")
    }
    else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name !")
    }
    else {
      doMkdir(name, state)
    }
  }
}
