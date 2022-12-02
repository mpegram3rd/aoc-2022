const lineReader = require('./linereader');
// Scoring rules:
// Playing Rock = +1, Playing Paper = +2, Playing Scissors = +3
// Loss = +0, Tie = +3, Win = +6
// Matrix setup is order Rock, Paper Scissors both horizontally and vertically
// So [2][1] is Elf(S) / You(P) = +2(P) +0(Loss) = 2
const scoringMatrix = [[4,8,3],
                       [1,5,9],
                       [7,2,6]];

// Lambda for performing calculations on each line processed
function calculator(line, accumulator) {
    console.log(`Line from file: ${line}`);
    accumulator.count++;
}

const accumulator = {count: 0};
lineReader.process('input.txt', calculator, accumulator)
    .then(() =>  {
        console.log(`Processed ${accumulator.count} lines from the file`, accumulator);
        console.log('Finished');
    })
    .catch((err) => {
        console.error(err);
    });
