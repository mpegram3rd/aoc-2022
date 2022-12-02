const lineReader = require('./linereader');
const calc = require('./calc2');  // calc1 / calc2

const accumulator = {score: 0};
lineReader.process('input.txt', calc.calculator, accumulator)
    .then(() =>  {
        console.log(`Your score is ${accumulator.score}`);
    })
    .catch((err) => {
        console.error(err);
    });
