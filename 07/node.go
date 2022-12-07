package main

type Node struct {
	name     string
	isDir    bool
	size     int
	children []Node
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

func (n *Node) AddChild(child *Node) {
	child.parent = n
	n.children = append(n.children, *child)
}

func (n *Node) GetParent() *Node {
	return n.parent
}

func (n *Node) FindByName(name string) *Node {
	for _, child := range n.children {
		if child.name == name {
			return &child
		}
	}
	return nil
}
