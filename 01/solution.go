package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"sort"
	"strconv"
)

func main() {

	filePath := os.Args[1]                   // read file path from commandline
	howMany, err := strconv.Atoi(os.Args[2]) // how many of the  top elves to tally
	if err != nil {
		log.Fatalf("Failed to parse the howMany parameter to a number\n", err)
	}

	// open file and check for errors
	file, err := os.Open(filePath)

	defer file.Close()

	if err != nil {
		log.Fatal(err)
	}

	garbIn := bufio.NewScanner(file)
	garbIn.Split(bufio.ScanLines)

	elves := []Elf{}
	elfNum := 1

	elf := createElf(elfNum)

	for garbIn.Scan() {
		line := garbIn.Text()
		if len(line) > 0 {
			calories, err := strconv.Atoi(line)
			if err != nil {
				log.Fatal("Error parsing line %s: %s\n", line, err)
			}
			elf.AddCalories(calories)
		} else {
			elf.Report()
			elves = append(elves, elf)
			elfNum++
			elf = createElf(elfNum)
		}
	}
	elfReport(elves, howMany)

}

func createElf(elfNum int) Elf {
	return Elf{
		num:           elfNum,
		totalCalories: 0,
	}
}

func elfReport(elves []Elf, howMany int) {
	sort.Slice(elves, func(indx1, indx2 int) bool {
		return elves[indx1].totalCalories > elves[indx2].totalCalories
	})

	fmt.Println()
	fmt.Printf("Read in %d elves\n", len(elves))
	fmt.Printf("Top %d elves\n", howMany)
	totalCollectedCalories := 0
	for indx, elf := range elves[0:howMany] {
		fmt.Printf("Rank #%d - %s is carrying %d calories\n", indx+1, elf.GetName(), elf.totalCalories)
		totalCollectedCalories += elf.totalCalories
	}
	fmt.Printf("Total collected calories = %d\n", totalCollectedCalories)
}
