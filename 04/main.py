import re


##############################################################################
# Assignment:
#
# The elves are assigned grids to clean. They've noticed there are some cases where these assignments overlap
# between the pairs of elves.
# Problem 1: Find how many of these assignments are COMPLETE overlaps (all of one elf's grids are inside
# their partner's)
#
# Problem 2: Find out how many of these assignments are PARTIAL overlaps
#############################################################################

# this will take the line and parse it into 2 distinct sets representing the range of numberrs
def parse(_line):
    values = list(map(int, re.split(',|-', _line)))
    set1 = set(range(values[0], values[1] + 1))  # +1 to make end of range inclusive
    set2 = set(range(values[2], values[3] + 1))  # +1 to make end of range inclusive
    return [set1, set2]


file1 = open('input.txt', 'r')
Lines = file1.readlines()

answer1count = 0
answer2count = 0

for line in Lines:
    elf_sets = parse(line)

    # if either set is a superset of the other, then it meets the criteria for problem one (complete overlap)
    if elf_sets[0].issuperset(elf_sets[1]) or elf_sets[1].issuperset(elf_sets[0]):
        answer1count = answer1count + 1

    # if the intersection of the 2 sets has any values at all, it meets the criteria for problem two (partial overlap)
    if len(elf_sets[0].intersection(elf_sets[1])) > 0:
        answer2count = answer2count + 1

print("Number of complete overlapping assignments: " + answer1count)
print("Number of partial overlapping assignemnts: " + answer2count)
