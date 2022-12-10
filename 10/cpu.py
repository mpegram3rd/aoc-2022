import re


class CPU:

    # Setup CPU with initial X Register value
    def __init__(self, _x_register, _breakpoints):
        self._x = _x_register
        self._breakpoints = _breakpoints
        self._breakpoint_values = {}
        self._current_cycle = 0
        self._handlers = {'noop': self.__noop, 'addx': self.__add_x}

    # Read X Register value
    def read_x_register(self):
        return self._x

    def get_current_cycle(self):
        return self._current_cycle

    def get_breakpoint_values(self):
        return self._breakpoint_values

    def __noop(self, instruction):
        print("Executing: noop")
        self.__tick(1)

    def __add_x(self, instruction):
        print("Executing: addx %d" % (int(instruction[1])))
        self.__tick(2)
        self._x += int(instruction[1])

    def __tick(self, count):
        if count > 0:
            self._current_cycle += 1
            if self._current_cycle in self._breakpoints:
                print("-- Breakpoint Value for cycle %d with regX %d = %d" % (self._current_cycle, self.read_x_register(), (self._current_cycle * self.read_x_register())))
                self._breakpoint_values[self._current_cycle] = self.read_x_register()
            self.__tick(count -1)

    # Run a command on the CPU
    def execute(self, _line):
        _line = _line.replace('\n', '')  # strip end of line
        instruction = re.split(' ', _line)

        # Lookup the handler for the instruction and pass in the instruction details
        self._handlers[instruction[0]](instruction)
