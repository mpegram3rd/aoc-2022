module.exports = (function() {

    // Scoring rules:
    // Playing Rock = +1, Playing Paper = +2, Playing Scissors = +3
    // Loss = +0, Tie = +3, Win = +6
    // Matrix setup is order Rock, Paper Scissors both horizontally and vertically
    // So [2][1] is Elf(S) / You(P) = +2(P) +0(Loss) = 2
    this.scoringMatrix = [[4,8,3],
        [1,5,9],
        [7,2,6]];

    // Index mappings where 0 = Rock, 1 = Paper, 2 = Scissors for 2d array above
    // Matching index position is equivalent to the "x" axis of 2d array above
    this.youMap = ['X', 'Y', 'Z']
    // Matching index position is equivalent to the "Y" axis of 2d array above
    this.elfMap = ['A', 'B', 'C'];

    this.rpsMap = ['Rock', 'Paper', 'Scissors'];

    // Maps and normalizes the play
    this.normalize = function normalize(play, map) {
        const index = map.indexOf(play);

        return {
            index: index,
            item: rpsMap[index]
        };
    }


    return this;
})()
