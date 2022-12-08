const lineReader = require('../js-utils/linereader');

const grid = [];
lineReader.process('input.txt', (line, accum )=> {}, grid)
    .then(() =>  {
        console.log(`Your score is ${accumulator.score}`);
    })
    .catch((err) => {
        console.error(err);
    });
