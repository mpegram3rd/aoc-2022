import cpu

file1 = open('input.txt', 'r')
Lines = file1.readlines()
computer = cpu.CPU(1, (20, 60, 100, 140, 180, 220))

for line in Lines:
    computer.execute(line)

print("Value of X Register at cycle %d: %d" % (computer.get_current_cycle(), computer.read_x_register()))

sum = 0
breakpoint_values = computer.get_breakpoint_values()
for value in breakpoint_values:
    sum += (value * breakpoint_values[value])

print("Sum of values at each breakpoint = %d" % sum)
