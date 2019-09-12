package exercises.filesystem.files

import exercises.filesystem.filesystem.FilesystemException

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {

  override def asDirectory: Directory = throw new FilesystemException(s"File $path is not a directory")

  override def asFile: File = this

  override def toString: String = name
}

object File {

  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
