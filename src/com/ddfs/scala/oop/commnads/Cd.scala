package com.ddfs.scala.oop.commnads

import com.ddfs.scala.oop.files.{DirEntry, Directory}
import com.ddfs.scala.oop.filesystem.State

import scala.annotation.tailrec


class Cd(dir: String) extends Command {
  override def apply(state: State): State = {

    // 1. find the root
    val root = state.root
    val wd = state.wd

    // 2. Find the absolute path for changing the directory
    val absolutePath = {
      if (dir.startsWith(Directory.SEPARATOR)) {
        dir
      }
      // its absolute path}
      // wd = / => //a/b/c/ [this is wrong]
      else if (wd.isRoot) {
        wd.path + dir
      }
      else {
        wd.path + Directory.SEPARATOR + dir
      }
    }

    // 3. find the directory to cd to , given the path
    val destinationDirectory = doFindEntry(root, absolutePath)

    // 4. change the state given the new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory) {
      state.setMessage(dir + " : no such directory ")
    }
    else {
      State(root, destinationDirectory.asDictionary)
    }
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) {
        currentDirectory
      }
      else if (path.tail.isEmpty) {
        currentDirectory.findEntry(path.head)
      }
      else {
        val nexDir = currentDirectory.findEntry(path.head)
        if (nexDir == null || !nexDir.isDirectory) null
        else findEntryHelper(nexDir.asDictionary, path.tail)
      }
    }

    @tailrec
    def collapseRelativeToken(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) {
        result
      }
      else if (".".equals(path.head)) {
        collapseRelativeToken(path.tail, result)
      }
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeToken(path.tail, result.tail)
      }
      else {
        collapseRelativeToken(path.tail, result :+ path.head)
      }
    }


    // 1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    // 1.5  eliminate or collapse relative token


    val newTokens = collapseRelativeToken(tokens, List())
    // 2. navigate to the correct entry
    findEntryHelper(root, newTokens)
  }
}
