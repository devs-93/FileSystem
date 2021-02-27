package com.ddfs.scala.oop.files

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry]) extends DirEntry(parentPath, name) {

  def hasEntry(name: String): Boolean = ???
  
  def getAllFolderNameInPath: List[String] =
  {
    // a/b/c/d -> [a,b,c,d]
    path.substring((1)).split((Directory.SEPARATOR)).toList
  }
  def findDescendant(path: List[String]): Directory = ???
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")


  def empty(parentPath: String, name: String) = {
    new Directory(parentPath, name, List())
  }

}