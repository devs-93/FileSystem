package com.ddfs.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = parentPath + Directory.SEPARATOR + name
  def asDictionary : Directory
  def getType:String
  def asFile : File
}
