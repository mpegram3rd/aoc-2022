module.exports = (function() {

    this.findBestView = (grid) => {
        const viewScores = scoreViews(grid);
        let bestScore = 0;
        let bestX = 0;
        let bestY = 0;
        console.log("Tree Visibilty Scores:\n=================");

        viewScores.forEach((row, y) => {
            row.forEach((val, x) => {
                if (val > bestScore) {
                    bestScore = val;
                    bestY = y;
                    bestX = x;
                }
            });
        });
        console.log("Highest Visibilty Score is: " + bestScore);
        console.log("Located at: " + bestX + ", " + bestY);
    }
    function scoreViews(grid) {
        const scores = [];
        for (let y = 0; y < grid.length; y++) {
            const rowScore = [];
            const row = grid[y];
            for (let x = 0; x < row.length; x++) {
                rowScore.push(score(x, y, grid));
            }
            scores.push(rowScore);
        }
        return scores;
    }

    function score(x, y, grid) {
        const height = grid[y][x];

        const left = scoreLeft(x, y, grid, height);
        const right = scoreRight(x, y, grid, height);
        const up = scoreUp(x, y, grid, height);
        const down = scoreDown(x, y, grid, height);

        return left * right * up * down;
    }

    function scoreLeft(x, y, grid, height) {
        let currentPos = x - 1;
        let score = 0;
        let blocked = false;
        while (currentPos >= 0 && !blocked) {
            score++;
            blocked = grid[y][currentPos] >= height;
            currentPos--;
        }
        return score;
    }

    function scoreRight(x, y, grid, height) {
        let currentPos = x + 1;
        let score = 0;
        let blocked = false;
        while (currentPos < grid[y].length && !blocked) {
            score++;
            blocked = grid[y][currentPos] >= height;
            currentPos++;
        }
        return score;
    }

    function scoreUp(x, y, grid, height) {
        let currentPos = y - 1;
        let score = 0;
        let blocked = false;
        while (currentPos >= 0 && !blocked) {
            score++;
            blocked = grid[currentPos][x] >= height;
            currentPos--;
        }
        return score;
    }

    function scoreDown(x, y, grid, height) {
        let currentPos = y + 1;
        let score = 0;
        let blocked = false;
        while (currentPos < grid.length && !blocked) {
            score++;
            blocked = grid[currentPos][x] >= height;
            currentPos++;
        }
        return score;
    }

    return this;
})()
