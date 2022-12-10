import cpu

# read all the data in at once
file1 = open('input.txt', 'r')
Lines = file1.readlines()

# initializes our CPU with the designated breakpoints
computer = cpu.CPU(1, (20, 60, 100, 140, 180, 220))

# executes each commands
for line in Lines:
    computer.execute(line)

print("\nBreakpoint Analysis")
print("Value of X Register at cycle %d: %d" % (computer.get_current_cycle(), computer.read_x_register()))

total = 0
breakpoint_values = computer.get_breakpoint_values()
for value in breakpoint_values:
    total += (value * breakpoint_values[value])

print("Sum of values at each breakpoint = %d" % total)
print()

computer.render_screen()
