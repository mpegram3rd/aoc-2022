const lineReader = require('./linereader');
const calc1 = require('./calc1');

const accumulator = {score: 0};
lineReader.process('input.txt', calc1.calculator, accumulator)
    .then(() =>  {
        console.log(`Your score is ${accumulator.score}`);
    })
    .catch((err) => {
        console.error(err);
    });
