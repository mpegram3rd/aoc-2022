const lineReader = require('../js-utils/linereader');

const grid = [];
lineReader.process('input.txt', lineProcessor, grid)
    .then(() =>  {
        const visMap = findVisibility(grid);
        let visibleCount = 0;
        visMap.forEach(row => {
            let rowStr = "";
            row.forEach(val => {
                visibleCount += val ? 1 : 0;
                rowStr += val ? "1" : "0";
            });
            console.log(rowStr);
        });
        console.log("There are " + visibleCount + " visible trees");
    })
    .catch((err) => {
        console.error(err);
    });

function lineProcessor(line, accumulator) {
    const heights = Array.from(line, (val) => val - '0')
    accumulator.push(heights)
}

function findVisibility(grid) {
    const visMap = [];
    for (let y = 0; y < grid.length; y++) {
        const visRow = [];
        const row = grid[y];
        for (let x = 0; x < row.length; x++) {
            visRow.push(isVisible(x, y, grid));
        }
        visMap.push(visRow)
    }
    return visMap;
}

// Note array is really kind of y/x because each top level element is a row and each row is a character
function isVisible(x, y, grid) {
    const height = grid[y][x];

    let visible = checkLeft(x, y, grid, height);
    if (!visible)
        visible = checkRight(x, y, grid, height);
    if (!visible)
        visible = checkUp(x,y, grid, height);
    if (!visible)
        visible = checkDown(x,y, grid, height);
    return visible;
}

function checkLeft(x, y, grid, height) {
    let currentPos = x - 1;
    let visible = true;
    while (currentPos >= 0 && visible === true) {
        visible = grid[y][currentPos] < height
        currentPos--;
    }
    return visible;
}

function checkRight(x, y, grid, height) {
    let currentPos = x + 1;
    let visible = true;
    while (currentPos < grid[y].length && visible === true) {
        visible = grid[y][currentPos] < height
        currentPos++;
    }
    return visible;
}

function checkUp(x, y, grid, height) {
    let currentPos = y - 1;
    let visible = true;
    while (currentPos >= 0 && visible === true) {
        visible = grid[currentPos][x] < height
        currentPos--;
    }
    return visible;
}

function checkDown(x, y, grid, height) {
    let currentPos = y + 1;
    let visible = true;
    while (currentPos < grid.length && visible === true) {
        visible = grid[currentPos][x] < height
        currentPos++;
    }
    return visible;
}
