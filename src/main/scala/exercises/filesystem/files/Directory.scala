package exercises.filesystem.files

import exercises.filesystem.filesystem.FilesystemException

class Directory(
  override val parentPath: String,
  override val name: String,
  val contents: List[DirEntry]
) extends DirEntry(parentPath, name) {

  def hasEntry(entryName: String): Boolean = findEntry(entryName) != null

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    @scala.annotation.tailrec
    def findEntryHelper(entryName: String, contentList: List[DirEntry]): DirEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(entryName)) contentList.head
      else findEntryHelper(entryName, contentList.tail)
    }

    findEntryHelper(entryName, contents)
  }

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.map(d => if (d.name.equals(entryName)) newEntry else d))
//    new Directory(parentPath, name, contents.filter(!_.name.equals(entryName)) :+ newEntry)

  def getAllFoldersInPath: List[String] = path
    .substring(1)
    .split(Directory.SEPARATOR)
    .filter(!_.isEmpty)
    .toList

  def findDescendant(path: List[String]): Directory =
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)

  override def asDirectory: Directory = this

  override def toString = s"$name/"

  override def asFile: File = throw new FilesystemException(s"Directory $path is not a file")
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String) = new Directory(parentPath, name, List())
}
