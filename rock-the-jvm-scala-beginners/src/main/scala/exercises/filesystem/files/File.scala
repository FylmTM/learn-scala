package exercises.filesystem.files

import exercises.filesystem.filesystem.FilesystemException

class File(override val parentPath: String, override val name: String, val contents: String)
  extends DirEntry(parentPath, name) {

  def appendContents(newContents: String): DirEntry = setContents(s"$contents\n$newContents")

  def setContents(newContents: String): DirEntry = new File(parentPath, name, newContents)

  override def asDirectory: Directory = throw new FilesystemException(s"File $path is not a directory")

  override def asFile: File = this

  override def toString: String = name

  def isDirectory: Boolean = false

  def isFile: Boolean = true
}

object File {

  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
