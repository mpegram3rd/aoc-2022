package main

import (
	"log"
	"strconv"
	"strings"
)

type Command interface {
	Do(data string, fileSystem *Node) *Node
}

type CDCommand struct{}

func (c *CDCommand) Do(data string, fileSystem *Node) *Node {
	parsed := strings.Split(data, " ")
	if parsed[2] == ".." {
		return fileSystem.GetParent()
	}
	newDir := fileSystem.FindByName(parsed[2])
	if newDir == nil {
		return fileSystem
	}
	return newDir
}

type LSCommand struct{}

func (c *LSCommand) Do(data string, fileSystem *Node) *Node {
	return fileSystem
}

type DirFileCommand struct{}

func (c *DirFileCommand) Do(data string, fileSystem *Node) *Node {
	parsed := strings.Split(data, " ")
	child := NewNode(parsed[1], 0)
	fileSystem.AddChild(child)
	return fileSystem
}

type FileCommand struct{}

func (c *FileCommand) Do(data string, fileSystem *Node) *Node {
	parsed := strings.Split(data, " ")

	// only add if we've never seen it before
	if fileSystem.FindByName(parsed[1]) == nil {
		size, err := strconv.Atoi(parsed[0])
		if err != nil {
			log.Fatalf("Error parsing file size: %s\n", err)
		}

		child := NewNode(parsed[1], size)
		fileSystem.AddChild(child)
	}
	return fileSystem
}
