package com.ddfs.scala.oop.files

import com.ddfs.scala.oop.filesystem.FilesystemException

import java.nio.file.FileSystemException

class File(override val parentPath: String, override val name: String, content: String) extends DirEntry(parentPath, name) {
  def asDictionary: Directory = throw new FilesystemException("A file can not be converted into directory!!")
  def getType: String = "File"
  override def isDirectory: Boolean = false
  override def isFile: Boolean = true
  def asFile: File = this
}

object File {
  def empty(parentPath: String, name: String): File = {
    new File(parentPath, name, "")
  }
}