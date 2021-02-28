package com.ddfs.scala.oop.files

import com.ddfs.scala.oop.filesystem.FilesystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry]) extends DirEntry(parentPath, name) {

  def hasEntry(name: String): Boolean = {
    findEntry(name) != null
  }

  def getAllFolderNameInPath: List[String] = {
    // a/b/c/d -> [a,b,c,d]
    path.substring((1)).split((Directory.SEPARATOR)).toList.filter(x => x.nonEmpty)
  }


  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) {
      this
    }
    else {
      findEntry(path.head).asDictionary.findDescendant(path.tail)
    }
  }


  def addEntry(newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents :+ newEntry)
  }



  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry = {
      if (contentList.isEmpty) {
        null
      }
      else if (contentList.head.name.equals(name)) {
        contentList.head
      }
      else {
        findEntryHelper(name, contentList.tail)
      }
    }

    findEntryHelper(entryName, contents)
  }


  def replaceEntry(entryName: String, newEntry: DirEntry): Directory = {
    new Directory(parentPath,name,contents.filter(e => !e.name.equals((entryName))):+newEntry)
  }

  def isRoot :Boolean={
    parentPath.isEmpty
  }
  def asDictionary: Directory = this
  def asFile: File = throw new FilesystemException("A file can not be converted into directory !!")
  def getType:String="Directory"
  override def isDirectory: Boolean = true
  override def isFile: Boolean = false
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"


  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String) = {
    new Directory(parentPath, name, List())
  }

}