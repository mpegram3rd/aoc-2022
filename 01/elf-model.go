package main

import "fmt"

type Elf struct {
	num           int
	totalCalories int
}

func (e *Elf) AddCalories(calories int) {
	e.totalCalories += calories
}

func (e *Elf) GetName() string {
	return fmt.Sprintf("Elf %d", e.num)
}

func (e *Elf) Report() {
	fmt.Printf("%s is carrying %d total calories\n", e.GetName(), e.totalCalories)
}
