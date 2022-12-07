package main

import "fmt"

type Node struct {
	name     string
	isDir    bool
	size     int
	children []*Node
	parent   *Node
}

func NewNode(name string, size int) *Node {
	return &Node{name: name, isDir: size == 0, size: size}
}

func (n *Node) TotalSize() int {
	totalSize := n.size
	if n.children != nil && len(n.children) > 0 {
		for _, child := range n.children {
			totalSize += child.TotalSize()
		}
	}
	return totalSize
}

func (n *Node) AddChild(child *Node) *Node {
	child.parent = n
	n.children = append(n.children, child)
	fmt.Printf("Added file: %s\n", child.GetFullPath())
	return n
}

func (n *Node) GetFullPath() string {
	if n.parent == nil {
		return n.name
	}
	path := n.parent.GetFullPath() + "/" + n.name
	return path
}

func (n *Node) GetParent() *Node {
	return n.parent
}

func (n *Node) FindByName(name string) *Node {
	for _, child := range n.children {
		if child.name == name {
			return child
		}
	}
	return nil
}

// Wow.... this took FOREVER to sort out.
func (n *Node) FindDirsWithinLimit(limit int) int {
	total := 0
	if n.isDir {
		nodeSize := n.TotalSize()
		if nodeSize < limit {
			total += nodeSize
		}
		for _, child := range n.children {
			total += child.FindDirsWithinLimit(limit)
		}
	}
	return total
}

func (n *Node) PrettyPrint(padding string) {

	if n.isDir {
		fmt.Printf("%s/%s %d\n", padding, n.name, n.TotalSize())
		for _, child := range n.children {
			child.PrettyPrint(padding + "  ")
		}
	} else {
		fmt.Printf("%s%d %s\n", padding, n.size, n.name)
	}
}

func (n *Node) FindFreeSpace(spaceNeeded int, smallestSoFar int) *Node {
	var bestNode *Node
	if n.isDir {
		nodeSize := n.TotalSize()
		if nodeSize >= spaceNeeded && nodeSize <= smallestSoFar {
			bestNode = n
			smallestSoFar = nodeSize
		}
		for _, child := range n.children {
			result := child.FindFreeSpace(spaceNeeded, smallestSoFar)
			if result != nil {
				bestNode = result
				smallestSoFar = result.TotalSize()
			}
		}
	}
	return bestNode
}
