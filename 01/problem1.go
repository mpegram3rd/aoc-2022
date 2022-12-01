package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {

	filePath := os.Args[1] // read file path from commandline

	// open file and check for errors
	file, err := os.Open(filePath)
	defer file.Close()

	if err != nil {
		log.Fatal(err)
	}

	garbIn := bufio.NewScanner(file)
	garbIn.Split(bufio.ScanLines)

	elfNum := 1
	calorieTotal := 0
	maxElf := 0
	maxCalories := 0

	for garbIn.Scan() {
		line := garbIn.Text()
		if len(line) > 0 {
			calories, err := strconv.Atoi(line)
			if err != nil {
				log.Fatal("Error parsing line %s: %s\n", line, err)
			}
			calorieTotal += calories
		} else {
			// see if we have a new leading elf
			if calorieTotal > maxCalories {
				maxElf = elfNum
				maxCalories = calorieTotal
			}
			fmt.Printf("Elf %d is carrying %d calories\n", elfNum, calorieTotal)
			calorieTotal = 0
			elfNum++
		}
	}
	fmt.Println()
	fmt.Printf("Please reach out to Elf %d who is carrying %d calories if you run out of food\n", maxElf, maxCalories)
}
