import re


def parse(line):
    values = list(map(int, re.split(',|-', line)))
    set1 = set(range(values[0], values[1]))
    set2 = set(range(values[2], values[3]))
    return [set1, set2]


file1 = open('input.txt', 'r')
Lines = file1.readlines()

count = 0
# Strips the newline character
for line in Lines:
    elfsets = parse(line)
    if elfsets[0].issuperset(elfsets[1]) or elfsets[1].issuperset(elfsets[0]):
        count = count + 1

print(count)
