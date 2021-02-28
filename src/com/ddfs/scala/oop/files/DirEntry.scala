package com.ddfs.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separatorIfReq = {
      if (Directory.ROOT_PATH.equals(parentPath)) {""}
      else {Directory.SEPARATOR}
    }
    parentPath + separatorIfReq + name
  }

  def asDictionary: Directory

  def getType: String

  def asFile: File

  def isDirectory: Boolean

  def isFile: Boolean
}
