const mapping = require('./datamapping');

module.exports = (function() {
    // Lambda for performing calculations on each line processed
    this.calculator = (line, accumulator) => {
        const plays = line.split(' ');
        if (plays.length > 0) {
            const elfPlay = normalize(plays[0], mapping.elfMap);
            const youPlay = normalize(plays[1], mapping.youMap);
            const points = mapping.scoringMatrix[elfPlay.index][youPlay.index];
            console.log(`(Raw Line: ${line}) Elf played ${elfPlay.item} and You played ${youPlay.item} - Points: ${points}`);
            accumulator.score += points
        }
    }

    // Maps and normalizes the play
    function normalize(play, map) {
        const index = map.indexOf(play);

        return {
            index: index,
            item: mapping.rpsMap[index]
        };
    }

    return this;
})();
