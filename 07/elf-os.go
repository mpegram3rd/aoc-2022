package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

const MaxDirSizeProb1 = 100000
const DriveSize = 70000000
const FreeSpaceRequired = 30000000

func main() {
	// open file and check for errors
	file, err := os.Open("input.txt")

	defer file.Close()
	if err != nil {
		log.Fatal(err)
	}

	// Command handlers map
	handlers := map[string]Command{
		"$ cd": new(CDCommand),
		"$ ls": new(LSCommand),
		"dir ": new(DirFileCommand),
		"file": new(FileCommand),
	}

	garbIn := bufio.NewScanner(file)
	garbIn.Split(bufio.ScanLines)

	root := NewNode("", 0)
	currentNode := root

	for garbIn.Scan() {
		line := garbIn.Text()
		cmd := line[0:4]
		handler := handlers[cmd]
		if line[0:1] == "$" {
			// Output a command prompt
			fmt.Printf("[%s]: %s\n", currentNode.name, line)
		} else {
			// Output just the line as is because it is the result of a command
			fmt.Printf("%s\n", line)
		}
		if handler == nil {
			handler = handlers["file"]
		}
		currentNode = handler.Do(line, currentNode)
	}
	println("All commands and results processed\n----------------------------\nThe File System looks like this:")
	root.PrettyPrint("")
	totalUsed := root.TotalSize()
	availableDiskSpace := DriveSize - root.TotalSize()
	fmt.Printf("Total disk space used: %d / Available: %d\n", totalUsed, availableDiskSpace)
	fmt.Printf("Total size with limit %d: %d\n", MaxDirSizeProb1, root.FindDirsWithinLimit(MaxDirSizeProb1))
	spaceNeeded := FreeSpaceRequired - availableDiskSpace
	fmt.Printf("We need to free up %d bytes to have the required %d space\n", spaceNeeded, FreeSpaceRequired)
	node := root.FindFreeSpace(spaceNeeded, totalUsed)
	fmt.Printf("Best directory to delete is %s of size %d\n", node.GetFullPath(), node.TotalSize())
}
