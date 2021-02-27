package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.files.{DirEntry, Directory}
import com.ddfs.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  def doCreateEntry(name: String, state: State): State = {
    def updateStructure(oldRootDir: Directory, allDirsInPath: List[String], newEntry: DirEntry): Directory = {
      if (allDirsInPath.isEmpty) {
        oldRootDir.addEntry(newEntry)
      }
      else {
        val oldEntryInDir = oldRootDir.findEntry(allDirsInPath.head).asDictionary
        oldRootDir.replaceEntry(oldEntryInDir.name,updateStructure(oldEntryInDir,allDirsInPath.tail,newEntry))
      }
    }

    val wd = state.wd
    // 1. all the directory in the full path
    val allDirsInPath = wd.getAllFolderNameInPath // DONE
    // 2. create new directory entry in th wd
    /*val newDirectory = Directory.empty(wd.path, name) // DONE*/
    // TODO implement this
    val newEntry:DirEntry= createSpecificEntry(state)
    // 3. update the whole directory structure from the root(the directory is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    // 4. find new working directory instance given wd's full path, in the NEW directory structure.
    val newWd = newRoot.findDescendant(allDirsInPath)
    State(newRoot, newWd)
  }

  def createSpecificEntry(state:State):DirEntry = ???

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists!")
    }
    else if (name.contains(Directory.SEPARATOR)) {
      // mkdir <abc/def/sdf> ---> Not Allowed in the system
      state.setMessage(name + " must not contain separator!")
    }
    else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name !")
    }
    else {
      doCreateEntry(name, state)
    }
  }
}
