const fs = require('node:fs');
const readline = require('node:readline');

module.exports = (function() {
    this.process = async (fileName, lineProcessor, accumulator) => {
        const fileStream = fs.createReadStream(fileName);

        const rl = readline.createInterface({
            input: fileStream,
            crlfDelay: Infinity,
        });

        // Each line in input.txt will be successively available here as `line`.
        for await (const line of rl)
            lineProcessor(line, accumulator);
    }

    return this;
})();
