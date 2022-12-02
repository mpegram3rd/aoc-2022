module.exports = (function() {
    // Scoring rules:
    // Playing Rock = +1, Playing Paper = +2, Playing Scissors = +3
    // Loss = +0, Tie = +3, Win = +6
    // Matrix setup is order Rock, Paper Scissors both horizontally and vertically
    // So [2][1] is Elf(S) / You(P) = +2(P) +0(Loss) = 2
    const scoringMatrix = [[4,8,3],
        [1,5,9],
        [7,2,6]];

    // Index mappings where 0 = Rock, 1 = Paper, 2 = Scissors for 2d array above
    // Matching index position is equivalent to the "x" axis of 2d array above
    const youMap = ['X', 'Y', 'Z']
    // Matching index position is equivalent to the "Y" axis of 2d array above
    const elfMap = ['A', 'B', 'C'];

    // Lambda for performing calculations on each line processed
    this.calculator = (line, accumulator) => {
        const plays = line.split(' ');
        if (plays.length > 0) {
            const elfPlay = normalize(plays[0], elfMap);
            const youPlay = normalize(plays[1], youMap);
            const points = scoringMatrix[elfPlay.index][youPlay.index];
            console.log(`(Raw Line: ${line}) Elf played ${elfPlay.item} and You played ${youPlay.item} - Points: ${points}`);
            accumulator.score += points
        }
    }

    // Maps and normalizes the play
    function normalize(play, map) {
        var item;
        const index = map.indexOf(play);
        switch(index) {
            case 0: item = 'Rock';
                break;
            case 1: item = 'Paper';
                break;
            case 2: item = 'Scissors';
                break;
            default: item = '';
        }

        return {
            index: index,
            item: item
        };
    }

    return this;
})();
