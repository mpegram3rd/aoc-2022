import re


class CPU:

    # Setup CPU with initial X Register value
    def __init__(self, _x_register, _breakpoints):

        # Build a CRT with all the pixels off represented by '.'
        self._crt = []
        for row in range(0,6):
            row_vals = []
            for col in range(0,40):
                row_vals.append('.')
            self._crt.append(row_vals)

        self._x = _x_register
        self._breakpoints = _breakpoints
        self._breakpoint_values = {}
        self._current_cycle = 0
        self._handlers = {'noop': self.__noop, 'addx': self.__add_x}

    # Read X Register value
    def read_x_register(self):
        return self._x

    # What cycle is the CPU currently on
    def get_current_cycle(self):
        return self._current_cycle

    # Find out the internal values captured at each breakpoint
    def get_breakpoint_values(self):
        return self._breakpoint_values

    # Noop does nothing for 1 CPU cycle
    def __noop(self, instruction):
        print("Executing: noop")
        self.__tick(1)

    # Add X will add the value in the instruction to the X register after 2 CPU cycles
    def __add_x(self, instruction):
        print("Executing: addx %d" % (int(instruction[1])))
        self.__tick(2)
        self._x += int(instruction[1])

    # Simulates the CPU cycle and captures the current values if a breakpoint is crossed
    def __tick(self, count):
        if count > 0:
            self.__update_crt()
            self._current_cycle += 1
            if self._current_cycle in self._breakpoints:
                self._breakpoint_values[self._current_cycle] = self.read_x_register()
            self.__tick(count - 1)

    def __update_crt(self):
        # 40 pixels per row, do int division
        row = self._current_cycle // 40
        # remainder is the column to render
        col = self._current_cycle % 40

        # Pixels are 3 wide based off value of the x register
        pixel_range = range(self._x - 1, self._x + 2, 1)

        # so if the scanline is within the pixel range it should be rendered
        # as a '#' otherwise render a '.'
        self._crt[row][col] = '#' if col in pixel_range else '.'

    # Run a command on the CPU
    def execute(self, _line):
        _line = _line.replace('\n', '')  # strip end of line
        instruction = re.split(' ', _line)

        # Lookup the handler for the instruction and pass in the instruction details
        self._handlers[instruction[0]](instruction)

    def render_screen(self):
        print("Screen output:")
        for y in self._crt:
            line = ''
            for x in y:
                line += x
            print(line)
