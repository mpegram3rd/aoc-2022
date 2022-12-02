module.exports = (function() {
    const mapping = require('./datamapping');


    // Rock = 0, Paper = 1, Scissor = 2
    // X = lose, Y = Tie, Z = Win
    // So Rock + Y (RockY) = we must tie rock so we play Rock(0)
    // Rock + Z (RockZ) = we must win so we play Paper(1)
    const stategyMap = {
        "RockX" : 2,
        "RockY" : 0,
        "RockZ" : 1,
        "PaperX" : 0,
        "PaperY" : 1,
        "PaperZ" : 2,
        "ScissorsX" : 1,
        "ScissorsY" : 2,
        "ScissorsZ" : 0
    }


    // Lambda for performing calculations on each line processed
    this.calculator = (line, accumulator) => {
        const plays = line.split(' ');
        if (plays.length > 0) {
            const elfPlay = normalize(plays[0], mapping.elfMap);
            const youPlay = findMyPlay(elfPlay, plays[1]);
            const points = scoringMatrix[elfPlay.index][youPlay.index];
            console.log(`(Raw Line: ${line}) Elf played ${elfPlay.item} and You played ${youPlay.item} - Points: ${points}`);
            accumulator.score += points
        }
    }


    // Strategy is like this:
    // X = lose
    // Y = draw
    // Z = win
    // This will figure out what you have to play against the elf's play to follow the strategy
    function findMyPlay(elfPlay, strategy) {
        const playKey = elfPlay.item + strategy;
        const index = stategyMap[playKey];
        return {
            index: index,
            item: mapping.rpsMap[index]
        };
    }


    return this;
})();
