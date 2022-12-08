const lineReader = require('../js-utils/linereader');
const solution1 = require('./solution1');
const solution2 = require('./solution2');

const grid = [];
lineReader.process('input.txt', lineProcessor, grid)
    .then(() =>  {
        solution1.checkVisibility(grid);
        solution2.findBestView(grid);
    })
    .catch((err) => {
        console.error(err);
    });

function lineProcessor(line, accumulator) {
    const heights = Array.from(line, (val) => val - '0')
    accumulator.push(heights)
}
